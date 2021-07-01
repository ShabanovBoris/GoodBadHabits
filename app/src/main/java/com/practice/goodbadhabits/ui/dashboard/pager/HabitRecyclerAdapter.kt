package com.practice.goodbadhabits.ui.dashboard.pager

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practice.goodbadhabits.databinding.HabitCardItemBinding
import com.practice.data.utils.toISOFormat
import java.util.*

class HabitRecyclerAdapter(
    private var cardItemBinding: HabitCardItemBinding? = null,
    private val appContext: Context
) :
    ListAdapter<com.practice.domain.entities.Habit, HabitRecyclerAdapter.ViewHolderHabit>(
        DiffCallback()
    ) {
    private val binding get() = requireNotNull(cardItemBinding)

    private var onEdit: ((habit: com.practice.domain.entities.Habit) -> Unit)? = null
    private var onDoneCheck: ((habitId: String, button: CompoundButton) -> Unit)? = null

    fun setOnEditListener(action: (habit: com.practice.domain.entities.Habit) -> Unit) {
        onEdit = action
    }
    fun setOnDoneCheckListener(action: (habitId: String, button: CompoundButton) -> Unit) {
        onDoneCheck = action
    }

    class ViewHolderHabit(
        private val binding: HabitCardItemBinding,
        private val appContext: Context,
        private val onEdit: ((habit: com.practice.domain.entities.Habit) -> Unit)?,
        private val onDoneCheck: ((habitId: String, button: CompoundButton) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: com.practice.domain.entities.Habit) {
            binding.apply {
                mainTitle.text = item.title
                tvRepeat.text = "${item.count} times every ${item.repeat} days"
                ivColor.setColorFilter(appContext.getColor(item.colorId))
                tvPriority.text = com.practice.domain.entities.Habit.Priority.values()[item.priority].name

                if (item.doneDates.isNullOrEmpty()){
                    tvLastDoneDate.text = ""
                    cbDoneHabit.text = ""
                }else{
                    tvLastDoneDate.text = Date(item.doneDates.last()).toISOFormat()
                    cbDoneHabit.text = item.doneDates.size.toString()
                    cbDoneHabit.isChecked = !item.doneDates.contains(0)
                }


                cbDoneHabit.setOnClickListener {
                    Toast.makeText(appContext, "You have done ${item.title} today", Toast.LENGTH_SHORT).show()
                    onDoneCheck?.invoke(item.id, it as CompoundButton)
                }

                root.setOnClickListener {
                    onEdit?.invoke(item)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHabit {
        cardItemBinding = HabitCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderHabit(binding, appContext, onEdit, onDoneCheck)
    }


    override fun onBindViewHolder(holder: ViewHolderHabit, position: Int) {
        holder.bind(getItem(position))
    }
}

private class DiffCallback : DiffUtil.ItemCallback<com.practice.domain.entities.Habit>() {

    override fun areItemsTheSame(oldItem: com.practice.domain.entities.Habit, newItem: com.practice.domain.entities.Habit): Boolean =
        newItem == oldItem


    override fun areContentsTheSame(oldItem: com.practice.domain.entities.Habit, newItem: com.practice.domain.entities.Habit): Boolean =
        newItem.title == oldItem.title
}