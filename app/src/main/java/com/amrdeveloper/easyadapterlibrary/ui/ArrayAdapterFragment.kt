package com.amrdeveloper.easyadapterlibrary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.amrdeveloper.easyadapterlibrary.R
import com.amrdeveloper.easyadapterlibrary.data.UserArrayAdapter
import com.amrdeveloper.easyadapterlibrary.data.users

class ArrayAdapterFragment : Fragment() {

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_array_adapter, container, false)

        val listView = view.findViewById<ListView>(R.id.users_list)
        val adapter = UserArrayAdapter(requireContext(), users)
        listView.adapter = adapter

        return view
    }
}