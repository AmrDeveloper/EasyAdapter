package com.amrdeveloper.easyadapter.compiler.model

import com.amrdeveloper.easyadapter.option.ViewSetterType

data class BindBackgroundResData (
    override var value : String,
    override var viewId: String,
    override var bindType: BindType = BindType.BACKGROUND_RES,
    override var viewClassType: String = "android.view.View",
    override var viewClassSetter: String = "setBackgroundResource",
    override var viewSetterType: ViewSetterType = ViewSetterType.METHOD,
    override val poetFormat: String = "itemView.findViewById<%L>(%T.id.%L).%L"
) : BindingData()