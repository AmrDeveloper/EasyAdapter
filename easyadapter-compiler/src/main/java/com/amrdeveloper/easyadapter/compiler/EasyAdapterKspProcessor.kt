package com.amrdeveloper.easyadapter.compiler

import com.amrdeveloper.easyadapter.adapter.*
import com.amrdeveloper.easyadapter.compiler.data.adapter.AdapterData
import com.amrdeveloper.easyadapter.compiler.generator.*
import com.amrdeveloper.easyadapter.compiler.parser.AdapterKspParser
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.writeTo

class EasyAdapterKspProcessor(private val env: SymbolProcessorEnvironment) : SymbolProcessor {

    @OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val logger = env.logger
        val adapterParser = AdapterKspParser(logger)
        val dependencies =  Dependencies(false, *resolver.getAllFiles().toList().toTypedArray())

        resolver.getSymbolsWithAnnotation(RecyclerAdapter::class.java.name).forEach {
            if (it is KSClassDeclaration) {
                val adapter = adapterParser.parseRecyclerAdapter(it)
                val recyclerAdapter = RecyclerAdapterGenerator(adapter).generate()
                generateAdapterSourceFile(adapter, recyclerAdapter, dependencies)
            }
        }

        resolver.getSymbolsWithAnnotation(ListAdapter::class.java.name).forEach {
            if (it is KSClassDeclaration) {
                val adapter = adapterParser.parseListAdapter(it)
                val listAdapter = ListAdapterGenerator(adapter).generate()
                generateAdapterSourceFile(adapter, listAdapter, dependencies)
            }
        }

        resolver.getSymbolsWithAnnotation(PagingDataAdapter::class.java.name).forEach {
            if (it is KSClassDeclaration) {
                val adapter = adapterParser.parsePagingDataAdapter(it)
                val pagingDataAdapter = PagingDataAdapterGenerator(adapter).generate()
                generateAdapterSourceFile(adapter, pagingDataAdapter, dependencies)
            }
        }

        resolver.getSymbolsWithAnnotation(PagedListAdapter::class.java.name).forEach {
            if (it is KSClassDeclaration) {
                val adapter = adapterParser.parsePagedListAdapter(it)
                val pagedListAdapter = PagedListAdapterGenerator(adapter).generate()
                generateAdapterSourceFile(adapter, pagedListAdapter, dependencies)
            }
        }

        resolver.getSymbolsWithAnnotation(ArrayAdapter::class.java.name).forEach {
            if (it is KSClassDeclaration) {
                val adapter = adapterParser.parseArrayAdapter(it)
                val arrayAdapter = ArrayAdapterGenerator(adapter).generate()
                generateAdapterSourceFile(adapter, arrayAdapter, dependencies)
            }
        }

        return emptyList()
    }

    @KotlinPoetKspPreview
    private fun generateAdapterSourceFile(adapterData : AdapterData, adapter: TypeSpec, dependencies: Dependencies) {
        val adapterSourceFile = FileSpec.builder(adapterData.adapterPackageName, adapterData.adapterClassName)
        adapterSourceFile.addType(adapter).build().writeTo(env.codeGenerator, dependencies)
    }
}

class KspProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment) = EasyAdapterKspProcessor(environment)
}