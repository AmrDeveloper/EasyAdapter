package com.amrdeveloper.easyadapter.compiler.parser

import com.amrdeveloper.easyadapter.adapter.*
import com.amrdeveloper.easyadapter.bind.*
import com.amrdeveloper.easyadapter.compiler.data.adapter.*
import com.amrdeveloper.easyadapter.compiler.data.bind.*
import com.amrdeveloper.easyadapter.compiler.data.listener.*
import com.amrdeveloper.easyadapter.option.ListenerType
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toTypeName

class AdapterKspParser(private val logger: KSPLogger) {

    @OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
    fun parseRecyclerAdapter(classDeclaration: KSClassDeclaration): RecyclerAdapterData {
        val className = classDeclaration.simpleName.getShortName()
        val adapterPackageName = classDeclaration.packageName.asString()
        val annotation = classDeclaration.getAnnotationsByType(RecyclerAdapter::class).first()
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val generateUpdateData = annotation.generateUpdateData
        val adapterClassName = annotation.customClassName.ifEmpty { "${className}RecyclerAdapter" }
        val viewBindingDataList = parseAdapterBindingList(classDeclaration)
        val adapterListeners = parseAdapterListeners(classDeclaration)

        return RecyclerAdapterData(
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            adapterListeners,
            generateUpdateData,
        )
    }

    @OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
    fun parseListAdapter(classDeclaration: KSClassDeclaration): ListAdapterData {
        val className = classDeclaration.simpleName.getShortName()
        val adapterPackageName = classDeclaration.packageName.asString()
        val annotation = classDeclaration.getAnnotationsByType(ListAdapter::class).first()
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val diffUtilContent = annotation.diffUtilContent
        val adapterClassName = annotation.customClassName.ifEmpty { "${className}ListAdapter" }
        val viewBindingDataList = parseAdapterBindingList(classDeclaration)
        val adapterListeners = parseAdapterListeners(classDeclaration)

        return ListAdapterData(
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            adapterListeners,
            diffUtilContent,
        )
    }

    @OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
    fun parsePagingDataAdapter(classDeclaration: KSClassDeclaration): PagingAdapterData {
        val className = classDeclaration.simpleName.getShortName()
        val adapterPackageName = classDeclaration.packageName.asString()
        val annotation = classDeclaration.getAnnotationsByType(PagingDataAdapter::class).first()
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val diffUtilContent = annotation.diffUtilContent
        val adapterClassName = annotation.customClassName.ifEmpty { "${className}PagingDataAdapter" }
        val viewBindingDataList = parseAdapterBindingList(classDeclaration)
        val adapterListeners = parseAdapterListeners(classDeclaration)

        return PagingAdapterData(
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            adapterListeners,
            diffUtilContent,
        )
    }

    @OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
    fun parsePagedListAdapter(classDeclaration: KSClassDeclaration): PagedListAdapterData {
        val className = classDeclaration.simpleName.getShortName()
        val adapterPackageName = classDeclaration.packageName.asString()
        val annotation = classDeclaration.getAnnotationsByType(PagedListAdapter::class).first()
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val diffUtilContent = annotation.diffUtilContent
        val adapterClassName = annotation.customClassName.ifEmpty { "${className}PagedListAdapter" }
        val viewBindingDataList = parseAdapterBindingList(classDeclaration)
        val adapterListeners = parseAdapterListeners(classDeclaration)

        return PagedListAdapterData(
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            adapterListeners,
            diffUtilContent,
        )
    }

    @OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
    fun parseArrayAdapter(classDeclaration: KSClassDeclaration): ArrayAdapterData {
        val className = classDeclaration.simpleName.getShortName()
        val adapterPackageName = classDeclaration.packageName.asString()
        val annotation = classDeclaration.getAnnotationsByType(ArrayAdapter::class).first()
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val adapterClassName = annotation.customClassName.ifEmpty { "${className}ArrayAdapter" }
        val viewBindingDataList = parseAdapterBindingList(classDeclaration)
        val adapterListeners = parseAdapterListeners(classDeclaration)

        return ArrayAdapterData(
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            adapterListeners,
        )
    }

    @OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
    fun parseExpandableAdapter(classDeclaration: KSClassDeclaration, expandableMap : Map<String, BindExpandableData>) : ExpandableAdapterData {
        val className = classDeclaration.simpleName.getShortName()
        val adapterPackageName = classDeclaration.packageName.asString()
        val annotation = classDeclaration.getAnnotationsByType(ExpandableAdapter::class).first()
        val appPackageName = annotation.appPackageName
        val adapterClassName = annotation.customClassName.ifEmpty { "${className}ExpandableAdapter" }

        for(property in classDeclaration.getDeclaredProperties()) {
            if (property.isAnnotationPresent(BindExpandableMap::class)) {
                val annotatedType = property.type.toTypeName().toString()
                if (annotatedType.startsWith("kotlin.collections.Map").not()) {
                    logger.error("BindExpandableMap used only with Map class", classDeclaration)
                }

                val dataTypeArguments = property.type.element?.typeArguments
                dataTypeArguments?.let {
                    val expandableGroupType = dataTypeArguments[0]
                    var expandableGroupClassName = expandableGroupType.toTypeName().toString()
                    expandableGroupClassName = expandableGroupClassName.replace("`", "")
                    val groupExpandableData = expandableMap[expandableGroupClassName]
                    if (groupExpandableData == null) {
                        logger.error("Can't find expandable group class with name $expandableGroupClassName", classDeclaration)
                        return@let
                    }

                    val expandableItemType = dataTypeArguments[1]
                    val expandableItemList = expandableItemType.type?.element
                    var expandableItemClassName = expandableItemList?.typeArguments?.first()?.toTypeName().toString()
                    expandableItemClassName = expandableItemClassName.replace("`", "")
                    val itemExpandableData = expandableMap[expandableItemClassName]
                    if (itemExpandableData == null) {
                        logger.error("Can't find expandable item class with name $itemExpandableData", classDeclaration)
                        return@let
                    }

                    return ExpandableAdapterData(
                        appPackageName,
                        adapterPackageName,
                        adapterClassName,
                        groupExpandableData,
                        itemExpandableData
                    )
                }
            }
        }

        error("Can't find expandable classes to build the ExpandableAdapter")
    }

    @OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
    fun parseBindExpandableClass(classDeclaration: KSClassDeclaration) : BindExpandableData {
        val className = classDeclaration.simpleName.getShortName()
        val classPackageName = classDeclaration.packageName.asString()
        val annotation = classDeclaration.getAnnotationsByType(BindExpandable::class).first()
        val layoutId = annotation.layoutId
        val viewBindingDataList = parseAdapterBindingList(classDeclaration)
        val adapterListeners = parseAdapterListeners(classDeclaration)

        return BindExpandableData(
            className,
            classPackageName,
            layoutId,
            viewBindingDataList,
            adapterListeners,
        )
    }

    @OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
    fun parseAdapterBindingList(classDeclaration: KSClassDeclaration): List<BindingData> {
        val viewBindingDataList = mutableListOf<BindingData>()

        classDeclaration.getDeclaredProperties().forEach {
            val elementName = it.qualifiedName?.getShortName().toString()
            val elementType = it.type.toString()

            if (it.isAnnotationPresent(BindText::class)) {
                val annotation = it.getAnnotationsByType(BindText::class).first()
                val binding = BindingTextData(elementName, annotation.viewId)
                viewBindingDataList.add(binding)
            }

            if (it.isAnnotationPresent(BindImageRes::class)) {
                if (elementType != "Int") {
                    logger.error("@BindImageRes can used only with Int data type", it)
                }
                val annotation = it.getAnnotationsByType(BindImageRes::class).first()
                val binding = BindImageResData(elementName, annotation.viewId)
                viewBindingDataList.add(binding)
            }

            if (it.isAnnotationPresent(BindBackgroundRes::class)) {
                if (elementType != "Int") {
                    logger.error("@BindBackgroundRes can used only with Int data type", it)
                }
                val annotation = it.getAnnotationsByType(BindBackgroundRes::class).first()
                val binding = BindBackgroundResData(elementName, annotation.viewId)
                viewBindingDataList.add(binding)
            }

            if (it.isAnnotationPresent(BindBackgroundColor::class)) {
                if (elementType != "Int") {
                    logger.error("@BindBackgroundColor can used only with Int data type", it)
                }
                val annotation = it.getAnnotationsByType(BindBackgroundColor::class).first()
                val binding = BindBackgroundColorData(elementName, annotation.viewId)
                viewBindingDataList.add(binding)
            }

            if (it.isAnnotationPresent(BindVisibility::class)) {
                if (elementType != "Int") {
                    logger.error("@BindVisibility can used only with Int data type", it)
                }
                val annotation = it.getAnnotationsByType(BindVisibility::class).first()
                val binding = BindVisibilityData(elementName, annotation.viewId)
                viewBindingDataList.add(binding)
            }

            if (it.isAnnotationPresent(BindImage::class)) {
                if (elementType != "String") {
                    logger.error("@BindImage can used only with String data type", it)
                }
                val annotation = it.getAnnotationsByType(BindImage::class).first()
                val binding = BindImageData(elementName, annotation.viewId, annotation.loader)
                viewBindingDataList.add(binding)
            }

            if (it.isAnnotationPresent(BindAlpha::class)) {
                if (elementType != "Float") {
                    logger.error("@BindAlpha can used only with float data type", it)
                }
                val annotation = it.getAnnotationsByType(BindAlpha::class).first()
                val binding = BindAlphaData(elementName, annotation.viewId)
                viewBindingDataList.add(binding)
            }
        }

        return viewBindingDataList
    }

    @OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
    fun parseAdapterListeners(classDeclaration: KSClassDeclaration): Set<ListenerData> {
        val modelName = classDeclaration.simpleName.getShortName()
        val listeners = mutableSetOf<ListenerData>()
        classDeclaration.getAnnotationsByType(BindListener::class).forEach {
            val listener = when (it.listenerType) {
                ListenerType.OnClick -> ClickListenerData(modelName, it.viewId)
                ListenerType.OnLongClick -> LongClickListenerData(modelName, it.viewId)
                ListenerType.OnTouch -> TouchListenerData(modelName, it.viewId)
                ListenerType.OnCheckedChange -> CheckedListenerData(modelName, it.viewId)
                ListenerType.OnTextChange -> TextChangedListenerData(modelName, it.viewId)
            }
            val isUnique = listeners.add(listener)
            if (isUnique.not()) {
                logger.warn (
                    "You have declared ${it.listenerType} Listener more than one time in the same class",
                    classDeclaration
                )
            }
        }
        return listeners
    }

}