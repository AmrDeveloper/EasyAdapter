package com.amrdeveloper.easyadapter.compiler.data.bind

import com.amrdeveloper.easyadapter.option.ViewSetterType

data class BindingTextData (
    override var value : String,
    override var viewId: String,
    override var bindType: BindType = BindType.TEXT,
    override var viewClassType: String = "android.widget.TextView",
    override var viewClassSetter: String = "setText",
    override var viewSetterType: ViewSetterType = ViewSetterType.METHOD,
    override val poetFormat: String = "itemView.findViewById<%L>(%T.id.%L).%L"
) : BindingData()