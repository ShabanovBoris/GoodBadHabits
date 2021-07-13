package com.practice.goodbadhabits.ui.dashboard.pager

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_POINT_MARK
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practice.data.utils.toISOFormat
import com.practice.domain.entities.Habit
import com.practice.domain.entities.HabitManager
import com.practice.goodbadhabits.HabitApplication
import com.practice.goodbadhabits.databinding.HabitCardItemBinding
import java.util.*

class HabitRecyclerAdapter(
    private var cardItemBinding: HabitCardItemBinding? = null,
    private val appContext: Context
) :
    ListAdapter<Habit, HabitRecyclerAdapter.ViewHolderHabit>(
        DiffCallback()
    ) {
    private val binding get() = checkNotNull(cardItemBinding)

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
        private val appContext: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        private val spannableStringBuilder = SpannableStringBuilder()

        fun bind(item: Habit) {
            require(appContext is HabitApplication)
            spannableStringBuilder.clear()

            binding.apply {
                mainTitle.text = item.title

                tvRepeat.text = spannableStringBuilder.apply {
                    append(item.count.toString(), RelativeSizeSpan(1.5f), SPAN_POINT_MARK)
                    append(" times every ")
                    append(item.repeatDays.toString(), RelativeSizeSpan(1.5f), SPAN_POINT_MARK)
                    append(" days")
                }

                ivColor.setColorFilter(appContext.getColor(item.colorId))
                tvPriority.text = Habit.Priority.values()[item.priority].name

                if (item.doneDates.isNullOrEmpty()) {
                    tvLastDoneDate.text = ""
                    cbDoneHabit.text = ""
                } else {
                    tvLastDoneDate.text = Date(item.doneDates.last()).toISOFormat()
                    cbDoneHabit.text = item.doneDates.size.toString()
                    cbDoneHabit.isChecked = item.isCompleted
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
        return ViewHolderHabit(binding, appContext).apply {
            binding.root.setOnClickListener {
                onEdit?.invoke(getItem(bindingAdapterPosition))
            }
            binding.cbDoneHabit.setOnClickListener {
                Toast.makeText(
                    appContext,
                    HabitManager(getItem(bindingAdapterPosition)).showMessage(true),
                    Toast.LENGTH_SHORT
                ).show()
                onDoneCheck?.invoke(getItem(bindingAdapterPosition), it as CompoundButton)
            }
        }
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