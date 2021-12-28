package com.amrdeveloper.easyadapter.compiler.data.bind

import com.amrdeveloper.easyadapter.option.ViewSetterType

data class BindImageResData (
    override var fieldName : String,
    override var viewId: String,
    override var bindType: BindType = BindType.IMAGE_RES,
    override var viewClassType: String = "android.widget.ImageView",
    override var viewClassSetter: String = "setImageResource",
    override var viewSetterType: ViewSetterType = ViewSetterType.METHOD,
    override val poetFormat: String = "itemView.findViewById<%L>(%T.id.%L).%L"
) : BindingData()