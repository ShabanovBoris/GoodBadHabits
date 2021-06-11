package com.practice.goodbadhabits.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.practice.goodbadhabits.R
import kotlinx.coroutines.CoroutineScope

/**
 * handle night mode user preferences
 * @param activity for [SharedPreferences] access
 * @param toolBar with [icon] for switch modes
 * @see
 * Icon id will searching by
 *  findItem(R.id.night_mode_menu_icon)
 */
class NightDayModeHelper(
    private val activity: FragmentActivity,
    private val toolBar: androidx.appcompat.widget.Toolbar
) {
    private var prefs: SharedPreferences =
        activity.getSharedPreferences("ModeNightDay", Context.MODE_PRIVATE)
    private val icon = toolBar.menu.findItem(R.id.night_mode_menu_icon)

    fun nightModePreference() {
        val nightModeFlags = activity.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK

        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                prefs.edit {
                    putInt("mode", 1)
                }
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                prefs.edit {
                    putInt("mode", 0)
                }
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                prefs.edit {
                    putInt("mode", -1)
                }
            }
        }

        val mode = prefs.getInt("mode", -1)

        when (mode) {
            0 -> icon.setIcon(R.drawable.ic_dark_mode)
            1 -> icon.setIcon(R.drawable.ic_day_mode)
        }

        toolBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.night_mode_menu_icon)
            when (mode) {
                0 -> {
                    activity.getSharedPreferences("ModeNightDay", Context.MODE_PRIVATE)
                        .edit {
                            putInt("mode", 1)
                        }
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                1 -> {
                    activity.getSharedPreferences("ModeNightDay", Context.MODE_PRIVATE)
                        .edit {
                            putInt("mode", 0)
                        }
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                else -> {
                    Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show()
                }
            }

           return@setOnMenuItemClickListener true
        }
    }
}