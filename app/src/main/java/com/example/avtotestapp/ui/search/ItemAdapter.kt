package com.example.avtotestapp.ui.search

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.avtotestapp.data.RepData
import com.example.avtotestapp.databinding.ItemBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.log

class ItemAdapter(private val listener : ClickListener) : PagingDataAdapter<RepData, ItemViewHolder>(DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<RepData>() {
        override fun areItemsTheSame(oldItem: RepData, newItem: RepData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: RepData, newItem: RepData): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)

        holder.itemView.setOnClickListener {
            if (item != null) {
                listener.onClick(item.owner.login)
            }
        }

        if (item != null) {
            holder.bind(item)
        }
    }

    class ClickListener(val clickListener: (login:String) -> Unit) {
        fun onClick(login: String) = clickListener(login)
    }

}

class ItemViewHolder(private val binding: ItemBinding):
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: RepData) {

        binding.name.text = item.name
        binding.description.text = item.description
        binding.language.text = item.language
        binding.stars.text = item.stargazers_count.toString()
        val formatter = DateTimeFormatter.ofPattern("d MMM uuu")
        binding.data.text = "Updated at ${item.updated_at.format(formatter)}"

        Glide.with(itemView.context).load(item.owner.avatar_url).into(binding.imageView)

    }
}