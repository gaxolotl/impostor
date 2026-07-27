package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.db.GameRepository
import com.example.data.db.PlayerStatsEntity
import com.example.data.model.Category
import com.example.data.model.DefaultCategories
import com.example.data.model.GamePhase
import com.example.data.model.GameSettings
import com.example.data.model.Language
import com.example.data.model.Player
import com.example.data.model.PlayerRole
import com.example.data.model.RoundResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository = GameRepository(
        AppDatabase.getDatabase(application).gameDao()
    )

    private val _language = MutableStateFlow(Language.EN)
    val language: StateFlow<Language> = _language.asStateFlow()

    private val _currentPhase = MutableStateFlow(GamePhase.HOME)
    val currentPhase: StateFlow<GamePhase> = _currentPhase.asStateFlow()

    private val _gameSettings = MutableStateFlow(GameSettings())
    val gameSettings: StateFlow<GameSettings> = _gameSettings.asStateFlow()

    // Default 3 initial players
    private val _players = MutableStateFlow(
        listOf(
            Player(id = UUID.randomUUID().toString(), name = "Alex"),
            Player(id = UUID.randomUUID().toString(), name = "Maria"),
            Player(id = UUID.randomUUID().toString(), name = "Ivan")
        )
    )
    val players: StateFlow<List<Player>> = _players.asStateFlow()

    private val _currentRevealIndex = MutableStateFlow(0)
    val currentRevealIndex: StateFlow<Int> = _currentRevealIndex.asStateFlow()

    private val _selectedSecretWord = MutableStateFlow("")
    val selectedSecretWord: StateFlow<String> = _selectedSecretWord.asStateFlow()

    private val _selectedCategoryName = MutableStateFlow("")
    val selectedCategoryName: StateFlow<String> = _selectedCategoryName.asStateFlow()

    private val _timerSecondsLeft = MutableStateFlow(60)
    val timerSecondsLeft: StateFlow<Int> = _timerSecondsLeft.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    // Map of voterPlayerId to votedPlayerId
    private val _votes = MutableStateFlow<Map<String, String>>(emptyMap())
    val votes: StateFlow<Map<String, String>> = _votes.asStateFlow()

    private val _roundResult = MutableStateFlow<RoundResult?>(null)
    val roundResult: StateFlow<RoundResult?> = _roundResult.asStateFlow()

    val playerStatsList: StateFlow<List<PlayerStatsEntity>> = repository.playerStats
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val customCategories: StateFlow<List<Category>> = combine(_language) { lang ->
        lang[0]
    }.combine(repository.getCustomCategories(Language.EN)) { _, list ->
        list
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private var timerJob: Job? = null

    fun setLanguage(lang: Language) {
        _language.value = lang
    }

    fun setPhase(phase: GamePhase) {
        _currentPhase.value = phase
        if (phase != GamePhase.DISCUSSION) {
            pauseTimer()
        }
    }

    fun updateSettings(settings: GameSettings) {
        val count = settings.impostorCount.coerceAtMost((_players.value.size - 1).coerceAtLeast(1))
        _gameSettings.value = settings.copy(impostorCount = count)
    }

    fun addPlayer(name: String) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        val newPlayer = Player(id = UUID.randomUUID().toString(), name = trimmed)
        _players.value = _players.value + newPlayer
        // Ensure impostor count is valid
        updateSettings(_gameSettings.value)
    }

    fun removePlayer(index: Int) {
        if (_players.value.size <= 3) return
        val list = _players.value.toMutableList()
        list.removeAt(index)
        _players.value = list
        updateSettings(_gameSettings.value)
    }

    fun updatePlayerName(index: Int, newName: String) {
        val list = _players.value.toMutableList()
        if (index in list.indices) {
            list[index] = list[index].copy(name = newName)
            _players.value = list
        }
    }

    fun startNewRound() {
        val currentLang = _language.value
        val settings = _gameSettings.value
        val currentPlayers = _players.value

        if (currentPlayers.size < 3) return

        // Gather candidate categories
        val allAvailable = DefaultCategories.list + customCategories.value
        val selectedCats = allAvailable.filter { settings.selectedCategoryIds.contains(it.id) }
            .ifEmpty { DefaultCategories.list }

        // Pick random category and random word
        val chosenCat = selectedCats.random()
        val words = chosenCat.getWords(currentLang)
        val word = if (words.isNotEmpty()) words.random() else "Secret"

        _selectedSecretWord.value = word
        _selectedCategoryName.value = chosenCat.getName(currentLang)

        // Assign Impostors
        val numImpostors = settings.impostorCount.coerceIn(1, currentPlayers.size - 1)
        val impostorIndices = currentPlayers.indices.shuffled().take(numImpostors).toSet()

        val updatedPlayers = currentPlayers.mapIndexed { index, player ->
            if (impostorIndices.contains(index)) {
                player.copy(
                    role = PlayerRole.IMPOSTOR,
                    secretWord = "",
                    hasSeenWord = false
                )
            } else {
                player.copy(
                    role = PlayerRole.CIVILIAN,
                    secretWord = word,
                    hasSeenWord = false
                )
            }
        }

        _players.value = updatedPlayers
        _currentRevealIndex.value = 0
        _votes.value = emptyMap()
        _roundResult.value = null

        setPhase(GamePhase.REVEAL)
    }

    fun markCurrentPlayerSeen() {
        val idx = _currentRevealIndex.value
        val list = _players.value.toMutableList()
        if (idx in list.indices) {
            list[idx] = list[idx].copy(hasSeenWord = true)
            _players.value = list
        }

        if (idx + 1 < list.size) {
            _currentRevealIndex.value = idx + 1
        } else {
            // All players revealed! Start Discussion
            val timeLimit = _gameSettings.value.timeLimitSeconds
            _timerSecondsLeft.value = if (timeLimit > 0) timeLimit else 0
            setPhase(GamePhase.DISCUSSION)
            if (timeLimit > 0) {
                startTimer()
            }
        }
    }

    fun startTimer() {
        timerJob?.cancel()
        _isTimerRunning.value = true
        timerJob = viewModelScope.launch {
            while (_isTimerRunning.value && _timerSecondsLeft.value > 0) {
                delay(1000L)
                _timerSecondsLeft.value = _timerSecondsLeft.value - 1
            }
            if (_timerSecondsLeft.value <= 0 && _gameSettings.value.timeLimitSeconds > 0) {
                _isTimerRunning.value = false
                setPhase(GamePhase.VOTING)
            }
        }
    }

    fun pauseTimer() {
        _isTimerRunning.value = false
        timerJob?.cancel()
    }

    fun toggleTimerPause() {
        if (_isTimerRunning.value) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    fun recordVote(voterId: String, suspectId: String) {
        val currentVotes = _votes.value.toMutableMap()
        currentVotes[voterId] = suspectId
        _votes.value = currentVotes
    }

    fun finishVotingAndReveal() {
        pauseTimer()
        val allPlayers = _players.value
        val currentVotes = _votes.value
        val secretWord = _selectedSecretWord.value
        val categoryName = _selectedCategoryName.value

        // Count votes per suspect
        val voteCounts = mutableMapOf<String, Int>()
        currentVotes.values.forEach { suspectId ->
            voteCounts[suspectId] = (voteCounts[suspectId] ?: 0) + 1
        }

        val maxVotes = voteCounts.values.maxOrNull() ?: 0
        val votedOutPlayers = if (maxVotes > 0) {
            allPlayers.filter { voteCounts[it.id] == maxVotes }
        } else {
            emptyList()
        }

        val impostors = allPlayers.filter { it.role == PlayerRole.IMPOSTOR }
        val impostorIds = impostors.map { it.id }.toSet()

        // Check if any voted out player is an impostor
        val isImpostorCaught = votedOutPlayers.any { it.role == PlayerRole.IMPOSTOR }
        val winnerTeam = if (isImpostorCaught) PlayerRole.CIVILIAN else PlayerRole.IMPOSTOR

        // Calculate points
        val pointsMap = mutableMapOf<String, Int>()
        allPlayers.forEach { player ->
            var pts = 0
            if (player.role == PlayerRole.CIVILIAN) {
                if (winnerTeam == PlayerRole.CIVILIAN) pts += 10
                // Bonus for voting correctly
                val myVote = currentVotes[player.id]
                if (myVote != null && impostorIds.contains(myVote)) {
                    pts += 10
                }
            } else {
                // Impostor
                if (winnerTeam == PlayerRole.IMPOSTOR) pts += 25
            }
            pointsMap[player.id] = pts
        }

        val roundRes = RoundResult(
            secretWord = secretWord,
            categoryName = categoryName,
            impostorNames = impostors.map { it.name },
            votedOutNames = votedOutPlayers.map { it.name },
            isImpostorCaught = isImpostorCaught,
            winnerTeam = winnerTeam,
            pointsAwarded = pointsMap
        )

        _roundResult.value = roundRes

        // Persist stats in database
        viewModelScope.launch {
            allPlayers.forEach { player ->
                val isImp = player.role == PlayerRole.IMPOSTOR
                val won = if (isImp) winnerTeam == PlayerRole.IMPOSTOR else winnerTeam == PlayerRole.CIVILIAN
                val pts = pointsMap[player.id] ?: 0
                val myVote = currentVotes[player.id]
                val correctVote = myVote != null && impostorIds.contains(myVote)

                repository.savePlayerGameResult(
                    playerName = player.name,
                    isImpostor = isImp,
                    wonGame = won,
                    pointsEarned = pts,
                    correctVoteCast = correctVote
                )
            }
        }

        setPhase(GamePhase.RESULT)
    }

    fun addCustomCategory(title: String, words: List<String>) {
        if (title.isBlank() || words.isEmpty()) return
        viewModelScope.launch {
            repository.addCustomCategory(title, _language.value, words)
        }
    }

    fun deleteCustomCategory(dbId: Long) {
        viewModelScope.launch {
            repository.deleteCustomCategory(dbId)
        }
    }
}
