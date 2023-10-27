package com.example.marketlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val items: List<String>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    interface OnItemRemoveListener {
        fun onItemRemove(position: Int)
    }

    private var itemRemoveListener: OnItemRemoveListener? = null

    fun setOnItemRemoveListener(listener: OnItemRemoveListener) {
        itemRemoveListener = listener
        Log.d("com.example.marketlist.ItemAdapter", "Remove button clicked at position:")

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.textViewItemName)
        val removeButton: Button = itemView.findViewById(R.id.removeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemNameTextView.text = item

        // Set a click listener for the remove button
        holder.removeButton.setOnClickListener {
            val itemPosition = holder.adapterPosition
            itemRemoveListener?.onItemRemove(itemPosition)
            Log.d("com.example.marketlist.ItemAdapter", "Remove button clicked at position: $itemPosition")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}


