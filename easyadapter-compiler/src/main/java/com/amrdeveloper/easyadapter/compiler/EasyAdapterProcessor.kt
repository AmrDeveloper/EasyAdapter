package com.amrdeveloper.easyadapter.compiler

import com.amrdeveloper.easyadapter.adapter.ListAdapter
import com.amrdeveloper.easyadapter.adapter.PagedListAdapter
import com.amrdeveloper.easyadapter.adapter.PagingDataAdapter
import com.amrdeveloper.easyadapter.adapter.RecyclerAdapter
import com.amrdeveloper.easyadapter.bind.*
import com.amrdeveloper.easyadapter.compiler.generator.ListAdapterGenerator
import com.amrdeveloper.easyadapter.compiler.generator.PagedListAdapterGenerator
import com.amrdeveloper.easyadapter.compiler.generator.PagingDataAdapterGenerator
import com.amrdeveloper.easyadapter.compiler.model.ListAdapterData
import com.amrdeveloper.easyadapter.compiler.generator.RecyclerAdapterGenerator
import com.amrdeveloper.easyadapter.compiler.model.*
import com.amrdeveloper.easyadapter.compiler.utils.EasyAdapterLogger
import com.squareup.kotlinpoet.FileSpec
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

class EasyAdapterProcessor : AbstractProcessor() {

    private lateinit var logger: EasyAdapterLogger

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        processingEnv?.let {
            logger = EasyAdapterLogger(it)
        }
    }

    override fun process(annotations: MutableSet<out TypeElement>, environment: RoundEnvironment): Boolean {
        val kaptGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: return false

        val recyclerAdapterElements = environment.getElementsAnnotatedWith(RecyclerAdapter::class.java)
        for (adapterElement in recyclerAdapterElements) {
            val recyclerAdapter = parseRecyclerAdapterClass(adapterElement)
            FileSpec.builder(recyclerAdapter.adapterPackageName, recyclerAdapter.adapterClassName)
                .addType(RecyclerAdapterGenerator(recyclerAdapter).generate())
                .build()
                .writeTo(File(kaptGeneratedDir))
        }

        val listAdapterElements = environment.getElementsAnnotatedWith(ListAdapter::class.java)
        for (adapterElement in listAdapterElements) {
            val listAdapter = parseListAdapterClass(adapterElement)
            FileSpec.builder(listAdapter.adapterPackageName, listAdapter.adapterClassName)
                .addType(ListAdapterGenerator(listAdapter).generate())
                .build()
                .writeTo(File(kaptGeneratedDir))
        }

        val pagingAdapterElements = environment.getElementsAnnotatedWith(PagingDataAdapter::class.java)
        for (adapterElement in pagingAdapterElements) {
            val pagingAdapter = parsePagingDataAdapter(adapterElement)
            FileSpec.builder(pagingAdapter.adapterPackageName, pagingAdapter.adapterClassName)
                .addType(PagingDataAdapterGenerator(pagingAdapter).generate())
                .build()
                .writeTo(File(kaptGeneratedDir))
        }

        val pagedListAdapterElements = environment.getElementsAnnotatedWith(PagedListAdapter::class.java)
        for (adapterElement in pagedListAdapterElements) {
            val pagedAdapter = parsePagedListAdapter(adapterElement)
            FileSpec.builder(pagedAdapter.adapterPackageName, pagedAdapter.adapterClassName)
                .addType(PagedListAdapterGenerator(pagedAdapter).generate())
                .build()
                .writeTo(File(kaptGeneratedDir))
        }
        return true
    }

    private fun parseRecyclerAdapterClass(element: Element) : RecyclerAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = processingEnv.elementUtils.getPackageOf(element).toString()
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

    private fun parseListAdapterClass(element: Element) : ListAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = processingEnv.elementUtils.getPackageOf(element).toString()
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

    private fun parsePagingDataAdapter(element: Element) : PagingAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = processingEnv.elementUtils.getPackageOf(element).toString()
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

    private fun parsePagedListAdapter(element: Element) : PagedListAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = processingEnv.elementUtils.getPackageOf(element).toString()
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

    private fun parseAdapterBindingList(enclosedElements : List<Element>) : List<BindingData> {
        val viewBindingDataList = mutableListOf<BindingData>()
        for (element in enclosedElements) {
            val textViewBinding = element.getAnnotation(BindText::class.java)
            if (textViewBinding != null) {
                val binding = BindingTextData(textViewBinding.value, textViewBinding.viewId)
                viewBindingDataList.add(binding)
            }

            val imageResBinding = element.getAnnotation(BindImageRes::class.java)
            if (imageResBinding != null) {
                val binding = BindImageResData(imageResBinding.value, imageResBinding.viewId)
                viewBindingDataList.add(binding)
            }

            val backgroundResBinding = element.getAnnotation(BindBackgroundRes::class.java)
            if (backgroundResBinding != null) {
                val binding = BindBackgroundResData(backgroundResBinding.value, backgroundResBinding.viewId)
                viewBindingDataList.add(binding)
            }

            val backgroundColorBinding = element.getAnnotation(BindBackgroundColor::class.java)
            if (backgroundColorBinding != null) {
                val binding = BindBackgroundColorData(backgroundColorBinding.value, backgroundColorBinding.viewId)
                viewBindingDataList.add(binding)
            }

            val visibilityBinding = element.getAnnotation(BindVisibility::class.java)
            if (visibilityBinding != null) {
                val binding = BindVisibilityData(visibilityBinding.value, visibilityBinding.viewId)
                viewBindingDataList.add(binding)
            }
        }
        return viewBindingDataList
    }

    override fun getSupportedAnnotationTypes() = mutableSetOf<String> (
        RecyclerAdapter::class.java.canonicalName,
        ListAdapter::class.java.canonicalName,
        PagingDataAdapter::class.java.canonicalName,
        PagedListAdapter::class.java.canonicalName
    )

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}