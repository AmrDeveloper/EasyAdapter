package com.amrdeveloper.easyadapterlibrary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amrdeveloper.easyadapterlibrary.R
import com.amrdeveloper.easyadapterlibrary.data.UserRecyclerAdapter
import com.amrdeveloper.easyadapterlibrary.data.users

class RecyclerAdapterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_adapter, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = UserRecyclerAdapter(users)
        recyclerView.adapter = adapter

        return view
    }
}