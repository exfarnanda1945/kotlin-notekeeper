package com.example.roomdatabase.fragments.list

import androidx.recyclerview.widget.DiffUtil
import com.example.roomdatabase.models.User

class ListDiffUtil(private val oldList:List<User>,private val newList:List<User>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }
}