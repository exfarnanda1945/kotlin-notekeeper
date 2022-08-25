package com.example.notekeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController= findNavController(R.id.fragmentContainerView)
        setupActionBarWithNavController(navController)
    }

    //https://www.youtube.com/watch?v=Qe_6OmmFaR8

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)

        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}