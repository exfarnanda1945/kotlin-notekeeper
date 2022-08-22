package com.example.roomdatabase.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.models.User

class ListUserAdapter:RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    private var userList: List<User> = emptyList<User>()
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var tvNum:TextView = itemView.findViewById(R.id.tv_number)
        var tvFirstName:TextView = itemView.findViewById(R.id.tv_firstname)
        var tvLastName:TextView= itemView.findViewById(R.id.tv_lastname)
        var tvAge:TextView = itemView.findViewById(R.id.tv_age)
        var customRowLayout:ConstraintLayout = itemView.findViewById(R.id.custom_item_row)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]

        holder.tvNum.text = "${currentItem.id}. "
        holder.tvFirstName.text = currentItem.firstName
        holder.tvLastName.text =currentItem.lastName
        holder.tvAge.text = "(${currentItem.age})"

        holder.customRowLayout.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(user:List<User>){
        this.userList = user
        notifyDataSetChanged()
    }

}