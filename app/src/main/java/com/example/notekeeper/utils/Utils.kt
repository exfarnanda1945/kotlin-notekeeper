package com.example.notekeeper.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.notekeeper.R

class Utils {
    companion object {
        fun setTintIcon(context: Context, icon: Int, color: Int) {
            val iconDrawable: Drawable = AppCompatResources.getDrawable(context, icon)!!
            DrawableCompat.setTint(iconDrawable, ContextCompat.getColor(context, color))
        }

        fun validateForm(title: String, desc: String): Boolean {
            return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(desc))
        }

        fun useToast(context: Context, message: String, toastLength: Int) {
            Toast.makeText(context, message, toastLength).show()
        }

        fun convertDateToString(day: Int, month: Int, year: Int): String {
            return "$day ${getMonthString(month)} $year"
        }

        private fun getMonthString(month: Int): String {
            return when (month) {
                1 -> "Januari"
                2 -> "Februari"
                3 -> "Maret"
                4 -> "April"
                5 -> "Mei"
                6 -> "Juni"
                7 -> "Juli"
                8 -> "Agustus"
                9 -> "September"
                10 -> "Oktober"
                11 -> "November"
                12 -> "Desember"
                else -> "Januari"
            }
        }
    }
}