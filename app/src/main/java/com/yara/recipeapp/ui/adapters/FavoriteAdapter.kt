package com.yara.recipeapp.ui.favourites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yara.recipeapp.data.local.entities.FavouriteEntity
import com.yara.recipeapp.databinding.ItemFavouriteBinding

class FavouritesAdapter(private val onItemClick: (FavouriteEntity) -> Unit) :
    ListAdapter<FavouriteEntity, FavouritesAdapter.FavouritesViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavouriteEntity>() {
            override fun areItemsTheSame(oldItem: FavouriteEntity, newItem: FavouriteEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavouriteEntity, newItem: FavouriteEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val binding = ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
    }

    inner class FavouritesViewHolder(private val binding: ItemFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite: FavouriteEntity) {
            binding.apply {
                textViewTitle.text = favorite.mealName

                Glide.with(imageView.context)
                    .load(favorite.mealThumb)
                    .into(imageView)

                itemView.setOnClickListener {
                    onItemClick(favorite)
                }

                deleteIcon.setOnClickListener {
                    onItemClick(favorite)
                }
            }
        }
    }
}
