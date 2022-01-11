package com.amrdeveloper.easyadapterlibrary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amrdeveloper.easyadapterlibrary.R
import com.amrdeveloper.easyadapterlibrary.data.UserListAdapter
import com.amrdeveloper.easyadapterlibrary.data.users

class ListAdapterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_adapter, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = UserListAdapter()
        recyclerView.adapter = adapter
        adapter.submitList(users)

        return view
    }
}