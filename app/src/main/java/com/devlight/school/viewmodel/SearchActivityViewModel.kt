package com.devlight.school.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.devlight.school.data.entity.Drink
import com.devlight.school.data.repository.DrinkApiRepositoryImpl
import com.devlight.school.data.repository.base.DrinkApiRepository
import com.devlight.school.base.BaseViewModel

class SearchActivityViewModel(application: Application): BaseViewModel(application) {
    private val apiRepository: DrinkApiRepository = DrinkApiRepositoryImpl()

    fun updateDrinksLiveData(query: String) {
        apiRepository.updateDrinksLiveData(query)
    }

    override fun getLiveData(): MutableLiveData<List<Drink>> {
        return apiRepository.getLiveData()
    }

    override fun getCurrentData(): List<Drink> {
        return apiRepository.getCurrentData()
    }
}