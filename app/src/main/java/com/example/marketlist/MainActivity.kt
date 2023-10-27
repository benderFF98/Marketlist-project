package com.example.marketlist

import AddItemDialogFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), ItemAdapter.OnItemRemoveListener {
    private val itemList = mutableListOf<String>() // Declare itemList as a class-level property
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the button by its ID
        val addItemButton = findViewById<Button>(R.id.addItemButton)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ItemAdapter(itemList) // Initialize the adapter here

        // Set the item removal listener
        adapter.setOnItemRemoveListener(this) // Assuming your MainActivity implements the OnItemRemoveListener

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Set a click listener for the button
        addItemButton.setOnClickListener {
            // Add your code here to respond to the button click
            val dialogFragment = AddItemDialogFragment()
            dialogFragment.setItemListAndAdapter(itemList, adapter)
            dialogFragment.show(supportFragmentManager, "addItemDialog")
        }
    }

    override fun onItemRemove(position: Int) {
        if (position >= 0 && position < itemList.size) {
            // Log the itemList before item removal
            Log.d("MainActivity", "Item list before removal: $itemList")

            itemList.removeAt(position)
            adapter.notifyItemRemoved(position)

            // Log the itemList after item removal
            Log.d("MainActivity", "Item list after removal: $itemList")
        }
    }
}
