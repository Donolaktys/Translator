package ru.donolaktys.translator.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.donolaktys.translator.BuildConfig
import ru.donolaktys.translator.R
import ru.donolaktys.translator.view.history.HistoryFragment
import ru.donolaktys.translator.view.words.WordsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, WordsFragment()).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.menu_item_search -> {
                    hideFragment(HISTORY_TAG)
                    showFragment(R.id.container, SEARCH_TAG, WordsFragment())
                }
                R.id.menu_item_history -> {
                    hideFragment(SEARCH_TAG)
                    showFragment(R.id.container, HISTORY_TAG, HistoryFragment())
                }
                else -> {
                    super.onOptionsItemSelected(item)
                }
            }
    }

    private fun showFragment(containerId: Int, tag: String, currentFragment: Fragment): Boolean {
        val fragment = supportFragmentManager.findFragmentByTag(tag);
        val transaction = supportFragmentManager.beginTransaction ()

        if (fragment != null) {
            transaction.show(fragment)
        } else {
            transaction.add(containerId, currentFragment, tag)
        }
        transaction.commit()
        return true
    }

    private fun hideFragment(tag: String) {
        val currentFragment = supportFragmentManager.findFragmentByTag(tag)

        if (currentFragment != null) {
            supportFragmentManager.beginTransaction().hide(currentFragment)
                .commitNow()
        }
    }

    companion object {
        private val SEARCH_TAG: String = BuildConfig.APPLICATION_ID + ".SEARCH_TAG"
        private val HISTORY_TAG: String = BuildConfig.APPLICATION_ID + ".HISTORY_TAG"
//        private val FAVORITES_TAG: String = BuildConfig.APPLICATION_ID + ".FAVORITES_TAG"
    }
}