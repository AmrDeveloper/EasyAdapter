package com.amrdeveloper.easyadapter.compiler.data.bind

import com.amrdeveloper.easyadapter.compiler.data.listener.ListenerData

data class BindExpandableData (
    val modelClassName: String,
    val modelClassPackageName : String,
    val layoutId: String,
    val bindingDataList: List<BindingData>,
    val listeners: Set<ListenerData>
)