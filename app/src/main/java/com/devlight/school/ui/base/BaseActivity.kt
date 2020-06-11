package com.devlight.school.ui.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devlight.school.R
import com.devlight.school.adapter.list.DrinkAdapter
import com.devlight.school.constant.*
import com.devlight.school.viewmodel.base.BaseViewModel
import com.devlight.school.model.entity.Drink
import com.devlight.school.receiver.FlyModeReceiver
import com.devlight.school.ui.activity.DrinkDetailActivity
import com.google.android.material.snackbar.Snackbar
import java.util.*

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

    inner class DrinkOfferReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val listDrink: List<Drink> = viewModel.getCurrentData()
            if (listDrink.isEmpty()) {
                return
            }
            val view: View = findViewById(R.id.recycler_view)

            val pendingResult: PendingResult = goAsync()
            val asyncTask = Task(pendingResult, view, listDrink, context)
            asyncTask.execute(intent)
        }

    }

    private class Task(
        private val pendingResult: BroadcastReceiver.PendingResult,
        private val view: View,
        private val drinks: List<Drink>,
        private val context: Context
    ) : AsyncTask<Intent, Unit, Unit>() {

        override fun doInBackground(vararg intent: Intent?) {
            val drinkId: Long = intent[0]!!.getLongExtra(DRINK_ID, -1L)

            val random = Random()
            var randomValue: Int
            val drink: Drink
            do {
                randomValue = random.nextInt(drinks.size)
            } while (drinks[randomValue].getIdDrink() == drinkId)

            drink = drinks[randomValue]

            Snackbar.make(view, "Як щодо - ${drink.getStrDrink()}", 3500)
                .setAction("Переглянути") {
                    val newIntent = Intent(context, DrinkDetailActivity::class.java)
                    newIntent.putExtra(VIEW_MODEL_TYPE, SEARCH_MODEL_TYPE)
                    newIntent.putExtra(DRINK, drink)
                    context.startActivity(newIntent)
                }.show()
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            pendingResult.finish()
        }

    }
}