package ru.donolaktys.translator.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.donolaktys.translator.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.container, WordsFragment()).commit()
    }
}