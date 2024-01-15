package com.chunkingz.gardeningjournal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chunkingz.gardeningjournal.R
import com.chunkingz.gardeningjournal.models.Plant
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GardenLogFragment : Fragment() {

    private lateinit var gardenLogViewModel: GardenLogViewModel
    private lateinit var plantAdapter: PlantAdapter
    private var selectedPlantId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_garden_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gardenLogViewModel = ViewModelProvider(this)[GardenLogViewModel::class.java]
        plantAdapter = PlantAdapter()

        val plantRecyclerView: RecyclerView = view.findViewById(R.id.plantRecyclerView)
        plantRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        plantRecyclerView.adapter = plantAdapter

        // Observe the list of plants from ViewModel
        gardenLogViewModel.allPlants.observe(viewLifecycleOwner) { plants ->
            plantAdapter.submitList(plants)
        }

        // Get the new plant details from the EditText fields in fragment_garden_log.xml & Create a new Plant object
        view.findViewById<Button>(R.id.btnAddPlant).setOnClickListener {
            val plantName = view.findViewById<EditText>(R.id.editTextPlantName).text.toString()
            val plantType = view.findViewById<EditText>(R.id.editTextPlantType).text.toString()
            val plantWateringFrequency = view.findViewById<EditText>(R.id.editTextWateringFrequency).text.toString()

            if (plantName.trim().isEmpty() || plantType.trim().isEmpty() || plantWateringFrequency.trim().isEmpty()){
                Toast.makeText(context, "Fill out all the fields", Toast.LENGTH_LONG).show();
            } else {
                val newPlant = Plant(
                    name = plantName,
                    type = plantType,
                    wateringFrequency = plantWateringFrequency.toInt(),
                    plantingDate = getCurrentDate()
                )

                gardenLogViewModel.insert(newPlant)

                view.findViewById<EditText>(R.id.editTextPlantName).text.clear()
                view.findViewById<EditText>(R.id.editTextPlantType).text.clear()
                view.findViewById<EditText>(R.id.editTextWateringFrequency).text.clear()
            }
        }

        val navController = findNavController()
        plantRecyclerView.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val childView = rv.findChildViewUnder(e.x, e.y)
                if (childView != null && e.action == MotionEvent.ACTION_UP) {
                    val position = rv.getChildAdapterPosition(childView)
                    val clickedPlant = plantAdapter.currentList[position]
                    val args = Bundle()
                    args.putInt("plantId", clickedPlant.id)
                    navController.navigate(R.id.action_garden_log_to_plant_details, args)
                    return true
                }
                return false
            }
        })
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

}
