package com.amrdeveloper.easyadapter.compiler.generator

import com.amrdeveloper.easyadapter.compiler.data.adapter.ExpandableAdapterData
import com.amrdeveloper.easyadapter.compiler.utils.ViewTable
import com.squareup.kotlinpoet.TypeSpec

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

class ExpandableAdapterGenerator(private val adapterData: ExpandableAdapterData) : AdapterGenerator() {

    private val adapterName = adapterData.adapterClassName
    private val appPackageName = adapterData.appPackageId
    private val groupClassName = ClassName(adapterData.expandableGroup.modelClassPackageName,adapterData.expandableGroup.modelClassName)
    private val itemClassName = ClassName(adapterData.expandableItem.modelClassPackageName,adapterData.expandableItem.modelClassName)
    private val expandableGroupList = GeneratorConstants.listClassName.parameterizedBy(groupClassName)
    private val expandableMapList = GeneratorConstants.mapClassName.parameterizedBy(groupClassName, GeneratorConstants.listClassName.parameterizedBy(itemClassName))
    private val rClassName = ClassName(appPackageName, "R")
    private val groupViewTable = ViewTable()
    private val childViewTable = ViewTable()

    override fun generate(): TypeSpec = TypeSpec.classBuilder(adapterName)
        .primaryConstructor(
            FunSpec.constructorBuilder()
                .addParameter("groupList", expandableGroupList)
                .addParameter("itemsMap", expandableMapList)
                .build()
        )
        .addProperty(PropertySpec.builder("groupList", expandableGroupList)
            .addModifiers(KModifier.PRIVATE)
            .initializer("groupList")
            .mutable(true)
            .build()
        )
        .addProperty(PropertySpec.builder("itemsMap", expandableMapList)
            .addModifiers(KModifier.PRIVATE)
            .initializer("itemsMap")
            .mutable(true)
            .build()
        )
        .superclass(GeneratorConstants.baseExpandableAdapterClassName)
        .addBaseMethods()
        .addGlobalListenersRequirements(groupClassName, adapterData.expandableGroup.listeners)
        .addGlobalListenersRequirements(itemClassName, adapterData.expandableItem.listeners)
        .build()

    private fun TypeSpec.Builder.addBaseMethods(): TypeSpec.Builder = apply {
        addFunction(FunSpec.builder("getGroupView")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("listPosition", INT)
            .addParameter("isExpanded", Boolean::class)
            .addParameter("convertView", GeneratorConstants.viewClassName)
            .addParameter("parent", GeneratorConstants.viewGroupClassName)
            .returns(GeneratorConstants.viewClassName)
            .addStatement("val itemView = %T.from(parent.context).inflate(%T.layout.%L, parent, false)",
                GeneratorConstants.layoutInflaterClassName,
                rClassName, adapterData.expandableGroup.layoutId)
            .addStatement("val item = getGroup(listPosition)")
            .addBindingDataList(rClassName, groupViewTable, adapterData.expandableGroup.bindingDataList)
            .addListenerBindingList(rClassName, groupViewTable, adapterData.expandableGroup.listeners)
            .addStatement("return itemView")
            .build()
        )

        addFunction(FunSpec.builder("getChildView")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("listPosition", INT)
            .addParameter("expandedListPosition", INT)
            .addParameter("isLastChild", Boolean::class)
            .addParameter("convertView", GeneratorConstants.viewClassName)
            .addParameter("parent", GeneratorConstants.viewGroupClassName)
            .returns(GeneratorConstants.viewClassName)
            .addStatement("val itemView = %T.from(parent.context).inflate(%T.layout.%L, parent, false)",
                GeneratorConstants.layoutInflaterClassName,
                rClassName, adapterData.expandableItem.layoutId)
            .addStatement("val item = getChild(listPosition, expandedListPosition)")
            .addBindingDataList(rClassName, childViewTable, adapterData.expandableItem.bindingDataList)
            .addListenerBindingList(rClassName, childViewTable, adapterData.expandableItem.listeners)
            .addStatement("return itemView")
            .build()
        )

        addFunction(FunSpec.builder("getGroupId")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("position", INT)
            .returns(Long::class)
            .addStatement("return position.toLong()")
            .build()
        )

        addFunction(FunSpec.builder("getChildId")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("p0", INT)
            .addParameter("expandedListPosition", INT)
            .returns(Long::class)
            .addStatement("return expandedListPosition.toLong()")
            .build()
        )

        addFunction(FunSpec.builder("getGroup")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("position", INT)
            .returns(groupClassName)
            .addStatement("return groupList[position]")
            .build()
        )

        addFunction(FunSpec.builder("getGroupCount")
            .addModifiers(KModifier.OVERRIDE)
            .returns(Int::class)
            .addStatement("return groupList.size")
            .build()
        )

        addFunction(FunSpec.builder("getChild")
            .addModifiers(KModifier.OVERRIDE)
            .returns(itemClassName)
            .addParameter("listPosition", INT)
            .addParameter("expandedListPosition", INT)
            .addStatement("return itemsMap[groupList[listPosition]]!![expandedListPosition]")
            .build()
        )

        addFunction(FunSpec.builder("getChildrenCount")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("position", INT)
            .addStatement("""  
                return if (itemsMap.containsKey(groupList[position])) {
                    itemsMap[groupList[position]]!!.size
                } else {
                    0
                }
            """.trimIndent())
            .returns(Int::class)
            .build()
        )

        addFunction(FunSpec.builder("hasStableIds")
            .addModifiers(KModifier.OVERRIDE)
            .returns(Boolean::class)
            .addStatement("return true")
            .build()
        )

        addFunction(FunSpec.builder("isChildSelectable")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("p0", INT)
            .addParameter("p1", INT)
            .returns(Boolean::class)
            .addStatement("return true")
            .build()
        )

    }
}