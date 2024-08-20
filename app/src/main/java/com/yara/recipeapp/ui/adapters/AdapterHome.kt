package com.yara.recipeapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yara.recipeapp.data.remote.models.Meal
import com.yara.recipeapp.databinding.ItemRecipeBinding

class AdapterHome(private var productList : List<Meal>) :
    RecyclerView.Adapter<AdapterHome.ViewHolder>() {

    private lateinit var onClick : (Int) -> Unit
    fun setOnClick(onClick : (Int) -> Unit)
    {
        this.onClick = onClick
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRecipeBinding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text = productList[position].strMeal
        Glide.with(holder.itemView.context).load(productList[position].strMealThumb)
            .into(holder.binding.image)
    }

    override fun getItemCount(): Int {
        return productList?.size?:0
    }


    inner class ViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    fun update(newList:List<Meal>){
        productList = newList
        notifyDataSetChanged()
    }
}