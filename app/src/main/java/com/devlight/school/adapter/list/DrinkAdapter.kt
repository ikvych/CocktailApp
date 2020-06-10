package com.devlight.school.adapter.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.devlight.school.R
import com.devlight.school.data.entity.Drink
import com.devlight.school.databinding.ItemDrinkListBinding

class DrinkAdapter(
    private val context: Context,
    private val activityName: String
) : RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {

    var drinkList: List<Drink> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val itemDrinkListBinding: ItemDrinkListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_drink_list,
            parent,
            false
        )
        return DrinkViewHolder(itemDrinkListBinding)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink: Drink = drinkList[position]
        holder.itemDrinkListBinding.drink = drink
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }


    inner class DrinkViewHolder(val itemDrinkListBinding: ItemDrinkListBinding) : RecyclerView.ViewHolder(itemDrinkListBinding.root) {
        init {
            itemDrinkListBinding.root.setOnClickListener { v ->
                var position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    var drink: Drink = drinkList[position]

                }
            }
        }
    }
}