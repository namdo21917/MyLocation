package com.example.mylocation.ui.infoWindow

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

//
//import android.os.Bundle
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import com.example.mylocation.R
//import com.example.mylocation.databinding.FragmentInfoWindowListDialogItemBinding
//import com.example.mylocation.databinding.FragmentInfoWindowListDialogBinding
//
//const val ARG_ITEM_COUNT = "item_count"
//
class InfoWindowFragment : BottomSheetDialogFragment() {
//
//    private var _binding: FragmentInfoWindowListDialogBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        _binding = FragmentInfoWindowListDialogBinding.inflate(inflater, container, false)
//        return binding.root
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        activity?.findViewById<RecyclerView>(R.id.list)?.layoutManager =
//            LinearLayoutManager(context)
//        activity?.findViewById<RecyclerView>(R.id.list)?.adapter =
//            arguments?.getInt(ARG_ITEM_COUNT)?.let { ItemAdapter(it) }
//    }
//
//    private inner class ViewHolder internal constructor(binding: FragmentInfoWindowListDialogItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        internal val text: TextView = binding.text
//    }
//
//    private inner class ItemAdapter internal constructor(private val mItemCount: Int) :
//        RecyclerView.Adapter<ViewHolder>() {
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//            return ViewHolder(
//                FragmentInfoWindowListDialogItemBinding.inflate(
//                    LayoutInflater.from(
//                        parent.context
//                    ), parent, false
//                )
//            )
//
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            holder.text.text = position.toString()
//        }
//
//        override fun getItemCount(): Int {
//            return mItemCount
//        }
//    }
//
//    companion object {
//
//        // TODO: Customize parameters
//        fun newInstance(itemCount: Int): InfoWindowFragment =
//            InfoWindowFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_ITEM_COUNT, itemCount)
//                }
//            }
//
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}