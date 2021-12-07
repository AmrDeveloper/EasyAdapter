package com.amrdeveloper.easyadapter.compiler.generator

import com.amrdeveloper.easyadapter.compiler.model.PagingAdapterData
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

class PagingDataAdapterGenerator(private val adapterData: PagingAdapterData) : AdapterGenerator {

    private val adapterName = adapterData.adapterClassName
    private val appPackageName = adapterData.appPackageId
    private val viewHolderName = "ViewHolder"
    private val viewHolderClassName = ClassName(adapterData.adapterPackageName, viewHolderName)
    private val viewHolderQualifiedClassName = ClassName(adapterData.adapterPackageName, "$adapterName.$viewHolderName")
    private val modelClassName = ClassName(adapterData.adapterPackageName, adapterData.modelClassName)
    private val rClassName = ClassName(appPackageName, "R")

    override fun generate(): TypeSpec = TypeSpec.classBuilder(adapterName)
        .superclass(GeneratorConstants.pagingDataAdapterClassName.parameterizedBy(modelClassName, viewHolderQualifiedClassName))
        .addSuperclassConstructorParameter("ModelComparator()")
        .addBaseMethods()
        .addViewHolderType()
        .addDiffUtilsItemCallback(modelClassName, adapterData.diffUtilContent)
        .build()

    private fun TypeSpec.Builder.addBaseMethods(): TypeSpec.Builder = apply {
        addFunction(
            FunSpec.builder("onCreateViewHolder")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("parent", GeneratorConstants.viewGroupClassName)
            .addParameter("viewType", INT)
            .returns(viewHolderQualifiedClassName)
            .addStatement("val view = android.view.LayoutInflater.from(parent.context).inflate(%T.layout.%L, parent, false)", rClassName, adapterData.layoutId)
            .addStatement("return $viewHolderName(view)")
            .build()
        )

        addFunction(
            FunSpec.builder("onBindViewHolder")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("viewHolder", viewHolderQualifiedClassName)
            .addParameter("position", INT)
            .addStatement("val currentItem = getItem(position) ?: return")
            .addStatement("viewHolder.bind(currentItem)")
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
            .addBindingDataList(rClassName, adapterData.bindingDataList)
            .build()
    )
}