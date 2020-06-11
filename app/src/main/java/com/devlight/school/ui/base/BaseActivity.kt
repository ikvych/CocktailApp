package com.devlight.school.ui.base

import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devlight.school.adapter.list.DrinkAdapter
import com.devlight.school.constant.ACTION_SHOW_DRINK_OFFER
import com.devlight.school.viewmodel.base.BaseViewModel
import com.devlight.school.model.entity.Drink
import com.devlight.school.receiver.FlyModeReceiver

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    lateinit var drinkAdapter: DrinkAdapter
    lateinit var viewModel : T
    private val flyModeReceiver: FlyModeReceiver = FlyModeReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
        registerReceiver(flyModeReceiver, filter)
    }

    fun initViewModel(viewModelClass: Class<T>) {
        viewModel = ViewModelProvider(this).get(viewModelClass)
    }

    fun initLiveDataObserver() {
        viewModel.getLiveData().observe(this, Observer { drinks ->
            drinkAdapter.drinkList = drinks
            determineVisibleLayerOnUpdateData(drinks)
        })
    }

    fun initRecyclerView(drinks: List<Drink>, recyclerViewId: Int, modelType: String) {
        val recyclerView: RecyclerView = findViewById(recyclerViewId)
        drinkAdapter = DrinkAdapter(this, modelType)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 4)
        }

        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = drinkAdapter

        determineVisibleLayerOnCreate(drinks)

        drinkAdapter.drinkList = drinks
    }

    /**
     * If drinks is empty, hide recyclerView and show appropriate textView,
     * else make recyclerView visible.
     *
     * To do this, use methods from ActivityUtil in util package
     *
     * @param drinks data list to check for items
     */
    open fun determineVisibleLayerOnCreate(drinks: List<Drink?>?) {
        //TO DO
    }

    /**
     * If drinks is empty, hide recyclerView and show appropriate textView,
     * else make recyclerView visible
     *
     * To do this, use methods from ActivityUtil in util package
     *
     * @param drinks data list to check for items
     */
    open fun determineVisibleLayerOnUpdateData(drinks: List<Drink?>?) {
        //TO DO
    }

    override fun onDestroy() {
        unregisterReceiver(flyModeReceiver)
        super.onDestroy()
    }
}