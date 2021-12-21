package com.amrdeveloper.easyadapter.compiler

import com.amrdeveloper.easyadapter.adapter.*
import com.amrdeveloper.easyadapter.bind.BindExpandable
import com.amrdeveloper.easyadapter.compiler.data.bind.BindExpandableData
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

    private lateinit var elementUtils : Elements
    private lateinit var logger: EasyAdapterLogger
    private lateinit var adapterParser: AdapterParser

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        processingEnv?.let {
            elementUtils = it.elementUtils
            logger = EasyAdapterLogger(it)
            adapterParser = AdapterParser(elementUtils, logger)
        }
    }

    override fun process(annotations: MutableSet<out TypeElement>, environment: RoundEnvironment): Boolean {
        val generatedDirectory = processingEnv.filer

        environment.getElementsAnnotatedWith(RecyclerAdapter::class.java).forEach {
            val adapter = adapterParser.parseRecyclerAdapter(it)
            FileSpec.builder(adapter.adapterPackageName, adapter.adapterClassName)
                .addType(RecyclerAdapterGenerator(adapter).generate()).build()
                .writeTo(generatedDirectory)
        }

        environment.getElementsAnnotatedWith(ListAdapter::class.java).forEach {
            val adapter = adapterParser.parseListAdapter(it)
            FileSpec.builder(adapter.adapterPackageName, adapter.adapterClassName)
                .addType(ListAdapterGenerator(adapter).generate()).build()
                .writeTo(generatedDirectory)
        }

        environment.getElementsAnnotatedWith(PagingDataAdapter::class.java).forEach {
            val pagingAdapter = adapterParser.parsePagingDataAdapter(it)
            FileSpec.builder(pagingAdapter.adapterPackageName, pagingAdapter.adapterClassName)
                .addType(PagingDataAdapterGenerator(pagingAdapter).generate()).build()
                .writeTo(generatedDirectory)
        }

        environment.getElementsAnnotatedWith(PagedListAdapter::class.java).forEach {
            val pagedAdapter = adapterParser.parsePagedListAdapter(it)
            FileSpec.builder(pagedAdapter.adapterPackageName, pagedAdapter.adapterClassName)
                .addType(PagedListAdapterGenerator(pagedAdapter).generate()).build()
                .writeTo(generatedDirectory)
        }

        environment.getElementsAnnotatedWith(ArrayAdapter::class.java).forEach {
            val arrayAdapter = adapterParser.parseArrayAdapter(it)
            FileSpec.builder(arrayAdapter.adapterPackageName, arrayAdapter.adapterClassName)
                .addType(ArrayAdapterGenerator(arrayAdapter).generate()).build()
                .writeTo(generatedDirectory)
        }

        val expandableMap = mutableMapOf<String, BindExpandableData>()
        environment.getElementsAnnotatedWith(BindExpandable::class.java).forEach {

            val expandableClass = adapterParser.parseExpandableClass(it)
            val fullClassName = "${expandableClass.modelClassPackageName}.${expandableClass.modelClassName}"
            expandableMap[fullClassName] = expandableClass
        }

        environment.getElementsAnnotatedWith(ExpandableAdapter::class.java).forEach {
            val expandableAdapter = adapterParser.parseExpandableAdapter(it, expandableMap)
            expandableAdapter?.let { adapter ->
                FileSpec.builder(adapter.adapterPackageName, adapter.adapterClassName)
                    .addType(ExpandableAdapterGenerator(adapter).generate()).build()
                    .writeTo(generatedDirectory)
            }

        }

        return true
    }

    override fun getSupportedAnnotationTypes() = mutableSetOf (
        RecyclerAdapter::class.java.canonicalName,
        ListAdapter::class.java.canonicalName,
        PagingDataAdapter::class.java.canonicalName,
        PagedListAdapter::class.java.canonicalName,
        ArrayAdapter::class.java.canonicalName,
        ExpandableAdapter::class.java.canonicalName,
        BindExpandable::class.java.canonicalName
    )

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()
}