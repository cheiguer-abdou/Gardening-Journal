package com.chunkingz.gardeningjournal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.room.Room
import com.chunkingz.gardeningjournal.models.PlantDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    companion object {
        lateinit var gardenDatabase: PlantDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Room DB
        gardenDatabase = Room.databaseBuilder(
            applicationContext,
            PlantDatabase::class.java,
            "garden_database"
        ).build()

        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
    }

}
