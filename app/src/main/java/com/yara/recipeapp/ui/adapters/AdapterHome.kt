package com.yara.recipeapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yara.recipeapp.data.remote.models.Meal
import com.yara.recipeapp.databinding.ItemRecipeBinding

class AdapterHome(private var productList : List<Meal>) : RecyclerView.Adapter<AdapterHome.ViewHolder>() {

    private var onItemClick: ((Meal) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRecipeBinding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Meal) {
            binding.title.text = recipe.strMeal
            Glide.with(binding.image.context)
                .load(recipe.strMealThumb)
                .into(binding.image)
            binding.root.setOnClickListener {
                onItemClick?.invoke(recipe)
            }
        }
    }

    fun setOnItemClickListener(listener: (Meal) -> Unit) {
        onItemClick = listener
    }

    fun update(newList: List<Meal>) {
        productList = newList
        notifyDataSetChanged()
    }
}