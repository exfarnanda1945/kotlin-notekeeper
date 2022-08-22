package com.example.roomdatabase.helpers

import android.text.Editable
import android.text.TextUtils

class ValidationForm {
    companion object{
        fun validate(firstName: String, lastName: String, age: Editable) :Boolean{
            return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
        }
    }
}