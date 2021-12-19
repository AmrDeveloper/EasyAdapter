package com.amrdeveloper.easyadapter.compiler

import com.amrdeveloper.easyadapter.adapter.*
import com.amrdeveloper.easyadapter.compiler.generator.*
import com.amrdeveloper.easyadapter.compiler.parser.AdapterKspParser
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.writeTo

class EasyAdapterKspProcessor(private val env: SymbolProcessorEnvironment) : SymbolProcessor {

    @OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val logger = env.logger
        val codeGenerator = env.codeGenerator
        val adapterParser = AdapterKspParser(logger)
        val dependencies =  Dependencies(false, *resolver.getAllFiles().toList().toTypedArray())

        resolver.getSymbolsWithAnnotation(RecyclerAdapter::class.java.name).forEach { classDeclaration ->
            if (classDeclaration is KSClassDeclaration) {
                val adapter = adapterParser.parseRecyclerAdapter(classDeclaration)
                val recyclerAdapter = RecyclerAdapterGenerator(adapter).generate()
                val fileKotlinPoet = FileSpec.builder(adapter.adapterPackageName, adapter.adapterClassName)
                fileKotlinPoet.addType(recyclerAdapter).build().writeTo(codeGenerator, dependencies)
            }
        }

        resolver.getSymbolsWithAnnotation(ListAdapter::class.java.name).forEach { classDeclaration ->
            if (classDeclaration is KSClassDeclaration) {
                val adapter = adapterParser.parseListAdapter(classDeclaration)
                val listAdapter = ListAdapterGenerator(adapter).generate()
                val fileKotlinPoet = FileSpec.builder(adapter.adapterPackageName, adapter.adapterClassName)
                fileKotlinPoet.addType(listAdapter).build().writeTo(codeGenerator, dependencies)
            }
        }

        resolver.getSymbolsWithAnnotation(PagingDataAdapter::class.java.name).forEach { classDeclaration ->
            if (classDeclaration is KSClassDeclaration) {
                val adapter = adapterParser.parsePagingDataAdapter(classDeclaration)
                val pagingDataAdapter = PagingDataAdapterGenerator(adapter).generate()
                val fileKotlinPoet = FileSpec.builder(adapter.adapterPackageName, adapter.adapterClassName)
                fileKotlinPoet.addType(pagingDataAdapter).build().writeTo(codeGenerator, dependencies)
            }
        }

        resolver.getSymbolsWithAnnotation(PagedListAdapter::class.java.name).forEach { classDeclaration ->
            if (classDeclaration is KSClassDeclaration) {
                val adapter = adapterParser.parsePagedListAdapter(classDeclaration)
                val pagedListAdapter = PagedListAdapterGenerator(adapter).generate()
                val fileKotlinPoet = FileSpec.builder(adapter.adapterPackageName, adapter.adapterClassName)
                fileKotlinPoet.addType(pagedListAdapter).build().writeTo(codeGenerator, dependencies)
            }
        }

        resolver.getSymbolsWithAnnotation(ArrayAdapter::class.java.name).forEach { classDeclaration ->
            if (classDeclaration is KSClassDeclaration) {
                val adapter = adapterParser.parseArrayAdapter(classDeclaration)
                val adapterGenerator = ArrayAdapterGenerator(adapter).generate()
                val fileKotlinPoet = FileSpec.builder(adapter.adapterPackageName, adapter.adapterClassName)
                fileKotlinPoet.addType(adapterGenerator).build().writeTo(codeGenerator, dependencies)
            }
        }

        return emptyList()
    }
}

class KspProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment) = EasyAdapterKspProcessor(environment)
}