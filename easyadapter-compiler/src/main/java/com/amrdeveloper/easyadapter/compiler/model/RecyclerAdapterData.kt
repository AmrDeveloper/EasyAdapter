package com.amrdeveloper.easyadapter.compiler.model

data class RecyclerAdapterData (
    override val appPackageId: String,
    override val adapterPackageName: String,
    override val adapterClassName: String,
    override val modelClassName: String,
    override val layoutId: String,
    override val bindingDataList: List<BindingData>,
    val generateUpdateData: Boolean,
) : AdapterData()