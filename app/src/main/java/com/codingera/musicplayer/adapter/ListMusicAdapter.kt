package com.codingera.musicplayer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.codingera.musicplayer.R
import com.codingera.musicplayer.databinding.ItemListMusicBinding
import com.codingera.musicplayer.model.ModelListMusic

class ListMusicAdapter(
    private val context: Context,
    private val items: List<ModelListMusic>,
    private val onSelectData: (ModelListMusic) -> Unit
) : RecyclerView.Adapter<ListMusicAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemListMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ModelListMusic) {
            with(binding) {
                tvBand.text = data.strNamaBand
                tvTitleMusic.text = data.strJudulMusic

                Glide.with(context)
                    .load(data.strCoverLagu)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.ic_no_image)
                    .into(imgCover)

                cvListMusic.setOnClickListener { onSelectData(data) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
