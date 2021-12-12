package com.amrdeveloper.easyadapter.compiler.data.bind

import com.amrdeveloper.easyadapter.option.ViewSetterType

data class BindBackgroundColorData (
    override var value : String,
    override var viewId: String,
    override var bindType: BindType = BindType.BACKGROUND_COLOR,
    override var viewClassType: String = "android.view.View",
    override var viewClassSetter: String = "setBackgroundColor",
    override var viewSetterType: ViewSetterType = ViewSetterType.METHOD,
    override val poetFormat: String = "itemView.findViewById<%L>(%T.id.%L).%L"
) : BindingData()