package com.devlight.school.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import com.devlight.school.R
import com.devlight.school.constant.*
import com.devlight.school.ui.base.BaseActivity
import com.devlight.school.model.entity.Drink
import com.devlight.school.util.setEmptySearchVisible
import com.devlight.school.util.setSearchEmptyListVisible
import com.devlight.school.util.setSearchRecyclerViewVisible
import com.devlight.school.viewmodel.SearchActivityViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class SearchActivity : BaseActivity<SearchActivityViewModel>() {

    lateinit var searchView: SearchView
    private val drinkOfferReceiver: DrinkOfferReceiver = DrinkOfferReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViewModel(SearchActivityViewModel::class.java)
        initRecyclerView(viewModel.getCurrentData(), R.id.search_recycler_view, SEARCH_MODEL_TYPE)
        initLiveDataObserver()
        initSearchView()

        val filter = IntentFilter().apply {
            addAction(ACTION_SHOW_DRINK_OFFER)
        }
        registerReceiver(drinkOfferReceiver, filter)
    }

    override fun onDestroy() {
        unregisterReceiver(drinkOfferReceiver)
        super.onDestroy()
    }

    override fun determineVisibleLayerOnCreate(drinks: List<Drink?>?) {
        if (drinks!!.isEmpty()) {
            setSearchEmptyListVisible(this@SearchActivity)
        } else {
            setSearchRecyclerViewVisible(this@SearchActivity)
        }
    }

    override fun determineVisibleLayerOnUpdateData(drinks: List<Drink?>?) {
        if (drinks!!.isEmpty()) {
            setEmptySearchVisible(this@SearchActivity)
        } else {
            setSearchRecyclerViewVisible(this@SearchActivity)
        }
    }

    private fun initSearchView() {
        searchView = findViewById(R.id.search_query)
        searchView.isIconifiedByDefault = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchQuery: String = query?.trim() ?: ""
                viewModel.updateDrinksLiveData(searchQuery)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    inner class DrinkOfferReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val listDrink: List<Drink> = viewModel.getCurrentData()
            if (listDrink.isEmpty()) {
                return
            }
            val view: View = findViewById(R.id.search_recycler_view)

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
