package com.amrdeveloper.easyadapter.compiler.data.bind

import com.amrdeveloper.easyadapter.option.ViewSetterType

abstract class BindingData {
    abstract var value: String
    abstract var viewId: String
    abstract var bindType: BindType
    abstract var viewClassType: String
    abstract var viewClassSetter: String
    abstract var viewSetterType: ViewSetterType
    abstract val poetFormat : String
}