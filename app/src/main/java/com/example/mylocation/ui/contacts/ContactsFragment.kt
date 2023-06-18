package com.example.mylocation.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylocation.R
import com.example.mylocation.adapter.ContactsAdapter
import com.example.mylocation.data.Contact
import com.example.mylocation.data.DatabaseHelper
import com.example.mylocation.databinding.FragmentContactBinding


class ContactsFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var contactRecyclerView: RecyclerView
    private lateinit var adapter: ContactsAdapter
    private lateinit var databaseHelper: DatabaseHelper

    private val address: String? = arguments?.getString("title")
    private val snippet: String? = arguments?.getString("snippet")
    private val latitude: Double? = arguments?.getDouble("latitude")
    private val longitude: Double? = arguments?.getDouble("longitude")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedContact = mutableListOf<Contact>()
        val buttonShare: Button = view.findViewById(R.id.button_share)

        databaseHelper = DatabaseHelper(requireContext())

        contactRecyclerView = view.findViewById(R.id.recycler_view_contacts)
        contactRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ContactsAdapter(contacts)
        adapter.onItemClick = { contact ->
            if (adapter.selectedItems.contains(contact)) {
                adapter.selectedItems.remove(contact)
            } else {
                adapter.selectedItems.add(contact)
            }
            adapter.notifyDataSetChanged()
        }

        contactRecyclerView.adapter = adapter

        buttonShare.setOnClickListener {
            try {

                databaseHelper.newSharedPlace(
                    address?:"",
                    snippet?:"",
                    longitude?:0.0,
                    latitude?:0.0,
                    (selectedContact) as List<String>
                )
            } catch (e: Exception) {
                throw Exception("Cannot add this marker")
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private val contacts by lazy {
        val contacts = mutableListOf<Contact>()
        contacts.add(Contact("Quang Diệu", "0976456899"))
        contacts.add(Contact("Quang Anh", "0977856809"))
        contacts.add(Contact("Minh Hiếu", "0971236809"))
        contacts.add(Contact("Hoàng Minh", "0977800009"))
        contacts
    }
}