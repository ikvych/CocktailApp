package com.devlight.school.data.repository.base

import androidx.lifecycle.MutableLiveData
import com.devlight.school.data.entity.Drink

interface DrinkApiRepository {

    fun getLiveData(): MutableLiveData<List<Drink>>
    fun updateDrinksLiveData(query: String)
    fun getCurrentData(): List<Drink>
}