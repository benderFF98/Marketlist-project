package com.example.marketlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class AddItemDialogFragment : DialogFragment() {
    private lateinit var itemList: MutableList<Item>
    private lateinit var adapter: ItemAdapter

    fun setItemListAndAdapter(itemList: MutableList<Item>, adapter: ItemAdapter) {
        this.itemList = itemList
        this.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_item, container, false)

        val itemNameEditText = view.findViewById<EditText>(R.id.editTextItemName)
        val saveButton = view.findViewById<Button>(R.id.buttonSave)
        val cancelButton = view.findViewById<Button>(R.id.buttonCancel)

        // Handle "Save" button click
        saveButton.setOnClickListener {
            val itemName = itemNameEditText.text.toString()
            if (itemName.isNotEmpty()) {
                itemList.add(Item(itemName)) // Create a new Item with the provided name
                adapter.notifyDataSetChanged() // Notify the adapter of data changes

                // Call the saveItemList function to save the updated list
                saveItemList()

                dismiss()
            }

            // You can pass the item name to the calling activity/fragment or perform any other desired action
            dismiss()
        }

        // Handle "Cancel" button click
        cancelButton.setOnClickListener {
            dismiss()
        }

        return view
    }

    private fun saveItemList() {
        // Initialize SharedPreferences
        val sharedPreferences = context?.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val itemListKey = "itemList"

        val editor = sharedPreferences?.edit()
        val itemNames = itemList.map { it.name }.toSet()
        editor?.putStringSet(itemListKey, itemNames)
        editor?.apply()

        // Log that an item has been saved
        Log.d("AddItemDialogFragment", "Item saved: $itemNames")
    }
}
