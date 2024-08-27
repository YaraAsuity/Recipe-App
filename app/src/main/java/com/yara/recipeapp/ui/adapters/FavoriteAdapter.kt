package com.yara.recipeapp.ui.favourites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yara.recipeapp.R
import com.yara.recipeapp.data.local.entities.FavouriteEntity
import com.yara.recipeapp.databinding.ItemFavouriteBinding
// In FavouritesAdapter.kt

class FavouritesAdapter(
    private val onItemClicked: (FavouriteEntity) -> Unit,
    private val onDeleteClicked: (FavouriteEntity) -> Unit
) : ListAdapter<FavouriteEntity, FavouritesAdapter.FavouritesViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favourite, parent, false)
        return FavouritesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
    }

    inner class FavouritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.textViewTitle)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val deleteIcon: ImageButton = itemView.findViewById(R.id.deleteIcon)

        fun bind(favorite: FavouriteEntity) {
            title.text = favorite.mealName
            // Use Glide or other image loading libraries to load images
            Glide.with(itemView.context)
                .load(favorite.mealThumb)
                .placeholder(R.drawable.placeholder)
                .into(imageView)

            itemView.setOnClickListener {
                onItemClicked(favorite)
            }

            deleteIcon.setOnClickListener {
                onDeleteClicked(favorite)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavouriteEntity>() {
            override fun areItemsTheSame(oldItem: FavouriteEntity, newItem: FavouriteEntity): Boolean {
                return oldItem.mealId == newItem.mealId
            }

            override fun areContentsTheSame(oldItem: FavouriteEntity, newItem: FavouriteEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
