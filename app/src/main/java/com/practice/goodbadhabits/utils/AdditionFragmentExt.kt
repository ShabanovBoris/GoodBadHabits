package com.practice.goodbadhabits.utils

import android.widget.Toast
import com.practice.goodbadhabits.databinding.FragmentAdditionBinding
import com.practice.goodbadhabits.ui.addition.AdditionFragment


fun AdditionFragment.validateFields(binding: FragmentAdditionBinding): Boolean {
    val title = binding.tvTitle
    val description = binding.etDescription
    val frequency = binding.etEvery
    val countRepeat = binding.etRepeat

    if ((title.editText?.text.isNullOrEmpty()) or
        (frequency.editText?.text.isNullOrEmpty()) or
        (countRepeat.editText?.text.isNullOrEmpty()) or
        (description.editText?.text.isNullOrEmpty())
    ) {
        Toast.makeText(binding.root.context, "Please, fill in all the fields", Toast.LENGTH_SHORT)
            .show()
        return false
    }
    //color button
    if (checkedColor == null) {
        Toast.makeText(binding.root.context, "Please, choose the color", Toast.LENGTH_SHORT).show()
        return false
    }

    return true
}

