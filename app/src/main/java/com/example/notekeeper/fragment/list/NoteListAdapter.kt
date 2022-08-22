package com.example.notekeeper.fragment.list

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notekeeper.R
import com.example.notekeeper.models.Note
import com.example.notekeeper.utils.Converters
import com.example.notekeeper.utils.Utils

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {
    private var noteList: List<Note> = emptyList()

    private var colors: List<Int> = listOf(
        R.color.smooth_blue,
        R.color.smooth_orange,
        R.color.smooth_yellow,
        R.color.smooth_green,
        R.color.smooth_blue,
        R.color.smooth_pink,
        R.color.smooth_teal
    )

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.rv_title_item)
        val date:TextView = itemView.findViewById(R.id.rv_date_item)
        var layoutContainer: RelativeLayout = itemView.findViewById(R.id.rv_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_card_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem= noteList[position]
        val title = currentItem.title
        val date = Converters().stringToDate(currentItem.date)

        holder.title.text = title
        holder.date.text = Utils.convertDateToString(date.dayOfMonth,date.monthValue,date.year)

        val layoutContainer  = holder.layoutContainer

        // set color programmatically
        val color = getColor(position,holder.layoutContainer.context)
        layoutContainer.setBackgroundColor(Color.parseColor(color))

        // set minimal height programmatically
        layoutContainer.minimumHeight = getMinHeight(position)


        holder.itemView.setOnClickListener{
            onClickToDetail(currentItem,holder.itemView)
        }

    }

    private fun onClickToDetail(item: Note,view:View) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(item)
        view.findNavController().navigate(action)

    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setData(noteList: List<Note>) {
        this.noteList = noteList
        notifyDataSetChanged()
    }

    private fun getColor(index: Int, ctx: Context): String {
        val color = colors[index % colors.size]
        val convertColorToInt = ContextCompat.getColor(ctx, color)
        return "#${Integer.toHexString(convertColorToInt)}"
    }

    private fun getMinHeight(index:Int):Int{
        var minHeight:Int = 300
        when(index % 4){
            0 -> {
                minHeight = 350
            }
            1 -> {
                minHeight = 450
            }
            2 -> {
                minHeight = 450
            }
            3 -> {
                minHeight = 350
            }
        }

        return minHeight
    }
}