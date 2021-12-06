package com.amrdeveloper.easyadapter.compiler.model

abstract class AdapterData {
    abstract val appPackageId : String
    abstract val adapterPackageName : String
    abstract val adapterClassName : String
    abstract val modelClassName : String
    abstract val layoutId : String
    abstract val bindingDataList : List<BindingData>
}