package com.amrdeveloper.easyadapter.compiler.data.adapter

import com.amrdeveloper.easyadapter.compiler.data.bind.BindingData
import com.amrdeveloper.easyadapter.compiler.data.listener.ListenerData

data class ArrayAdapterData (
    override val appPackageId: String,
    override val adapterPackageName: String,
    override val adapterClassName: String,
    override val modelClassName: String,
    override val layoutId: String,
    override val bindingDataList: List<BindingData>,
    override val listeners : Set<ListenerData>
) : AdapterData()