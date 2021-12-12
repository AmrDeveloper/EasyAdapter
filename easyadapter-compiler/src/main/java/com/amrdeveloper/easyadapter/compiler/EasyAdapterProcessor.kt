package com.amrdeveloper.easyadapter.compiler

import com.amrdeveloper.easyadapter.adapter.*
import com.amrdeveloper.easyadapter.compiler.generator.*
import com.amrdeveloper.easyadapter.compiler.parser.AdapterParser
import com.amrdeveloper.easyadapter.compiler.utils.EasyAdapterLogger
import com.squareup.kotlinpoet.FileSpec
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

class EasyAdapterProcessor : AbstractProcessor() {

    private lateinit var logger: EasyAdapterLogger
    private lateinit var elementUtils : Elements

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        processingEnv?.let {
            logger = EasyAdapterLogger(it)
            elementUtils = it.elementUtils
        }
    }

    override fun process(annotations: MutableSet<out TypeElement>, environment: RoundEnvironment): Boolean {
        val generatedDirectory = processingEnv.filer

        environment.getElementsAnnotatedWith(RecyclerAdapter::class.java).forEach {
            val adapter = AdapterParser.parseRecyclerAdapter(elementUtils, it)
            FileSpec.builder(adapter.adapterPackageName, adapter.adapterClassName)
                .addType(RecyclerAdapterGenerator(adapter).generate()).build()
                .writeTo(generatedDirectory)
        }

        environment.getElementsAnnotatedWith(ListAdapter::class.java).forEach {
            val adapter = AdapterParser.parseListAdapter(elementUtils, it)
            FileSpec.builder(adapter.adapterPackageName, adapter.adapterClassName)
                .addType(ListAdapterGenerator(adapter).generate()).build()
                .writeTo(generatedDirectory)
        }

        environment.getElementsAnnotatedWith(PagingDataAdapter::class.java).forEach {
            val pagingAdapter = AdapterParser.parsePagingDataAdapter(elementUtils, it)
            FileSpec.builder(pagingAdapter.adapterPackageName, pagingAdapter.adapterClassName)
                .addType(PagingDataAdapterGenerator(pagingAdapter).generate()).build()
                .writeTo(generatedDirectory)
        }

        environment.getElementsAnnotatedWith(PagedListAdapter::class.java).forEach {
            val pagedAdapter = AdapterParser.parsePagedListAdapter(elementUtils, it)
            FileSpec.builder(pagedAdapter.adapterPackageName, pagedAdapter.adapterClassName)
                .addType(PagedListAdapterGenerator(pagedAdapter).generate()).build()
                .writeTo(generatedDirectory)
        }

        environment.getElementsAnnotatedWith(ArrayAdapter::class.java).forEach {
            val arrayAdapter = AdapterParser.parseArrayAdapter(elementUtils, it)
            FileSpec.builder(arrayAdapter.adapterPackageName, arrayAdapter.adapterClassName)
                .addType(ArrayAdapterGenerator(arrayAdapter).generate()).build()
                .writeTo(generatedDirectory)
        }

        return true
    }

    override fun getSupportedAnnotationTypes() = mutableSetOf (
        RecyclerAdapter::class.java.canonicalName,
        ListAdapter::class.java.canonicalName,
        PagingDataAdapter::class.java.canonicalName,
        PagedListAdapter::class.java.canonicalName,
        ArrayAdapter::class.java.canonicalName
    )

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()
}