package com.devlight.school.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devlight.school.data.entity.Drink

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    abstract fun getCurrentData(): List<Drink>

    abstract fun getLiveData(): MutableLiveData<List<Drink>>
}