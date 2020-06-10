package com.devlight.school.model.repository

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.devlight.school.model.repository.base.DrinkDbRepository
import com.devlight.school.model.database.DrinkDao
import com.devlight.school.model.database.DrinkDataBase
import com.devlight.school.model.entity.Drink

class DrinkDbRepositoryImpl(context: Context):
    DrinkDbRepository {

    private val drinkDao: DrinkDao = DrinkDataBase.getInstance(context)!!.drinkDao()

    override fun getDrinks(): LiveData<List<Drink>> {
        return drinkDao.getAllDrinks()
    }

    override fun saveDrink(drink: Drink) {
        SaveDrinkAsyncTask(drinkDao).execute(drink)
    }

    private companion object class SaveDrinkAsyncTask(private val drinkDao: DrinkDao): AsyncTask<Drink, Unit, Unit>() {

        override fun doInBackground(vararg params: Drink) {
            params.forEach { drink ->  drinkDao.insert(drink)}
        }
    }

}