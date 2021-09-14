package com.example.noteappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startFragment()
    }

    fun startFragment() {
        val fm = supportFragmentManager
        var newFragment = fm.findFragmentById(R.id.main_frame_layout)
        if (newFragment == null) {
            newFragment = NotesFragment()
            fm.beginTransaction().add(R.id.main_frame_layout, newFragment)
                .commit()

        }
    }
}