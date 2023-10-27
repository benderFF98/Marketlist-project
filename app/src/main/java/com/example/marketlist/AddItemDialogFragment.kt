import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.marketlist.ItemAdapter
import com.example.marketlist.R // Replace with your app's package name

class AddItemDialogFragment : DialogFragment() {
    private lateinit var itemList: MutableList<String>
    private lateinit var adapter: ItemAdapter // Use your custom adapter type

    fun setItemListAndAdapter(itemList: MutableList<String>, adapter: ItemAdapter) {
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
                itemList.add(itemName)
                adapter.notifyDataSetChanged() // Notify the adapter of data changes
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
}
