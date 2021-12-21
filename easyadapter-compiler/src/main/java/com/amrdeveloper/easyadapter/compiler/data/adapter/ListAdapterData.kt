package com.amrdeveloper.easyadapter.compiler.data.adapter

import com.amrdeveloper.easyadapter.compiler.data.bind.BindingData
import com.amrdeveloper.easyadapter.compiler.data.listener.ListenerData

data class ListAdapterData (
    override val appPackageId: String,
    override val adapterPackageName: String,
    override val adapterClassName: String,
    val modelClassName: String,
    val layoutId: String,
    val bindingDataList: List<BindingData>,
    val listeners : Set<ListenerData>,
    val diffUtilContent : String,
) : AdapterData()