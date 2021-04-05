package ru.donolaktys.translator

import com.github.terrakok.modo.AppScreen
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.forward
import ru.donolaktys.core.INavigation
import ru.donolaktys.translator.Navigate.Screens.DescriptionScreen
import ru.donolaktys.translator.Navigate.Screens.HistoryScreen
import ru.donolaktys.translator.Navigate.Screens.WordsScreen
import ru.donolaktys.translator.view.description.DescriptionFragment
import ru.donolaktys.translator.view.history.HistoryFragment
import ru.donolaktys.wordsscreen.WordsFragment

class Navigate(private val modo: Modo) : INavigation<Modo> {
    override fun toWordsScreen() {
        modo.forward(WordsScreen())
    }

    override fun toHistoryScreen() {
        modo.forward(HistoryScreen())
    }

    override fun toDescriptionScreen(text: String, translation: String, imageUrl: String) {
        modo.forward(DescriptionScreen(text, translation, imageUrl))
    }

    object Screens {
        fun WordsScreen() = AppScreen("WordsScreen") { ru.donolaktys.wordsscreen.WordsFragment() }
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

    override fun getRouter(): Modo {
        return modo
    }
}