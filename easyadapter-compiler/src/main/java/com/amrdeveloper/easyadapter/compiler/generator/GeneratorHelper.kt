package com.amrdeveloper.easyadapter.compiler.generator

import com.amrdeveloper.easyadapter.compiler.data.bind.BindType
import com.amrdeveloper.easyadapter.compiler.data.bind.BindingData
import com.amrdeveloper.easyadapter.compiler.data.listener.ListenerData
import com.amrdeveloper.easyadapter.option.ViewSetterType
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

fun FunSpec.Builder.addBindingDataList(
    rClassName: ClassName,
    bindingDataList: List<BindingData>
): FunSpec.Builder = apply {
    bindingDataList.forEach {
        val bindingValueSetter =
            if (it.viewSetterType == ViewSetterType.PROPERTY)
                "${it.viewClassSetter}=item.${it.value}"
            else
                "${it.viewClassSetter}(item.${it.value})"
        when (it.bindType) {
            BindType.IMAGE -> {
                addStatement(
                    it.poetFormat,
                    bindingValueSetter,
                    it.viewClassType,
                    rClassName,
                    it.viewId
                )
            }
            else -> {
                addStatement(
                    it.poetFormat,
                    it.viewClassType,
                    rClassName,
                    it.viewId,
                    bindingValueSetter
                )
            }
        }
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

fun TypeSpec.Builder.addGlobalListenersRequirements(
    modelClassName: ClassName,
    listeners: Set<ListenerData>
): TypeSpec.Builder = apply {
    listeners.forEach {
        it.generateDeclarations(this, modelClassName)
    }
}

fun FunSpec.Builder.addListenerBindingList(
    rClassName: ClassName,
    listeners: Set<ListenerData>
): FunSpec.Builder = apply {
    listeners.forEach {
        it.generateBinds(this, rClassName)
    }
}
