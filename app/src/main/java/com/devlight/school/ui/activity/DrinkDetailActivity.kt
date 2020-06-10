package com.devlight.school.ui.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.devlight.school.R
import com.devlight.school.ui.base.BaseActivity
import com.devlight.school.constant.DRINK
import com.devlight.school.constant.MAIN_MODEL_TYPE
import com.devlight.school.constant.SEARCH_MODEL_TYPE
import com.devlight.school.constant.VIEW_MODEL_TYPE
import com.devlight.school.model.entity.Drink
import com.devlight.school.databinding.ActivityDrinkDetailBinding
import com.devlight.school.viewmodel.MainActivityViewModel

class DrinkDetailActivity : BaseActivity<MainActivityViewModel>() {

    private var drink: Drink? = null

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
                        }
                        SEARCH_MODEL_TYPE -> saveDrinkIntoDb(drink!!)
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
}
