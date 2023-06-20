package com.example.mylocation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylocation.R
import com.example.mylocation.data.Contact

class ContactsAdapter(
    private val contacts: List<Contact>
) :
    RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    var onItemClick: ((Contact) -> Unit)? = null
    var selectedItems: MutableList<Contact> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_contact, parent, false)

        return ContactViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact)

    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contactName: TextView = itemView.findViewById(R.id.contact_name)
        private val checkIndicator: ImageView = itemView.findViewById(R.id.check_indicator)
        fun bind(contact: Contact) {
            contactName.text = contact.name
            if (selectedItems.contains(contact)) {
                checkIndicator.setImageResource(android.R.drawable.checkbox_on_background)
            } else {
                checkIndicator.setImageResource(android.R.drawable.checkbox_off_background)
            }
        }

        init {
            itemView.setOnClickListener {
                val selectedItem = contacts[position]
                if (selectedItems.contains(selectedItem)) {
                    selectedItems.remove(selectedItem)
                } else {
                    selectedItems.add(selectedItem)
                }
                notifyDataSetChanged()

                onItemClick?.invoke(selectedItem)
            }

        }
    }
}

