package com.ikvych.cocktail.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.ikvych.cocktail.R
import com.ikvych.cocktail.constant.*
import com.ikvych.cocktail.data.entity.Drink
import com.ikvych.cocktail.databinding.ActivityDrinkDetailsBinding
import com.ikvych.cocktail.service.ApplicationService
import com.ikvych.cocktail.ui.base.BaseActivity
import com.ikvych.cocktail.viewmodel.MainActivityViewModel


class DrinkDetailActivity : BaseActivity() {

    private var drink: Drink? = null
    private var modelType: String? = null
    var imgWidth: Int? = null
    var imgHeight: Int? = null

    var layoutHeight: Int? = null
    var beginHeight = 720
    var finishWidth = 96.5
    var finishHeight = 96.5

    private lateinit var appBarLayout: AppBarLayout
    private lateinit var imageView: ImageView
    private lateinit var linerLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_details)

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

        val activityDrinkDetailsBinding: ActivityDrinkDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_drink_details)
        activityDrinkDetailsBinding.drink = drink

        appBarLayout = findViewById<AppBarLayout>(R.id.abl)
        imageView = findViewById(R.id.iv_drink)
        linerLayout = findViewById(R.id.fl_image)

        appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

                if (layoutHeight == null) {
                    layoutHeight = imageView.width
                    linerLayout.layoutParams.height = layoutHeight!!
                    linerLayout.requestLayout()
                }

                val scale = resources.displayMetrics.density
                val dpAsPixels1 = 72 * scale + 0.5f
                val dpAsPixels2 = 18 * scale + 0.5f
                val dpAsPixels3 = 38 * scale + 0.5f

                val px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 72 * .0F, resources.displayMetrics
                ).toInt()

                val totalsScrollRange = appBarLayout.totalScrollRange
                val offsetFactor = (-verticalOffset).toFloat() / totalsScrollRange
                val minHeight = (100F - (dpAsPixels3 * 100F / layoutHeight!!)) / 100F
                val scaleFactor = 1F - offsetFactor * minHeight;



                val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

                params.marginStart = (px * offsetFactor).toInt()
                params.topMargin = ((layoutHeight!! / 2 - dpAsPixels2) * offsetFactor).toInt()
/*                params.bottomMargin = (dpAsPixels3 * offsetFactor).toInt()*/
                params.width = (layoutHeight!! * scaleFactor).toInt()
/*                linerLayout.layoutParams.width = (imgWidth!! * scaleFactor).toInt()
                linerLayout.layoutParams.height = (imgWidth!! * scaleFactor).toInt()*/

                imageView.layoutParams = params

               /* imageView.setPaddingRelative(0, 0, 0, (dpAsPixels2 * offsetFactor).toInt())

                imageView.layoutParams.width = (imgWidth!! * scaleFactor).toInt()*/

            })
    }

    private fun saveDrinkIntoDb(drink: Drink) {
        val mainActivityViewModel: MainActivityViewModel =
            ViewModelProvider.AndroidViewModelFactory(
                application
            )
                .create(MainActivityViewModel::class.java)
        mainActivityViewModel.saveDrink(drink)
    }

    fun resumePreviousActivity(view: View?) {
        finish()
    }

    override fun onDestroy() {
        if (modelType == SEARCH_MODEL_TYPE) {
            val intent = Intent(this, ApplicationService::class.java)
            intent.putExtra(DRINK_ID, drink?.getIdDrink())
            intent.action = ACTION_SHOW_DRINK_OFFER
            startService(intent)
        }
        super.onDestroy()
    }
}
