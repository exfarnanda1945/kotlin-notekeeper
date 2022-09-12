package com.example.notekeeper.fragment.list

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notekeeper.R
import com.example.notekeeper.models.Note
import com.example.notekeeper.utils.Converters
import com.example.notekeeper.utils.Utils

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    private lateinit var onItemCallback: OnItemCallback

    private val diffcallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffcallback)

    fun setOnItemCallback(act: OnItemCallback) {
        this.onItemCallback = act
    }

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
        val date: TextView = itemView.findViewById(R.id.rv_date_item)
        var layoutContainer: RelativeLayout = itemView.findViewById(R.id.rv_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_card_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        val title = currentItem.title
        val date = Converters().stringToDate(currentItem.date)

        holder.title.text = title
        holder.date.text = Utils.convertDateToString(
            date.dayOfMonth,
            date.monthValue,
            date.year,
            holder.itemView.context
        )

        val layoutContainer = holder.layoutContainer

        // set color programmatically
        val color = getColor(position, holder.layoutContainer.context)
        layoutContainer.setBackgroundColor(Color.parseColor(color))

        // set minimal height programmatically
        layoutContainer.minimumHeight = getMinHeight(position)


        holder.itemView.setOnClickListener {
            onItemCallback.onItemClicked(differ.currentList[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(noteList: List<Note>) {
        differ.submitList(noteList)
    }

    private fun getColor(index: Int, ctx: Context): String {
        val color = colors[index % colors.size]
        val convertColorToInt = ContextCompat.getColor(ctx, color)
        return "#${Integer.toHexString(convertColorToInt)}"
    }

    private fun getMinHeight(index: Int): Int {
        var minHeight = 300
        when (index % 4) {
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

    interface OnItemCallback {
        fun onItemClicked(value: Note)
    }
}