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
import com.practice.domain.entities.Habit
import com.practice.domain.entities.HabitManager
import java.util.*

class HabitRecyclerAdapter(
    private var cardItemBinding: HabitCardItemBinding? = null,
    private val appContext: Context
) :
    ListAdapter<Habit, HabitRecyclerAdapter.ViewHolderHabit>(
        DiffCallback()
    ) {
    private val binding get() = requireNotNull(cardItemBinding)

    private var onEdit: ((habit: Habit) -> Unit)? = null
    private var onDoneCheck: ((habit: Habit, button: CompoundButton) -> Unit)? = null

    fun setOnEditListener(action: (habit: Habit) -> Unit) {
        onEdit = action
    }
    fun setOnDoneCheckListener(action: (habit: Habit, button: CompoundButton) -> Unit) {
        onDoneCheck = action
    }

    class ViewHolderHabit(
        private val binding: HabitCardItemBinding,
        private val appContext: Context,
        private val onEdit: ((habit: Habit) -> Unit)?,
        private val onDoneCheck: ((habit: Habit, button: CompoundButton) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Habit) {
            binding.apply {
                mainTitle.text = item.title
                tvRepeat.text = "${item.count} times every ${item.repeatDays} days"
                ivColor.setColorFilter(appContext.getColor(item.colorId))
                tvPriority.text = Habit.Priority.values()[item.priority].name

                if (item.doneDates.isNullOrEmpty()){
                    tvLastDoneDate.text = ""
                    cbDoneHabit.text = ""
                }else{
                    tvLastDoneDate.text = Date(item.doneDates.last()).toISOFormat()
                    cbDoneHabit.text = item.doneDates.size.toString()
                    cbDoneHabit.isChecked = item.isCompleted
                }


                cbDoneHabit.setOnClickListener {
                    Toast.makeText(appContext,
                        HabitManager(item).showMessage(true),
                        Toast.LENGTH_SHORT).show()
                    onDoneCheck?.invoke(item, it as CompoundButton)
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

private class DiffCallback : DiffUtil.ItemCallback<Habit>() {

    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean =
        newItem == oldItem


    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean =
        newItem.title == oldItem.title
}