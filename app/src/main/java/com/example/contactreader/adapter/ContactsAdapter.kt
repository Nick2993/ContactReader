package com.example.contactreader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drmoapp.R
import com.example.contactreader.model.Contact
import android.widget.Filter
import android.widget.Filterable

class ContactsAdapter(private var contactsList: List<Contact>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>(), Filterable {

    private var contactsListFull: List<Contact> = ArrayList(contactsList)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.name_text_view)
        val phoneNumberTextView: TextView = view.findViewById(R.id.phone_number_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactsList[position]
        holder.nameTextView.text = contact.name
        holder.phoneNumberTextView.text = contact.phoneNumber
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<Contact>()

                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(contactsListFull)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (item in contactsListFull) {
                        if (item.name.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                contactsList = results?.values as List<Contact>
                notifyDataSetChanged()
            }
        }
    }
}
