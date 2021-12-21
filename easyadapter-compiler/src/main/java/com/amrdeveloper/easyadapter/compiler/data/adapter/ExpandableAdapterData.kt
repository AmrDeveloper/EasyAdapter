package com.amrdeveloper.easyadapter.compiler.data.adapter

import com.amrdeveloper.easyadapter.compiler.data.bind.BindExpandableData

data class ExpandableAdapterData (
    override val appPackageId: String,
    override val adapterPackageName: String,
    override val adapterClassName: String,
    val expandableGroup : BindExpandableData,
    val expandableItem : BindExpandableData,
) : AdapterData()