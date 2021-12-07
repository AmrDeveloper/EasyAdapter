package com.amrdeveloper.easyadapter.compiler.generator

import com.amrdeveloper.easyadapter.compiler.model.BindingData
import com.amrdeveloper.easyadapter.option.ViewSetterType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec

fun FunSpec.Builder.addBindingDataList(
    rClassName: ClassName,
    bindingDataList: List<BindingData>
): FunSpec.Builder = apply {
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
}

fun TypeSpec.Builder.addDiffUtilsItemCallback(
    modelClassName: ClassName,
    diffUtilContent: String
): TypeSpec.Builder = addType(
    TypeSpec.classBuilder("ModelComparator")
        .superclass(GeneratorConstants.diffUtilCallbackClassName.parameterizedBy(modelClassName))
        .addFunction(
            FunSpec.builder("areItemsTheSame")
                .addModifiers(KModifier.OVERRIDE)
                .returns(Boolean::class.java)
                .addParameter("oldItem", modelClassName)
                .addParameter("newItem", modelClassName)
                .addStatement("return (oldItem == newItem)")
                .build()
        )
        .addFunction(
            FunSpec.builder("areContentsTheSame")
                .addModifiers(KModifier.OVERRIDE)
                .returns(Boolean::class.java)
                .addParameter("oldItem", modelClassName)
                .addParameter("newItem", modelClassName)
                .addStatement("return (oldItem.%L == newItem.%L)", diffUtilContent, diffUtilContent)
                .build()
        )
        .build()
)