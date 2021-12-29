package com.amrdeveloper.easyadapter.compiler

import com.amrdeveloper.easyadapter.adapter.*
import com.amrdeveloper.easyadapter.bind.BindExpandable
import com.amrdeveloper.easyadapter.compiler.data.adapter.AdapterData
import com.amrdeveloper.easyadapter.compiler.data.bind.BindExpandableData
import com.amrdeveloper.easyadapter.compiler.generator.*
import com.amrdeveloper.easyadapter.compiler.parser.AdapterParser
import com.amrdeveloper.easyadapter.compiler.utils.EasyAdapterLogger
import com.squareup.kotlinpoet.FileSpec
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

class EasyAdapterProcessor : AbstractProcessor() {

    private lateinit var elementUtils : Elements
    private lateinit var logger: EasyAdapterLogger
    private lateinit var adapterParser: AdapterParser
    private lateinit var generatedDirectory: Filer

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        processingEnv?.let {
            elementUtils = it.elementUtils
            logger = EasyAdapterLogger(it)
            adapterParser = AdapterParser(elementUtils, logger)
            generatedDirectory = processingEnv.filer
        }
    }

    override fun process(annotations: MutableSet<out TypeElement>, environment: RoundEnvironment): Boolean {
        environment.getElementsAnnotatedWith(RecyclerAdapter::class.java).forEach {
            val adapter = adapterParser.parseRecyclerAdapter(it)
            generateAdapterSourceFile(adapter, RecyclerAdapterGenerator(adapter))
        }

        environment.getElementsAnnotatedWith(ListAdapter::class.java).forEach {
            val adapter = adapterParser.parseListAdapter(it)
            generateAdapterSourceFile(adapter, ListAdapterGenerator(adapter))
        }

        environment.getElementsAnnotatedWith(PagingDataAdapter::class.java).forEach {
            val adapter = adapterParser.parsePagingDataAdapter(it)
            generateAdapterSourceFile(adapter, PagingDataAdapterGenerator(adapter))
        }

        environment.getElementsAnnotatedWith(PagedListAdapter::class.java).forEach {
            val adapter = adapterParser.parsePagedListAdapter(it)
            generateAdapterSourceFile(adapter, PagedListAdapterGenerator(adapter))
        }

        environment.getElementsAnnotatedWith(ArrayAdapter::class.java).forEach {
            val adapter = adapterParser.parseArrayAdapter(it)
            generateAdapterSourceFile(adapter, ArrayAdapterGenerator(adapter))
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
                generateAdapterSourceFile(adapter, ExpandableAdapterGenerator(adapter))
            }
        }

        return true
    }

    private fun generateAdapterSourceFile(adapter: AdapterData, adapterGenerator: AdapterGenerator) {
        FileSpec.builder(adapter.adapterPackageName, adapter.adapterClassName)
            .addType(adapterGenerator.generate()).build()
            .writeTo(generatedDirectory)
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