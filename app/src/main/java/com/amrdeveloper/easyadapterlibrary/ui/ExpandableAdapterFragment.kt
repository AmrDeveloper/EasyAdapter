package com.amrdeveloper.easyadapterlibrary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.amrdeveloper.easyadapterlibrary.R
import com.amrdeveloper.easyadapterlibrary.data.*

class ExpandableAdapterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expandable_adapter, container, false)

        val expandableListView = view.findViewById<ExpandableListView>(R.id.expandable_list)
        val userRepositoriesMap = mutableMapOf<User, List<Repository>>()
        for(user in users) {
            userRepositoriesMap[user] = repositories
        }
        val adapter = ExpandableUserRepositoriesExpandableAdapter(users, userRepositoriesMap)
        expandableListView.setAdapter(adapter)

        return view
    }
}