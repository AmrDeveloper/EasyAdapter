package com.amrdeveloper.easyadapter.compiler.data.bind

import com.amrdeveloper.easyadapter.compiler.utils.ViewTable
import com.amrdeveloper.easyadapter.option.ViewSetterType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec

data class BindingTextData (
    override var fieldName : String,
    override var viewId: String,
    override var bindType: BindType = BindType.TEXT,
    override var viewClassType: String = "android.widget.TextView",
    override var viewClassSetter: String = "setText",
    override var viewSetterType: ViewSetterType = ViewSetterType.METHOD,
) : BindingData() {

    override fun generateFieldBinding(builder: FunSpec.Builder, table: ViewTable, rClass: ClassName) {
        var variableName = table.resolve(viewId)
        if (variableName.isEmpty()) {
            variableName = table.define(viewId)
            declareViewVariable(builder, variableName, viewClassType, viewId, rClass)
        }

        builder.addStatement ("$variableName.${getBindingValueSetter()}")
    }
}