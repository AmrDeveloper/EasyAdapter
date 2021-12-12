package com.amrdeveloper.easyadapter.compiler.parser

import com.amrdeveloper.easyadapter.adapter.*
import com.amrdeveloper.easyadapter.bind.*
import com.amrdeveloper.easyadapter.compiler.model.*
import javax.lang.model.element.Element
import javax.lang.model.util.Elements

class AdapterParser(private val elementUtils: Elements) {

    fun parseRecyclerAdapter(element: Element) : RecyclerAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation(RecyclerAdapter::class.java)
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val generateUpdateData = annotation.generateUpdateData
        val adapterClassName = if (annotation.customClassName.isEmpty()) "${className}RecyclerAdapter" else annotation.customClassName
        val viewBindingDataList = parseAdapterBindingList(element.enclosedElements)

        return RecyclerAdapterData (
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            generateUpdateData,
        )
    }

    fun parseListAdapter(element: Element) : ListAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation(ListAdapter::class.java)
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val diffUtilContent = annotation.diffUtilContent
        val adapterClassName = if (annotation.customClassName.isEmpty()) "${className}ListAdapter" else annotation.customClassName
        val viewBindingDataList = parseAdapterBindingList(element.enclosedElements)

        return ListAdapterData (
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            diffUtilContent,
        )
    }

    fun parsePagingDataAdapter(element: Element) : PagingAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation(PagingDataAdapter::class.java)
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val diffUtilContent = annotation.diffUtilContent
        val adapterClassName = if (annotation.customClassName.isEmpty()) "${className}PagingDataAdapter" else annotation.customClassName
        val viewBindingDataList = parseAdapterBindingList(element.enclosedElements)

        return PagingAdapterData (
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            diffUtilContent,
        )
    }

    fun parsePagedListAdapter(element: Element) : PagedListAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation(PagedListAdapter::class.java)
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val diffUtilContent = annotation.diffUtilContent
        val adapterClassName = if (annotation.customClassName.isEmpty()) "${className}PagedListAdapter" else annotation.customClassName
        val viewBindingDataList = parseAdapterBindingList(element.enclosedElements)

        return PagedListAdapterData (
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            diffUtilContent,
        )
    }

    fun parseArrayAdapter(element: Element) : ArrayAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation(ArrayAdapter::class.java)
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val adapterClassName = if (annotation.customClassName.isEmpty()) "${className}ArrayAdapter" else annotation.customClassName
        val viewBindingDataList = parseAdapterBindingList(element.enclosedElements)

        return ArrayAdapterData (
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
        )
    }

    private fun parseAdapterBindingList(enclosedElements : List<Element>) : List<BindingData> {
        val viewBindingDataList = mutableListOf<BindingData>()
        for (element in enclosedElements) {
            element.getAnnotation(BindText::class.java)?.let {
                val binding = BindingTextData(it.value, it.viewId)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindImageRes::class.java)?.let {
                val binding = BindImageResData(it.value, it.viewId)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindBackgroundRes::class.java)?.let {
                val binding = BindBackgroundResData(it.value, it.viewId)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindBackgroundColor::class.java)?.let {
                val binding = BindBackgroundColorData(it.value, it.viewId)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindVisibility::class.java)?.let {
                val binding = BindVisibilityData(it.value, it.viewId)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindImage::class.java)?.let {
                val binding = BindImageData(it.value, it.viewId, it.loader)
                viewBindingDataList.add(binding)
            }
        }
        return viewBindingDataList
    }

}