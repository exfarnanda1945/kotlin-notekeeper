package com.example.notekeeper.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

//parcelable = Android's recommended way of passing your custom data structure between different components in your app
//https://medium.com/the-lazy-coders-journal/easy-parcelable-in-kotlin-the-lazy-coders-way-9683122f4c00

@Parcelize
@Entity("note_table")
data class Note(
    @PrimaryKey()
    val id:String,
    val isImportant:Boolean,
    val title:String,
    val description:String,
    val date:String
):Parcelable