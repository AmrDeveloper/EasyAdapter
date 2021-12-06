package com.amrdeveloper.easyadapter.compiler

import com.amrdeveloper.easyadapter.adapter.RecyclerAdapter
import com.amrdeveloper.easyadapter.bind.BindBackgroundColor
import com.amrdeveloper.easyadapter.bind.BindImageRes
import com.amrdeveloper.easyadapter.bind.BindBackgroundRes
import com.amrdeveloper.easyadapter.bind.BindText
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
            val adapterModel = parseRecyclerAdapterClass(adapterElement)
            FileSpec.builder(adapterModel.adapterPackageName, adapterModel.adapterClassName)
                .addType(RecyclerAdapterGenerator(adapterModel).generate())
                .build()
                .writeTo(File(kaptGeneratedDir))
        }

        return true
    }

    private fun parseRecyclerAdapterClass(element: Element) : AdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = processingEnv.elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation(RecyclerAdapter::class.java)
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val adapterClassName = if (annotation.customClassName.isEmpty()) "${className}Adapter" else annotation.customClassName
        val viewBindingDataList = parseAdapterBindingList(element.enclosedElements)

        return AdapterData (
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList
        )
    }

    private fun parseAdapterBindingList(enclosedElements : List<Element>) : List<BindingData> {
        val viewBindingDataList = mutableListOf<BindingData>()
        enclosedElements.forEach {
            val textViewBinding = it.getAnnotation(BindText::class.java)
            if (textViewBinding != null) {
                val binding = BindingTextData(textViewBinding.value, textViewBinding.viewId)
                viewBindingDataList.add(binding)
            }

            val imageResBinding = it.getAnnotation(BindImageRes::class.java)
            if (imageResBinding != null) {
                val binding = BindImageResData(imageResBinding.value, imageResBinding.viewId)
                viewBindingDataList.add(binding)
            }

            val backgroundResBinding = it.getAnnotation(BindBackgroundRes::class.java)
            if (backgroundResBinding != null) {
                val binding = BindBackgroundResData(backgroundResBinding.value, backgroundResBinding.viewId)
                viewBindingDataList.add(binding)
            }

            val backgroundColorBinding = it.getAnnotation(BindBackgroundColor::class.java)
            if (backgroundColorBinding != null) {
                val binding = BindBackgroundColorData(backgroundColorBinding.value, backgroundColorBinding.viewId)
                viewBindingDataList.add(binding)
            }
        }
        return viewBindingDataList
    }

    override fun getSupportedAnnotationTypes() = mutableSetOf<String> (
        RecyclerAdapter::class.java.canonicalName
    )

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}