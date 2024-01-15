package com.chunkingz.gardeningjournal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chunkingz.gardeningjournal.R
import com.chunkingz.gardeningjournal.models.Plant

class HomeFragment: Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.deleteAll()

        initializeDummyData()

        val navController = findNavController()
        view.findViewById<Button>(R.id.btnNavigateToGardenLog).setOnClickListener {
            navController.navigate(R.id.action_home_to_garden_log)
        }
    }

    private fun initializeDummyData() {
        val samplePlants = mutableListOf<Plant>()
        samplePlants.add(Plant(name = "Rose", type = "Flower", wateringFrequency = 2, plantingDate
        = "2023-01-01"))
        samplePlants.add(Plant(name = "Tomato", type = "Vegetable", wateringFrequency = 3,
            plantingDate = "2023-02-15"))
        samplePlants.add(Plant(name = "Basil", type = "Herb", wateringFrequency = 1, plantingDate =
        "2023-03-10"))
        for (plant in samplePlants) {
            homeViewModel.insert(plant)
        }
    }
}
