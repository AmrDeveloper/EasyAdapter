package com.amrdeveloper.easyadapter.compiler.data.bind

import com.amrdeveloper.easyadapter.compiler.utils.ViewTable
import com.amrdeveloper.easyadapter.option.ViewSetterType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec

data class BindImageResData (
    override var fieldName : String,
    override var viewId: String,
    override var bindType: BindType = BindType.IMAGE_RES,
    override var viewClassType: String = "android.widget.ImageView",
    override var viewClassSetter: String = "setImageResource",
    override var viewSetterType: ViewSetterType = ViewSetterType.METHOD,
) : BindingData() {

    override fun generateFieldBinding(builder: FunSpec.Builder, table: ViewTable, rClass: ClassName) {
        val variableName = declareViewVariableIfNotExists(builder, table, rClass)
        builder.addStatement ("$variableName.${getBindingValueSetter()}")
    }
}