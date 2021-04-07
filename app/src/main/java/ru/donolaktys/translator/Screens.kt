package ru.donolaktys.translator

import com.github.terrakok.modo.AppScreen
import ru.donolaktys.historyscreen.HistoryFragment
import ru.donolaktys.translator.view.description.DescriptionFragment
import ru.donolaktys.wordsscreen.WordsFragment

object Screens {
    fun WordsScreen() = AppScreen("WordsScreen") { WordsFragment() }
    fun HistoryScreen() = AppScreen("HistoryScreen") { HistoryFragment() }
    fun DescriptionScreen(text: String, translation: String, imageUrl: String) =
        AppScreen("DescriptionScreen") {
            DescriptionFragment.newInstance(
                text,
                translation,
                imageUrl
            )
        }
}