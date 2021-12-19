package com.amrdeveloper.easyadapter.compiler.parser

import com.amrdeveloper.easyadapter.adapter.*
import com.amrdeveloper.easyadapter.bind.*
import com.amrdeveloper.easyadapter.compiler.data.adapter.*
import com.amrdeveloper.easyadapter.compiler.data.bind.*
import com.amrdeveloper.easyadapter.compiler.data.listener.ClickListenerData
import com.amrdeveloper.easyadapter.compiler.data.listener.ListenerData
import com.amrdeveloper.easyadapter.compiler.data.listener.LongClickListenerData
import com.amrdeveloper.easyadapter.compiler.data.listener.TouchListenerData
import com.amrdeveloper.easyadapter.option.ListenerType
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview

class AdapterKspParser(private val logger: KSPLogger) {

    @OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
    fun parseRecyclerAdapter(classDeclaration : KSClassDeclaration) : RecyclerAdapterData {
        val className = classDeclaration.simpleName.getShortName()
        val adapterPackageName = classDeclaration.packageName.asString()
        val recyclerAdapterAnnotation = classDeclaration.getAnnotationsByType(RecyclerAdapter::class).first()
        val appPackageName = recyclerAdapterAnnotation.appPackageName
        val layoutId = recyclerAdapterAnnotation.layoutId
        val generateUpdateData = recyclerAdapterAnnotation.generateUpdateData
        val adapterClassName = if (recyclerAdapterAnnotation.customClassName.isEmpty()) "${className}RecyclerAdapter" else recyclerAdapterAnnotation.customClassName
        val viewBindingDataList = parseAdapterBindingList(classDeclaration)
        val adapterListeners = parseAdapterListeners(classDeclaration)

        return RecyclerAdapterData (
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
    fun parseListAdapter(classDeclaration : KSClassDeclaration) : ListAdapterData {
        val className = classDeclaration.simpleName.getShortName()
        val adapterPackageName = classDeclaration.packageName.asString()
        val listAdapterAnnotation = classDeclaration.getAnnotationsByType(ListAdapter::class).first()
        val appPackageName = listAdapterAnnotation.appPackageName
        val layoutId = listAdapterAnnotation.layoutId
        val diffUtilContent = listAdapterAnnotation.diffUtilContent
        val adapterClassName = if (listAdapterAnnotation.customClassName.isEmpty()) "${className}ListAdapter" else listAdapterAnnotation.customClassName
        val viewBindingDataList = parseAdapterBindingList(classDeclaration)
        val adapterListeners = parseAdapterListeners(classDeclaration)

        return ListAdapterData (
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
    fun parsePagingDataAdapter(classDeclaration : KSClassDeclaration) : PagingAdapterData {
        val className = classDeclaration.simpleName.getShortName()
        val adapterPackageName = classDeclaration.packageName.asString()
        val pagingDataAdapterAnnotation = classDeclaration.getAnnotationsByType(PagingDataAdapter::class).first()
        val appPackageName = pagingDataAdapterAnnotation.appPackageName
        val layoutId = pagingDataAdapterAnnotation.layoutId
        val diffUtilContent = pagingDataAdapterAnnotation.diffUtilContent
        val adapterClassName = if (pagingDataAdapterAnnotation.customClassName.isEmpty()) "${className}PagingDataAdapter" else pagingDataAdapterAnnotation.customClassName
        val viewBindingDataList = parseAdapterBindingList(classDeclaration)
        val adapterListeners = parseAdapterListeners(classDeclaration)

        return PagingAdapterData (
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
    fun parsePagedListAdapter(classDeclaration : KSClassDeclaration) : PagedListAdapterData {
        val className = classDeclaration.simpleName.getShortName()
        val adapterPackageName = classDeclaration.packageName.asString()
        val pagedListAnnotation = classDeclaration.getAnnotationsByType(PagedListAdapter::class).first()
        val appPackageName = pagedListAnnotation.appPackageName
        val layoutId = pagedListAnnotation.layoutId
        val diffUtilContent = pagedListAnnotation.diffUtilContent
        val adapterClassName = if (pagedListAnnotation.customClassName.isEmpty()) "${className}PagedListAdapter" else pagedListAnnotation.customClassName
        val viewBindingDataList = parseAdapterBindingList(classDeclaration)
        val adapterListeners = parseAdapterListeners(classDeclaration)

        return PagedListAdapterData (
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
    fun parseArrayAdapter(classDeclaration : KSClassDeclaration) : ArrayAdapterData {
        val className = classDeclaration.simpleName.getShortName()
        val adapterPackageName = classDeclaration.packageName.asString()
        val arrayAdapterAnnotation = classDeclaration.getAnnotationsByType(ArrayAdapter::class).first()
        val appPackageName = arrayAdapterAnnotation.appPackageName
        val layoutId = arrayAdapterAnnotation.layoutId
        val adapterClassName = if (arrayAdapterAnnotation.customClassName.isEmpty()) "${className}ArrayAdapter" else arrayAdapterAnnotation.customClassName
        val viewBindingDataList = parseAdapterBindingList(classDeclaration)
        val adapterListeners = parseAdapterListeners(classDeclaration)

        return ArrayAdapterData (
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
    fun parseAdapterBindingList(classDeclaration : KSClassDeclaration) : List<BindingData> {
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
        }

        return viewBindingDataList
    }

    @OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
    fun parseAdapterListeners(classDeclaration : KSClassDeclaration) : Set<ListenerData> {
        val listeners = mutableSetOf<ListenerData>()
        classDeclaration.getAnnotationsByType(BindListeners::class).forEach { bindListeners ->
            bindListeners.value.forEach {
                when (it.listenerType) {
                    ListenerType.OnClick -> {
                        val listener = ClickListenerData(it.viewId)
                        if (listeners.add(listener).not()) {
                            logger.warn("You declared the same OnClick Listener twice", classDeclaration)
                        }
                    }
                    ListenerType.OnLongClick -> {
                        val listener = LongClickListenerData(it.viewId)
                        if (listeners.add(listener).not()) {
                            logger.warn("You declared the same OnLongClick Listener twice", classDeclaration)
                        }
                    }
                    ListenerType.OnTouch -> {
                        val listener = TouchListenerData(it.viewId)
                        if (listeners.add(listener).not()) {
                            logger.warn("You declared the same OnTouch Listener twice", classDeclaration)
                        }
                    }
                }
            }
        }

        return listeners
    }

}