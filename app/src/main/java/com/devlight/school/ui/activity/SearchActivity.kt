package com.devlight.school.ui.activity

import android.content.IntentFilter
import android.os.Bundle
import android.widget.SearchView
import com.devlight.school.R
import com.devlight.school.constant.*
import com.devlight.school.ui.base.BaseActivity
import com.devlight.school.model.entity.Drink
import com.devlight.school.util.setEmptySearchVisible
import com.devlight.school.util.setSearchEmptyListVisible
import com.devlight.school.util.setSearchRecyclerViewVisible
import com.devlight.school.viewmodel.SearchActivityViewModel

class SearchActivity : BaseActivity<SearchActivityViewModel>() {

    lateinit var searchView: SearchView
    private val drinkOfferReceiver: DrinkOfferReceiver = DrinkOfferReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViewModel(SearchActivityViewModel::class.java)
        initRecyclerView(viewModel.getCurrentData(), R.id.recycler_view, SEARCH_MODEL_TYPE)
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


}
