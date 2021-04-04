package ru.donolaktys.translator.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.modo.*
import org.koin.android.ext.android.inject
import ru.donolaktys.translator.R
import ru.donolaktys.core.INavigation
import ru.donolaktys.translator.Navigate
import ru.donolaktys.utils.BackButtonListener

class MainActivity() : AppCompatActivity() {

    private val navigate: INavigation<Modo> by inject()
    private val modoRender by lazy { ModoRender(this, R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigate.getRouter().init(savedInstanceState, modoRender, Navigate.Screens.WordsScreen())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.menu_item_search -> {
                    navigate.toWordsScreen()
                    true
                }
                R.id.menu_item_history -> {
                    navigate.toHistoryScreen()
                    true
                }
                else -> {
                    super.onOptionsItemSelected(item)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        navigate.getRouter().render = modoRender
    }

    override fun onPause() {
        super.onPause()
        navigate.getRouter().render = null
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        navigate.getRouter().exit()
    }
}