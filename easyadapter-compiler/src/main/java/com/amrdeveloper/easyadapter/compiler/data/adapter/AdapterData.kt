package com.amrdeveloper.easyadapter.compiler.data.adapter

import com.amrdeveloper.easyadapter.compiler.data.bind.BindingData
import com.amrdeveloper.easyadapter.compiler.data.listener.ListenerData

abstract class AdapterData {
    abstract val appPackageId : String
    abstract val adapterPackageName : String
    abstract val adapterClassName : String
    abstract val modelClassName : String
    abstract val layoutId : String
    abstract val bindingDataList : List<BindingData>
    abstract val listeners : Set<ListenerData>
}