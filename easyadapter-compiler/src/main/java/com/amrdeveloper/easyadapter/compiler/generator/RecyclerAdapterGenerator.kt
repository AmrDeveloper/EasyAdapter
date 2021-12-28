package com.amrdeveloper.easyadapter.compiler.generator

import com.amrdeveloper.easyadapter.compiler.data.adapter.RecyclerAdapterData
import com.amrdeveloper.easyadapter.compiler.utils.ViewTable
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

class RecyclerAdapterGenerator (private val adapterData: RecyclerAdapterData) : AdapterGenerator() {

    private val adapterName = adapterData.adapterClassName
    private val appPackageName = adapterData.appPackageId
    private val viewHolderName = "ViewHolder"
    private val viewHolderClassName = ClassName(adapterData.adapterPackageName, viewHolderName)
    private val viewHolderQualifiedClassName = ClassName(adapterData.adapterPackageName, "$adapterName.$viewHolderName")
    private val modelClassName = ClassName(adapterData.adapterPackageName, adapterData.modelClassName)
    private val itemsListClassName = GeneratorConstants.listClassName.parameterizedBy(modelClassName)
    private val rClassName = ClassName(appPackageName, "R")

    override fun generate(): TypeSpec = TypeSpec.classBuilder(adapterName)
        .primaryConstructor(FunSpec.constructorBuilder()
            .addParameter("items", itemsListClassName)
            .build()
        )
        .superclass(GeneratorConstants.recyclerAdapterClassName.parameterizedBy(viewHolderQualifiedClassName))
        .addProperty(PropertySpec.builder("items", itemsListClassName)
            .addModifiers(KModifier.PRIVATE)
            .initializer("items")
            .mutable(true)
            .build()
        )
        .addGlobalListenersRequirements(modelClassName, adapterData.listeners)
        .addBaseMethods()
        .addViewHolderType()
        .build()

    private fun TypeSpec.Builder.addBaseMethods(): TypeSpec.Builder = apply {
        addFunction(FunSpec.builder("getItemCount")
            .addModifiers(KModifier.OVERRIDE)
            .returns(INT)
            .addStatement("return items.size")
            .build()
        )

        addFunction(FunSpec.builder("onCreateViewHolder")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("parent", GeneratorConstants.viewGroupClassName)
            .addParameter("viewType", INT)
            .returns(viewHolderQualifiedClassName)
            .addStatement(
                "val view = %T.from(parent.context).inflate(%T.layout.%L, parent, false)",
                GeneratorConstants.layoutInflaterClassName,
                rClassName,
                adapterData.layoutId
            )
            .addStatement("return $viewHolderName(view)")
            .build()
        )

        addFunction(FunSpec.builder("onBindViewHolder")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("viewHolder", viewHolderQualifiedClassName)
            .addParameter("position", INT)
            .addStatement("val item = items[position]")
            .addStatement("viewHolder.bind(item)")
            .addStatement("val itemView = viewHolder.itemView")
            .addListenerBindingList(rClassName, ViewTable(), adapterData.listeners)
            .build()
        )

        if (adapterData.generateUpdateData) {
            addFunction(FunSpec.builder("updateData")
                .addParameter("newList", itemsListClassName)
                .addStatement("items = newList")
                .addStatement("notifyDataSetChanged()")
                .build()
            )
        }
    }

    private fun TypeSpec.Builder.addViewHolderType(): TypeSpec.Builder = addType(
        TypeSpec.classBuilder(viewHolderClassName)
            .primaryConstructor(FunSpec.constructorBuilder()
                .addParameter("itemView", GeneratorConstants.viewClassName)
                .build()
            )
            .superclass(GeneratorConstants.recyclerViewHolderClassName)
            .addSuperclassConstructorParameter("itemView")
            .addBindingMethod()
            .build()
    )

    private fun TypeSpec.Builder.addBindingMethod(): TypeSpec.Builder = addFunction(
        FunSpec.builder("bind")
            .addParameter("item", modelClassName)
            .addBindingDataList(rClassName, ViewTable(), adapterData.bindingDataList)
            .build()
    )
}