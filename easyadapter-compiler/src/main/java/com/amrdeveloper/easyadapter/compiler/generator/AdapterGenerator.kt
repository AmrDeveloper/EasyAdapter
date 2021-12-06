package com.amrdeveloper.easyadapter.compiler.generator

import com.squareup.kotlinpoet.TypeSpec

interface AdapterGenerator {
    fun generate(): TypeSpec
}