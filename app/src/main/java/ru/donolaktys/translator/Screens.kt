package ru.donolaktys.translator

import com.github.terrakok.modo.AppScreen
import com.github.terrakok.modo.Modo
import ru.donolaktys.translator.model.data.DataModel
import ru.donolaktys.translator.view.description.DescriptionFragment
import ru.donolaktys.translator.view.history.HistoryFragment
import ru.donolaktys.translator.view.words.WordsFragment

object Screens {
    fun WordsScreen() = AppScreen("WordsScreen") { WordsFragment() }
    fun HistoryScreen() = AppScreen("HistoryScreen") { HistoryFragment() }
    fun DescriptionScreen(data: DataModel) = AppScreen("DescriptionScreen") { DescriptionFragment.newInstance(data) }
}