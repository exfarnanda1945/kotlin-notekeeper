package com.example.notekeeper.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.notekeeper.R

class Utils{
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

        fun convertDateToString(day: Int, month: Int, year: Int,context: Context): String {
            return "$day ${getMonthString(month,context)} $year"
        }

        private fun getMonthString(month: Int,context: Context): String {
            return when (month) {
                1 -> context.getString(R.string.january)
                2 -> context.getString(R.string.february)
                3 -> context.getString(R.string.march)
                4 -> context.getString(R.string.april)
                5 -> context.getString(R.string.may)
                6 -> context.getString(R.string.june)
                7 -> context.getString(R.string.july)
                8 -> context.getString(R.string.august)
                9 -> context.getString(R.string.september)
                10 -> context.getString(R.string.october)
                11 -> context.getString(R.string.november)
                else -> context.getString(R.string.december)
            }
        }
    }
}