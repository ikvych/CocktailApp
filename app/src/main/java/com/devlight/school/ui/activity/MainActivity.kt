package com.devlight.school.ui.activity

import android.content.Intent
import android.os.Bundle
import com.devlight.school.R
import com.devlight.school.ui.base.BaseActivity
import com.devlight.school.constant.MAIN_MODEL_TYPE
import com.devlight.school.model.entity.Drink
import com.devlight.school.util.setDbEmptyHistoryVisible
import com.devlight.school.util.setDbRecyclerViewVisible
import com.devlight.school.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainActivityViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel(MainActivityViewModel::class.java)
        initRecyclerView(viewModel.getCurrentData(), R.id.db_recycler_view, MAIN_MODEL_TYPE)
        initLiveDataObserver()

        fab.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    override fun determineVisibleLayerOnCreate(drinks: List<Drink?>?) {
        if (drinks!!.isEmpty()) {
            setDbEmptyHistoryVisible(this@MainActivity)
        } else {
            setDbRecyclerViewVisible(this@MainActivity)
        }
    }

    override fun determineVisibleLayerOnUpdateData(drinks: List<Drink?>?) {
        if (drinks!!.isEmpty()) {
            setDbEmptyHistoryVisible(this@MainActivity)
        } else {
            setDbRecyclerViewVisible(this@MainActivity)
        }
    }
}
