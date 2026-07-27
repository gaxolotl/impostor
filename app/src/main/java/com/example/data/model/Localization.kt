package com.example.data.model

object Localization {
    fun getString(key: String, lang: Language): String {
        val map = when (lang) {
            Language.EN -> englishStrings
            Language.BG -> bulgarianStrings
        }
        return map[key] ?: englishStrings[key] ?: key
    }

    private val englishStrings = mapOf(
        "app_title" to "IMPOSTOR",
        "app_subtitle" to "The Secret Word Party Game",
        "play_now" to "Play Now",
        "custom_categories" to "Custom Categories",
        "leaderboard" to "Leaderboard & Stats",
        "settings" to "Settings",
        "language" to "Language",
        
        // Setup Screen
        "setup_game" to "Game Setup",
        "players_count" to "Players (%d)",
        "add_player" to "Add Player",
        "enter_player_name" to "Enter player name",
        "min_3_players" to "Minimum 3 players required",
        "impostors_count" to "Number of Impostors",
        "time_limit" to "Round Timer",
        "time_unlimited" to "No Timer (Unlimited)",
        "time_seconds" to "%d Seconds",
        "impostor_hint" to "Impostor Category Hint",
        "impostor_hint_desc" to "Gives the Impostor a clue about the word's category",
        "select_categories" to "Select Word Categories",
        "selected_categories_count" to "%d Categories Selected",
        "start_round" to "Start Game",
        
        // Word Reveal Screen
        "pass_phone_to" to "Pass the phone to",
        "ready_to_reveal" to "Are you alone?",
        "swipe_up_hint" to "Swipe UP to reveal secret word",
        "release_to_hide" to "Release or tap button to hide",
        "you_are_impostor" to "YOU ARE THE IMPOSTOR!",
        "impostor_subtext" to "Blend in, ask subtle questions, and don't get caught!",
        "impostor_category_hint" to "Category Clue: %s",
        "civilian_secret_word" to "YOUR SECRET WORD",
        "civilian_subtext" to "Find who doesn't know this word!",
        "confirm_seen" to "I've Seen My Word",
        "next_player" to "Pass to Next Player",
        "all_players_ready" to "All players have received their secret roles!",
        "begin_discussion" to "Begin Discussion Phase",
        
        // Discussion Screen
        "discussion_phase" to "Discussion Phase",
        "discussion_instruction" to "Take turns asking each other questions about the word without giving it away!",
        "time_left" to "Time Remaining",
        "category_badge" to "Category: %s",
        "proceed_to_vote" to "Vote Now",
        "pause" to "Pause",
        "resume" to "Resume",
        
        // Voting Screen
        "voting_phase" to "Voting Phase",
        "vote_instruction" to "Select who you suspect is the Impostor",
        "select_suspect" to "Tap player to cast vote",
        "cast_vote" to "Confirm Vote for %s",
        "submit_votes" to "Reveal Results",
        "no_votes" to "Pass voting & reveal",
        
        // Result Screen
        "round_results" to "Round Results",
        "impostor_caught" to "IMPOSTOR CAUGHT!",
        "impostor_escaped" to "IMPOSTOR ESCAPED!",
        "the_impostors_were" to "The Impostor(s) was:",
        "the_secret_word_was" to "The Secret Word was:",
        "points_summary" to "Round Points Earned",
        "play_again" to "Play Next Round",
        "back_to_menu" to "Main Menu",
        
        // Categories Screen
        "manage_categories" to "Manage Categories",
        "built_in_categories" to "Built-in Categories",
        "your_custom_categories" to "Your Custom Categories",
        "add_category" to "Create Custom Category",
        "category_name_label" to "Category Title",
        "words_label" to "Words (separated by commas)",
        "words_hint" to "e.g. Pizza, Burger, Banitsa, Pasta",
        "save" to "Save Category",
        "cancel" to "Cancel",
        "delete" to "Delete",
        "no_custom_categories" to "No custom categories added yet.",
        "enter_cat_title" to "Please enter a category title",
        "enter_min_words" to "Please enter at least 3 words",
        
        // Leaderboard Screen
        "leaderboard_title" to "Player Leaderboard & Stats",
        "total_points" to "Pts",
        "games_played" to "Games",
        "win_rate" to "Win Rate",
        "impostor_wins" to "Impostor Wins",
        "civilian_wins" to "Civilian Wins",
        "no_stats" to "No games played yet! Play a round to record stats.",
        "badges_unlocked" to "Player Badges",
        "badge_deceiver" to "🎭 Master Deceiver",
        "badge_deceiver_desc" to "Won as an Impostor",
        "badge_eagle_eye" to "🕵️ Eagle Eye Detective",
        "badge_eagle_eye_desc" to "3+ correct impostor votes",
        "badge_veteran" to "🏆 Game Veteran",
        "badge_veteran_desc" to "Played 10+ rounds",
        "badge_high_scorer" to "👑 Point Leader",
        "badge_high_scorer_desc" to "Scored 50+ points"
    )

    private val bulgarianStrings = mapOf(
        "app_title" to "ИМПОСТЪР",
        "app_subtitle" to "Парти игра с тайни думи и детективски умения",
        "play_now" to "Започни игра",
        "custom_categories" to "Мои Категории",
        "leaderboard" to "Класиране и Статистика",
        "settings" to "Настройки",
        "language" to "Език",
        
        // Setup Screen
        "setup_game" to "Настройки на играта",
        "players_count" to "Играчи (%d)",
        "add_player" to "Добави играч",
        "enter_player_name" to "Въведете име на играча",
        "min_3_players" to "Минимум 3-ма играчи са необходими",
        "impostors_count" to "Брой импостри",
        "time_limit" to "Време за рунд",
        "time_unlimited" to "Без ограничение (Без таймер)",
        "time_seconds" to "%d Секунди",
        "impostor_hint" to "Подсказка за категория",
        "impostor_hint_desc" to "Дава на импостъра подсказка за категорията на думата",
        "select_categories" to "Изберете категории с думи",
        "selected_categories_count" to "%d Избрани категории",
        "start_round" to "Започни играта",
        
        // Word Reveal Screen
        "pass_phone_to" to "Предайте телефона на",
        "ready_to_reveal" to "Сам ли сте?",
        "swipe_up_hint" to "Плъзнете НАГОРЕ, за да видите тайната дума",
        "release_to_hide" to "Плъзнете надолу или натиснете бутона, за да скриете",
        "you_are_impostor" to "ТИ СИ ИМПОСТЪРЪТ!",
        "impostor_subtext" to "Слей се с тълпата, задавай хитри въпроси и не се издавай!",
        "impostor_category_hint" to "Подсказка за категория: %s",
        "civilian_secret_word" to "ТВОЯТА ТАЙНА ДУМА",
        "civilian_subtext" to "Открий кой не знае тази дума!",
        "confirm_seen" to "Видях думата си",
        "next_player" to "Предай на следващия играч",
        "all_players_ready" to "Всички играчи видяха своите роли!",
        "begin_discussion" to "Премини към обсъждане",
        
        // Discussion Screen
        "discussion_phase" to "Фаза на обсъждане",
        "discussion_instruction" to "Задавайте си въпроси за думата, без да я издавате напълно!",
        "time_left" to "Оставащо време",
        "category_badge" to "Категория: %s",
        "proceed_to_vote" to "Гласувай сега",
        "pause" to "Пауза",
        "resume" to "Продължи",
        
        // Voting Screen
        "voting_phase" to "Фаза на гласуване",
        "vote_instruction" to "Изберете кой според вас е импостърът",
        "select_suspect" to "Натиснете играч за гласуване",
        "cast_vote" to "Потвърди гласа за %s",
        "submit_votes" to "Разкрий резултата",
        "no_votes" to "Премини към разкриване",
        
        // Result Screen
        "round_results" to "Резултати от рунда",
        "impostor_caught" to "ИМПОСТЪРЪТ Е ДЕmaskИРАН!",
        "impostor_escaped" to "ИМПОСТЪРЪТ ИЗБЯГА!",
        "the_impostors_were" to "Импостърът(ите) беше:",
        "the_secret_word_was" to "Тайната дума беше:",
        "points_summary" to "Спечелени точки от рунда",
        "play_again" to "Следващ рунд",
        "back_to_menu" to "Главно меню",
        
        // Categories Screen
        "manage_categories" to "Управление на категории",
        "built_in_categories" to "Вградени категории",
        "your_custom_categories" to "Вашите персонализирани категории",
        "add_category" to "Създай нова категория",
        "category_name_label" to "Заглавие на категорията",
        "words_label" to "Думи (разделени със запетая)",
        "words_hint" to "напр. Пица, Баница, Бургер, Мусака",
        "save" to "Запази категорията",
        "cancel" to "Отказ",
        "delete" to "Изтрий",
        "no_custom_categories" to "Все още няма добавени ваши категории.",
        "enter_cat_title" to "Моля, въведете заглавие",
        "enter_min_words" to "Моля, въведете поне 3 думи",
        
        // Leaderboard Screen
        "leaderboard_title" to "Класиране и Статистики",
        "total_points" to "Точки",
        "games_played" to "Игри",
        "win_rate" to "Успеваемост",
        "impostor_wins" to "Победи като Импостър",
        "civilian_wins" to "Победи като Гражданин",
        "no_stats" to "Все още няма изиграни игри! Изиграйте рунд за статистика.",
        "badges_unlocked" to "Значки на играчите",
        "badge_deceiver" to "🎭 Майстор на измамата",
        "badge_deceiver_desc" to "Спечели като Импостър",
        "badge_eagle_eye" to "🕵️ Детектив Остро Око",
        "badge_eagle_eye_desc" to "3+ правилни гласа за импостър",
        "badge_veteran" to "🏆 Ветеран от игрите",
        "badge_veteran_desc" to "Изиграни 10+ рунда",
        "badge_high_scorer" to "👑 Шампион по точки",
        "badge_high_scorer_desc" to "Събрани 50+ точки"
    )
}
