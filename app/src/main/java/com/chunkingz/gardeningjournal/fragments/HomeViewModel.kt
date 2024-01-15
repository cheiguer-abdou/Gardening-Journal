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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val _homeData = MutableLiveData<String>()
    private val repository: PlantRepository

    val homeData: LiveData<String>
        get() = _homeData

    fun updateHomeData(newData: String) {
        _homeData.postValue(newData)
    }

    val allPlants: LiveData<List<Plant>>

    init {
        repository = PlantRepository(application)
        allPlants = repository.allPlants
    }

    fun getPlantById(plant: Plant) = viewModelScope.launch {
        repository.getPlantById(plant.id)
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
