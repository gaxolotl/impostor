package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.GamePhase
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.DiscussionScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.ResultScreen
import com.example.ui.screens.RevealScreen
import com.example.ui.screens.SetupScreen
import com.example.ui.screens.VotingScreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.ImpostorTheme
import com.example.ui.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImpostorTheme {
                ImpostorApp()
            }
        }
    }
}

@Composable
fun ImpostorApp(viewModel: GameViewModel = viewModel()) {
    val language by viewModel.language.collectAsStateWithLifecycle()
    val phase by viewModel.currentPhase.collectAsStateWithLifecycle()
    val settings by viewModel.gameSettings.collectAsStateWithLifecycle()
    val players by viewModel.players.collectAsStateWithLifecycle()
    val currentRevealIndex by viewModel.currentRevealIndex.collectAsStateWithLifecycle()
    val categoryName by viewModel.selectedCategoryName.collectAsStateWithLifecycle()
    val timerSecondsLeft by viewModel.timerSecondsLeft.collectAsStateWithLifecycle()
    val isTimerRunning by viewModel.isTimerRunning.collectAsStateWithLifecycle()
    val votes by viewModel.votes.collectAsStateWithLifecycle()
    val roundResult by viewModel.roundResult.collectAsStateWithLifecycle()
    val customCategories by viewModel.customCategories.collectAsStateWithLifecycle()
    val playerStatsList by viewModel.playerStatsList.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DarkBackground
    ) { innerPadding ->
        Modifier.padding(innerPadding)
        when (phase) {
            GamePhase.HOME -> {
                HomeScreen(
                    language = language,
                    onToggleLanguage = { viewModel.setLanguage(it) },
                    onNavigate = { viewModel.setPhase(it) }
                )
            }
            GamePhase.SETUP -> {
                SetupScreen(
                    language = language,
                    players = players,
                    settings = settings,
                    customCategories = customCategories,
                    onToggleLanguage = { viewModel.setLanguage(it) },
                    onAddPlayer = { viewModel.addPlayer(it) },
                    onRemovePlayer = { viewModel.removePlayer(it) },
                    onUpdatePlayerName = { idx, name -> viewModel.updatePlayerName(idx, name) },
                    onUpdateSettings = { viewModel.updateSettings(it) },
                    onStartGame = { viewModel.startNewRound() },
                    onBack = { viewModel.setPhase(GamePhase.HOME) }
                )
            }
            GamePhase.REVEAL -> {
                RevealScreen(
                    language = language,
                    players = players,
                    currentIndex = currentRevealIndex,
                    settings = settings,
                    categoryName = categoryName,
                    onToggleLanguage = { viewModel.setLanguage(it) },
                    onConfirmSeen = { viewModel.markCurrentPlayerSeen() }
                )
            }
            GamePhase.DISCUSSION -> {
                DiscussionScreen(
                    language = language,
                    categoryName = categoryName,
                    secondsLeft = timerSecondsLeft,
                    isTimerRunning = isTimerRunning,
                    hasTimer = settings.timeLimitSeconds > 0,
                    onToggleLanguage = { viewModel.setLanguage(it) },
                    onTogglePause = { viewModel.toggleTimerPause() },
                    onVoteNow = { viewModel.setPhase(GamePhase.VOTING) }
                )
            }
            GamePhase.VOTING -> {
                VotingScreen(
                    language = language,
                    players = players,
                    votes = votes,
                    onToggleLanguage = { viewModel.setLanguage(it) },
                    onCastVote = { voterId, suspectId -> viewModel.recordVote(voterId, suspectId) },
                    onSubmitVotes = { viewModel.finishVotingAndReveal() }
                )
            }
            GamePhase.RESULT -> {
                ResultScreen(
                    language = language,
                    result = roundResult,
                    players = players,
                    onToggleLanguage = { viewModel.setLanguage(it) },
                    onPlayNextRound = { viewModel.startNewRound() },
                    onMainMenu = { viewModel.setPhase(GamePhase.HOME) }
                )
            }
            GamePhase.CATEGORIES -> {
                CategoriesScreen(
                    language = language,
                    customCategories = customCategories,
                    onToggleLanguage = { viewModel.setLanguage(it) },
                    onAddCustomCategory = { title, words -> viewModel.addCustomCategory(title, words) },
                    onDeleteCustomCategory = { dbId -> viewModel.deleteCustomCategory(dbId) },
                    onBack = { viewModel.setPhase(GamePhase.HOME) }
                )
            }
            GamePhase.LEADERBOARD -> {
                LeaderboardScreen(
                    language = language,
                    statsList = playerStatsList,
                    onToggleLanguage = { viewModel.setLanguage(it) },
                    onBack = { viewModel.setPhase(GamePhase.HOME) }
                )
            }
        }
    }
}
