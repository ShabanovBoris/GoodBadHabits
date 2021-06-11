package com.practice.goodbadhabits.ui.dashboard.pager

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practice.goodbadhabits.databinding.HabitCardItemBinding
import com.practice.goodbadhabits.entities.Habit

class HabitRecyclerAdapter(private val appContext: Context) :
    ListAdapter<Habit, HabitRecyclerAdapter.ViewHolderHabit>(
        DiffCallback()
    ) {
    private var binding: HabitCardItemBinding? = null

    class ViewHolderHabit(view: View, private val binding: HabitCardItemBinding) :
        RecyclerView.ViewHolder(view) {

        private val title: TextView = binding.mainTitle
        private val coloredImage: AppCompatImageView = binding.ivColor
        private val textRepeat: TextView = binding.tvRepeat


        fun bind(item: Habit,appContext: Context) {
            title.text = item.title
            textRepeat.text = item.repeat
            coloredImage.setColorFilter(ContextCompat.getColor(appContext, item.color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHabit {
        binding = HabitCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderHabit(binding!!.root, binding!!)
    }


    override fun onBindViewHolder(holder: ViewHolderHabit, position: Int) {
        holder.bind(getItem(position),appContext)
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Habit>() {

    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean =
        newItem == oldItem


    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean =
        newItem.title == oldItem.title
}