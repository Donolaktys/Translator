package ru.donolaktys.translator

import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.forward
import ru.donolaktys.core.INavigation
import ru.donolaktys.translator.Screens.DescriptionScreen
import ru.donolaktys.translator.Screens.HistoryScreen
import ru.donolaktys.translator.Screens.WordsScreen

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

    override fun getRouter(): Modo {
        return modo
    }
}