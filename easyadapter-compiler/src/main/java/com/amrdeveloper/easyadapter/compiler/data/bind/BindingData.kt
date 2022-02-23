package com.amrdeveloper.easyadapter.compiler.data.bind

import com.amrdeveloper.easyadapter.compiler.utils.ViewTable
import com.amrdeveloper.easyadapter.option.ViewSetterType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec

abstract class BindingData {
    abstract var fieldName: String
    abstract var viewId: String
    abstract var condition : String
    abstract var bindType: BindType
    abstract var viewClassType: String
    abstract var viewClassSetter: String
    abstract var viewSetterType: ViewSetterType

    abstract fun generateFieldBinding(builder: FunSpec.Builder, table: ViewTable, rClass: ClassName)

    fun getBindingValueSetter(): String {
        return if (viewSetterType == ViewSetterType.PROPERTY)
            "$viewClassSetter=item.$fieldName"
        else
            "$viewClassSetter(item.$fieldName)"
    }

    fun declareViewVariableIfNotExists(builder: FunSpec.Builder, table: ViewTable, rClass: ClassName) : String {
        return table.resolve(viewId, viewClassType).ifEmpty {
            val variableName = table.define(viewId, viewClassType)
            declareViewVariable(builder, variableName, viewClassType, viewId, rClass)
            variableName
        }
    }

    private fun declareViewVariable(builder: FunSpec.Builder, name : String, viewType: String, viewId: String, rClass: ClassName) {
        builder.addStatement (
            "val %L=itemView.findViewById<%L>(%T.id.%L)",
            name, viewType, rClass, viewId
        )
    }
}