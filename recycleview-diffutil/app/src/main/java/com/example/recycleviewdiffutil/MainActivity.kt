package com.example.recycleviewdiffutil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mListFragment = ListFragment()
        val mFragmentManager = supportFragmentManager
        mFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container,mListFragment)
            commit()
        }
    }
}