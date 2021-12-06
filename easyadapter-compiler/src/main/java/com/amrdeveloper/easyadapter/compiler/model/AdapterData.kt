package com.amrdeveloper.easyadapter.compiler.model

data class AdapterData (
    val appPackageId : String,
    val adapterPackageName : String,
    val adapterClassName : String,
    val modelClassName : String,
    val layoutId : String,
    val generateUpdateData : Boolean,
    val bindingDataList : List<BindingData>
)