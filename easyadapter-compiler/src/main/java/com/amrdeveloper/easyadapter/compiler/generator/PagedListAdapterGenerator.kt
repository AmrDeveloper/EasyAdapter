package com.amrdeveloper.easyadapter.compiler.generator

import com.amrdeveloper.easyadapter.compiler.data.adapter.PagedListAdapterData
import com.amrdeveloper.easyadapter.compiler.utils.ViewTable
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

class PagedListAdapterGenerator(private val adapterData: PagedListAdapterData) : AdapterGenerator() {

    private val adapterName = adapterData.adapterClassName
    private val appPackageName = adapterData.appPackageId
    private val viewHolderName = "ViewHolder"
    private val viewHolderClassName = ClassName(adapterData.adapterPackageName, viewHolderName)
    private val viewHolderQualifiedClassName = ClassName(adapterData.adapterPackageName, "$adapterName.$viewHolderName")
    private val modelClassName = ClassName(adapterData.adapterPackageName, adapterData.modelClassName)
    private val rClassName = ClassName(appPackageName, "R")

    override fun generate(): TypeSpec = TypeSpec.classBuilder(adapterName)
        .superclass(GeneratorConstants.pagedListAdapterClassName.parameterizedBy(modelClassName, viewHolderQualifiedClassName))
        .addSuperclassConstructorParameter("ModelComparator()")
        .addBaseMethods()
        .addViewHolderType()
        .addGlobalListenersRequirements(modelClassName, adapterData.listeners)
        .addDiffUtilsItemCallback(modelClassName, adapterData.diffUtilContent)
        .build()

    private fun TypeSpec.Builder.addBaseMethods(): TypeSpec.Builder = apply {
        addFunction(
            FunSpec.builder("onCreateViewHolder")
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

        val viewTable = ViewTable("viewHolder", "position")

        addFunction(
            FunSpec.builder("onBindViewHolder")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("viewHolder", viewHolderQualifiedClassName)
                .addParameter("position", INT)
                .addStatement("val item = getItem(position) ?: return")
                .addStatement("viewHolder.bind(item)")
                .addStatement("val itemView = viewHolder.itemView")
                .addListenerBindingList(rClassName, viewTable, adapterData.listeners)
                .build()
        )
    }

    private fun TypeSpec.Builder.addViewHolderType(): TypeSpec.Builder = addType (
        TypeSpec.classBuilder(viewHolderClassName)
            .primaryConstructor(
                FunSpec.constructorBuilder()
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