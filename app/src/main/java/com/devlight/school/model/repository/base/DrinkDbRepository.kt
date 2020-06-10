package com.devlight.school.model.repository.base

import androidx.lifecycle.LiveData
import com.devlight.school.model.entity.Drink

interface DrinkDbRepository {

    fun getDrinks(): LiveData<List<Drink>>

    fun saveDrink(drink: Drink)

}