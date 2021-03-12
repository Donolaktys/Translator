package ru.donolaktys.translator.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.donolaktys.translator.R
import ru.donolaktys.translator.view.words.WordsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, WordsFragment()).commit()
        }

    }
}