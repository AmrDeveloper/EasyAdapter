package com.amrdeveloper.easyadapter.compiler.model

import com.amrdeveloper.easyadapter.option.ViewSetterType

data class BindVisibilityData (
    override var value: String,
    override var viewId: String,
    override var bindType: BindType = BindType.VIEW,
    override var viewClassType: String = "android.view.View",
    override var viewClassSetter: String = "setVisibility",
    override var viewSetterType: ViewSetterType = ViewSetterType.METHOD,
    override val poetFormat: String = "itemView.findViewById<%L>(%T.id.%L).%L"
) : BindingData()