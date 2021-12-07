package com.amrdeveloper.easyadapter.compiler.model

data class PagingAdapterData (
    override val appPackageId: String,
    override val adapterPackageName: String,
    override val adapterClassName: String,
    override val modelClassName: String,
    override val layoutId: String,
    override val bindingDataList: List<BindingData>,
    val diffUtilContent : String,
) : AdapterData()