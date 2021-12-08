package com.amrdeveloper.easyadapter.compiler.generator

import com.amrdeveloper.easyadapter.compiler.model.ArrayAdapterData
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

class ArrayAdapterGenerator(private val adapterData : ArrayAdapterData) : AdapterGenerator {

    private val adapterName = adapterData.adapterClassName
    private val appPackageName = adapterData.appPackageId
    private val modelClassName = ClassName(adapterData.adapterPackageName, adapterData.modelClassName)
    private val itemsListClassName = GeneratorConstants.listClassName.parameterizedBy(modelClassName)
    private val rClassName = ClassName(appPackageName, "R")

    override fun generate(): TypeSpec = TypeSpec.classBuilder(adapterName)
        .primaryConstructor(FunSpec.constructorBuilder()
            .addParameter("context", GeneratorConstants.contextClassName)
            .addParameter("items", itemsListClassName)
            .build()
        )
        .superclass(GeneratorConstants.arrayAdapterClassName.parameterizedBy(modelClassName))
        .addSuperclassConstructorParameter("context, 0, items")
        .addViewHolderType()
        .build()

    private fun TypeSpec.Builder.addViewHolderType(): TypeSpec.Builder = addFunction (
        FunSpec.builder("getView")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("position", INT)
            .addParameter("convertView", GeneratorConstants.viewClassName.copy(true))
            .addParameter("parent", GeneratorConstants.viewGroupClassName)
            .addStatement(
                "val itemView = %T.from(parent.context).inflate(%T.layout.%L, parent, false)",
                GeneratorConstants.layoutInflaterClassName,
                rClassName,
                adapterData.layoutId
            )
            .addStatement("val item = getItem(position) ?: return itemView")
            .addBindingDataList(rClassName, adapterData.bindingDataList)
            .returns(GeneratorConstants.viewClassName)
            .addStatement("return itemView")
            .build()
    )
}