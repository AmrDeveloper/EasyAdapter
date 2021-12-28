package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.compiler.utils.toCamelCase
import com.amrdeveloper.easyadapter.option.ListenerType
import com.squareup.kotlinpoet.*

abstract class ListenerData {

    abstract val modelName : String
    abstract val viewId : String
    abstract val viewClassName : ClassName
    abstract val listenerType : ListenerType
    abstract val defaultListenerFormat : String

    abstract fun generateInterfaceDeclarations(builder: TypeSpec.Builder, modelClassName: ClassName)

    abstract fun generateBinds(builder: FunSpec.Builder, rClassName: ClassName)

    fun generateListenerVariable(builder: TypeSpec.Builder) {
        builder.addProperty(
            PropertySpec.builder(getListenerVarName(), getListenerClassName())
                .mutable(true)
                .addModifiers(KModifier.LATEINIT)
                .addModifiers(KModifier.PRIVATE)
                .build()
        )
    }

    fun generateListenerVariableSetter(builder: TypeSpec.Builder) {
        builder.addFunction(
            FunSpec
                .builder("set${getListenerVarName().toCamelCase(true)}Listener")
                .addParameter("listener", getListenerClassName())
                .addStatement("${getListenerVarName()} = listener")
                .build()
        )
    }

    private fun getListenerClassName() : ClassName {
        return ClassName("", getListenerInterfaceName())
    }

    fun getListenerInterfaceName() : String {
        return "On${modelName}${viewId.toCamelCase()}${listenerType.shortName}Listener"
    }

    fun getListenerVarName() : String {
        return "on${viewId.toCamelCase()}${listenerType.shortName}"
    }
}