package com.devlight.school.model.repository.base

import androidx.lifecycle.MutableLiveData
import com.devlight.school.model.entity.Drink

interface DrinkApiRepository {

    fun getLiveData(): MutableLiveData<List<Drink>>
    fun updateDrinksLiveData(query: String)
    fun getCurrentData(): List<Drink>
}