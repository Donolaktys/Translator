package ru.donolaktys.translator.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.modo.*
import ru.donolaktys.translator.App.TranslatorApp
import ru.donolaktys.translator.R
import ru.donolaktys.translator.Screens
import ru.donolaktys.translator.utils.BackButtonListener

class MainActivity() : AppCompatActivity() {

    private val modo: Modo = TranslatorApp.instance.modo
    private val modoRender by lazy { ModoRender(this, R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        modo.init(savedInstanceState, modoRender, Screens.WordsScreen())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.menu_item_search -> {
                    modo.forward(Screens.WordsScreen())
                    true
                }
                R.id.menu_item_history -> {
                    modo.forward(Screens.HistoryScreen())
                    true
                }
                else -> {
                    super.onOptionsItemSelected(item)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        modo.render = modoRender
    }

    override fun onPause() {
        super.onPause()
        modo.render = null
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        modo.exit()
    }
}