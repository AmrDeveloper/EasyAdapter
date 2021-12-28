package com.amrdeveloper.easyadapter.compiler.generator

import com.squareup.kotlinpoet.TypeSpec

abstract class AdapterGenerator {
    abstract fun generate(): TypeSpec
}