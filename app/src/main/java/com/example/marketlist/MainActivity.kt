package com.example.marketlist

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Item(val name: String, var isChecked: Boolean = false)

class MainActivity : AppCompatActivity(), ItemAdapter.OnItemRemoveListener {
    private val itemList = mutableListOf<Item>() // Use the Item data class
    private lateinit var adapter: ItemAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedPrefName = "MySharedPreferences"
    private val itemListKey = "itemList"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)

        // Find the button by its ID
        val addItemButton = findViewById<Button>(R.id.addItemButton)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ItemAdapter(itemList) // Initialize the adapter here

        // Set the item removal listener
        adapter.setOnItemRemoveListener(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Load the saved item list (if any) after initializing the adapter
        loadItemList()

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
            Log.d("com.example.marketlist.MainActivity", "Item list before removal: $itemList")

            itemList.removeAt(position)
            adapter.notifyItemRemoved(position)

            // Save the updated list
            saveItemList()

            // Log the itemList after item removal
            Log.d("com.example.marketlist.MainActivity", "Item list after removal: $itemList")
        }
    }

    private fun loadItemList() {
        val savedItemList = sharedPreferences.getStringSet(itemListKey, HashSet())?.toMutableList()
        savedItemList?.let {
            itemList.clear()
            itemList.addAll(it.map { Item(it, false) }) // Initialize isChecked to false
            adapter.notifyDataSetChanged() // Update the RecyclerView
        }
    }

    private fun saveItemList() {
        val editor = sharedPreferences.edit()
        val itemNames = itemList.map { it.name }.toSet()
        editor.putStringSet(itemListKey, itemNames)
        editor.apply()
    }
}
