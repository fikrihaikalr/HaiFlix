package com.fikrihaikal.haiflix.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fikrihaikal.haiflix.core.domain.model.Caster
import com.fikrihaikal.haiflix.core.utils.commonImageUrl
import com.fikrihaikal.haiflix.core.utils.loadImage
import com.fikrihaikal.haiflix.databinding.ItemCastAndCrewBinding

class CasterAdapter(private val itemClick: (Caster) -> Unit) :
    RecyclerView.Adapter<CasterAdapter.CasterViewHolder>() {


    private var items: MutableList<Caster> = mutableListOf()

    fun setItems(items: List<Caster>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CasterViewHolder {
        val binding = ItemCastAndCrewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CasterViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: CasterViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class CasterViewHolder(
        private val binding: ItemCastAndCrewBinding,
        val itemClick: (Caster) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Caster) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.run {
                    tvCaster.text = name
                    ivCaster.loadImage(itemView.context, img.commonImageUrl())
                }
            }

        }
    }

}