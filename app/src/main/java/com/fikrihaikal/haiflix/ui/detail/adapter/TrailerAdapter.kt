package com.fikrihaikal.haiflix.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.haiflix.core.domain.model.MovieVideo
import com.fikrihaikal.haiflix.core.utils.commonYoutubeUrl
import com.fikrihaikal.haiflix.databinding.ItemTrailerBinding

class TrailerAdapter(private val itemClick: (MovieVideo) -> Unit) :
    RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {


    private var items: MutableList<MovieVideo> = mutableListOf()

    fun setItems(items: List<MovieVideo>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val binding = ItemTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrailerViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class TrailerViewHolder(
        private val binding: ItemTrailerBinding,
        val itemClick: (MovieVideo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: MovieVideo) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.wvTrailer.apply {
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    webChromeClient = WebChromeClient()
                    loadUrl(key.commonYoutubeUrl())
                }
            }

        }
    }

}