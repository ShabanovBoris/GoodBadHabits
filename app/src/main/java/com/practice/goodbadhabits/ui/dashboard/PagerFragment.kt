package com.practice.goodbadhabits.ui.dashboard

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.practice.goodbadhabits.R


class PagerFragment : Fragment(R.layout.fragment_pager) {

    companion object{
        enum class HabitType{
            GOOD,
            BAD
        }
        const val TYPE = "type"
    }

    override fun onStart() {
        super.onStart()


        val arg =
        arguments?.getSerializable(TYPE)

        Toast.makeText(requireContext(), (arg as HabitType).name, Toast.LENGTH_SHORT).show()
    }


}