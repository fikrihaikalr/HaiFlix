package com.fikrihaikal.haiflix.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.haiflix.core.domain.model.Movie
import com.fikrihaikal.haiflix.core.utils.commonImageUrl
import com.fikrihaikal.haiflix.core.utils.loadImage
import com.fikrihaikal.haiflix.databinding.ItemListFavoriteBinding
import com.fikrihaikal.haiflix.databinding.ItemListMovieBinding

class FavoriteAdapter(private val itemClick: (Movie) -> Unit,
    private val itemDelete: (Movie) -> Unit ) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {


    private var items: MutableList<Movie> = mutableListOf()

    fun setItems(items: List<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemListFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding, itemClick, itemDelete)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class FavoriteViewHolder(
        private val binding: ItemListFavoriteBinding,
        val itemClick: (Movie) -> Unit,
        val itemDelete: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Movie) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                itemView.setOnLongClickListener {
                    itemDelete(this)
                    true }
                binding.run {
                    tvName.text = name
                    tvRating.text = voteAverage.toString()
                    ivMovie.loadImage(itemView.context, img?.commonImageUrl())
                }
            }

        }
    }

}