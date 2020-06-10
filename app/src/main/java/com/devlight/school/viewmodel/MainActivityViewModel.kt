package com.devlight.school.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.devlight.school.viewmodel.base.BaseViewModel
import com.devlight.school.model.repository.base.DrinkDbRepository
import com.devlight.school.model.entity.Drink
import com.devlight.school.model.repository.DrinkDbRepositoryImpl

class MainActivityViewModel(
    application: Application
) : BaseViewModel(application) {

    private val dbRepository: DrinkDbRepository = DrinkDbRepositoryImpl(application)
    private val drinksLiveData: LiveData<List<Drink>> = dbRepository.getDrinks()

    override fun getCurrentData(): List<Drink> {
        return drinksLiveData.value ?: emptyList()
    }

    fun saveDrink(drink: Drink) {
        dbRepository.saveDrink(drink)
    }

    override fun getLiveData(): LiveData<List<Drink>> {
        return drinksLiveData
    }
}