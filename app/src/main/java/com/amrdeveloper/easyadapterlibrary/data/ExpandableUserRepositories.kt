package com.amrdeveloper.easyadapterlibrary.data

import com.amrdeveloper.easyadapter.adapter.ExpandableAdapter
import com.amrdeveloper.easyadapter.bind.BindExpandableMap

@ExpandableAdapter(appPackageId)
data class ExpandableUserRepositories (

    @BindExpandableMap
    val data : Map<User, List<Repository>>
)