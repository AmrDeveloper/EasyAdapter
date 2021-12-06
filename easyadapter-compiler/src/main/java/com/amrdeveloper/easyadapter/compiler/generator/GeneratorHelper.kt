package com.amrdeveloper.easyadapter.compiler.generator

import com.amrdeveloper.easyadapter.compiler.model.BindingData
import com.amrdeveloper.easyadapter.option.ViewSetterType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec

fun FunSpec.Builder.generateBindingData (
    rClassName: ClassName,
    bindingDataList: List<BindingData>
): FunSpec.Builder {
    bindingDataList.forEach {
        addStatement(
            it.poetFormat,
            it.viewClassType,
            rClassName,
            it.viewId,
            if (it.viewSetterType == ViewSetterType.PROPERTY)
                "${it.viewClassSetter}=item.${it.value}"
            else
                "${it.viewClassSetter}(item.${it.value})"
        )
    }
    return this
}