package com.chunkingz.gardeningjournal.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chunkingz.gardeningjournal.R
import com.chunkingz.gardeningjournal.models.Plant

class PlantDetailsFragment: Fragment() {
    private lateinit var viewModel: GardenLogViewModel
    private var plantId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_plant_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plantId = arguments?.getInt("plantId") ?: 0

        viewModel = ViewModelProvider(this)[GardenLogViewModel::class.java]

        viewModel.getPlantById(plantId).observe(viewLifecycleOwner) { plant ->
            updateUI(plant)
        }

        val navController = findNavController()
        // Navigate to Plant details Fragment
        view.findViewById<Button>(R.id.btnNavigateToGardenLog).setOnClickListener {
            navController.navigate(R.id.action_plant_details_to_garden_log)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(plant: Plant) {
        val textView = view?.findViewById<TextView>(R.id.textViewPlantDetails)
        if (textView != null) {
            textView.text = "Plant Name: ${plant.name}\n" +
                    "Type: ${plant.type}\n" +
                    "Watering Frequency: ${plant.wateringFrequency} days\n" +
                    "Planting Date: ${plant.plantingDate}"
        }
    }

}
