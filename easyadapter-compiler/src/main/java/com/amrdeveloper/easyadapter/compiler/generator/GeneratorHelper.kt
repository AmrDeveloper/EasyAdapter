package com.amrdeveloper.easyadapter.compiler.generator

import com.amrdeveloper.easyadapter.compiler.data.bind.BindingData
import com.amrdeveloper.easyadapter.compiler.data.listener.ListenerData
import com.amrdeveloper.easyadapter.compiler.utils.ViewTable
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

fun FunSpec.Builder.addBindingDataList(
    rClassName: ClassName,
    viewTable: ViewTable,
    bindingDataList: List<BindingData>
): FunSpec.Builder = apply {
    bindingDataList.forEach {
        it.generateFieldBinding(this, viewTable, rClassName)
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
        it.generateInterfaceDeclarations(this, modelClassName)
        it.generateListenerVariable(this)
        it.generateListenerVariableSetter(this)
    }
}

fun FunSpec.Builder.addListenerBindingList(
    rClassName: ClassName,
    viewTable: ViewTable,
    listeners: Set<ListenerData>
): FunSpec.Builder = apply {
    listeners.forEach {
        it.generateBinds(this, viewTable, rClassName)
    }
}
