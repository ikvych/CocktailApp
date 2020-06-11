package com.devlight.school.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.devlight.school.R
import com.devlight.school.constant.*
import com.devlight.school.ui.base.BaseActivity
import com.devlight.school.model.entity.Drink
import com.devlight.school.databinding.ActivityDrinkDetailBinding
import com.devlight.school.service.DrinkOfferService
import com.devlight.school.viewmodel.MainActivityViewModel

class DrinkDetailActivity : BaseActivity<MainActivityViewModel>() {

    private var drink: Drink? = null
    private var modelType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_detail)

        val intent = intent

        if (intent != null && intent.hasExtra(DRINK)) {
            drink = intent.getParcelableExtra(DRINK)
            if (intent.hasExtra(VIEW_MODEL_TYPE)) {
                val viewModelType = intent.getStringExtra(VIEW_MODEL_TYPE)
                if (viewModelType != null) {
                    when (viewModelType) {
                        MAIN_MODEL_TYPE -> {
                            modelType = MAIN_MODEL_TYPE
                        }
                        SEARCH_MODEL_TYPE -> {
                            modelType = SEARCH_MODEL_TYPE
                            saveDrinkIntoDb(drink!!)
                        }
                    }
                }
            }
        }

        val activityDrinkDetailsBinding: ActivityDrinkDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_drink_detail)
        activityDrinkDetailsBinding.drink = drink
    }

    private fun saveDrinkIntoDb(drink: Drink) {
        val mainActivityViewModel: MainActivityViewModel = AndroidViewModelFactory(application)
            .create(MainActivityViewModel::class.java)
        mainActivityViewModel.saveDrink(drink)
    }

    fun resumePreviousActivity(view: View?) {
        finish()
    }

    override fun onDestroy() {
        if (modelType.equals(SEARCH_MODEL_TYPE)) {
            val intent = Intent(this, DrinkOfferService::class.java)
            intent.putExtra(DRINK_ID, drink?.getIdDrink())
            startService(intent)
        }
        super.onDestroy()
    }
}
