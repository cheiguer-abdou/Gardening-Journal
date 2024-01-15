package com.chunkingz.gardeningjournal.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.chunkingz.gardeningjournal.models.Plant
import com.chunkingz.gardeningjournal.models.PlantDao
import com.chunkingz.gardeningjournal.models.PlantDatabase
import com.chunkingz.gardeningjournal.models.PlantRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GardenLogViewModel(application: Application) : AndroidViewModel(application) {
    private val _gardenLogData = MutableLiveData<List<Plant>>()
    private val repository: PlantRepository

    val gardenLogData: LiveData<List<Plant>>
        get() = _gardenLogData

    fun updateGardenLogData(newData: List<Plant>) {
        _gardenLogData.value = newData
    }

    val allPlants: LiveData<List<Plant>>

    init {
        repository = PlantRepository(application)
        allPlants = repository.allPlants
    }

    fun getPlantById(plantId: Int): LiveData<Plant> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            val plant = repository.getPlantById(plantId)
            emitSource(plant)
        }
    }

    fun insert(plant: Plant) = viewModelScope.launch {
        repository.insert(plant)
    }

    fun update(plant: Plant) = viewModelScope.launch {
        repository.update(plant)
    }

    fun delete(plant: Plant) = viewModelScope.launch {
        repository.delete(plant)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

}

