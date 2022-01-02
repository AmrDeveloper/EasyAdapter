package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.compiler.utils.ViewTable
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

    abstract fun generateBinds(builder: FunSpec.Builder, table : ViewTable, rClass: ClassName)

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

    fun declareViewVariable(builder: FunSpec.Builder, name : String, viewType: ClassName, viewId: String, rClass: ClassName) {
        builder.addStatement (
            "val %L=itemView.findViewById<%T>(%T.id.%L)",
            name, viewType, rClass, viewId
        )
    }

    private fun getListenerClassName() : ClassName {
        return ClassName("", getListenerInterfaceName())
    }

    fun getListenerInterfaceName() : String {
        return "On${modelName}${viewId.toCamelCase()}${listenerType.shortName}Listener"
    }

    fun getListenerVarName() : String {
        return "on${modelName}${viewId.toCamelCase()}${listenerType.shortName}"
    }
}