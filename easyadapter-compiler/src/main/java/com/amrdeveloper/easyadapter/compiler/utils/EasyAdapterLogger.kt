package com.amrdeveloper.easyadapter.compiler.utils

import javax.annotation.processing.ProcessingEnvironment
import javax.tools.Diagnostic
import javax.lang.model.element.Element

class EasyAdapterLogger(private val env: ProcessingEnvironment) {

    fun note(message: String, elem: Element? = null) {
        print(Diagnostic.Kind.NOTE, message, elem)
    }

    fun warn(message: String, elem: Element? = null) {
        print(Diagnostic.Kind.WARNING, message, elem)
    }

    fun error(message: String, elem: Element? = null) {
        print(Diagnostic.Kind.ERROR, message, elem)
    }

    private fun print(kind: Diagnostic.Kind, message: String, elem: Element?) {
        print("\n")
        env.messager.printMessage(kind, message, elem)
    }
}