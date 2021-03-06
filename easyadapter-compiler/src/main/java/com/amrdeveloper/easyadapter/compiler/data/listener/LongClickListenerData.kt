package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.compiler.generator.GeneratorConstants
import com.amrdeveloper.easyadapter.compiler.utils.ViewTable
import com.amrdeveloper.easyadapter.compiler.utils.toCamelCase
import com.amrdeveloper.easyadapter.option.ListenerType
import com.squareup.kotlinpoet.*

data class LongClickListenerData (
    override val modelName: String,
    override val viewId: String,
    override val viewClassName: ClassName = GeneratorConstants.viewClassName,
    override val listenerType: ListenerType = ListenerType.OnLongClick,
    override val defaultListenerFormat : String = "setOnLongClickListener{%L}"
) : ListenerData() {

    override fun generateInterfaceDeclarations(builder: TypeSpec.Builder, modelClassName: ClassName) {
        val listenerFunctionName = "on${modelName}${viewId.toCamelCase()}LongClickListener"

        builder.addType(
            TypeSpec.funInterfaceBuilder(getListenerInterfaceName())
                .addFunction(
                    FunSpec.builder(listenerFunctionName)
                        .addModifiers(KModifier.ABSTRACT)
                        .addParameter("model", modelClassName)
                        .addParameter("view", GeneratorConstants.viewClassName)
                        .returns(BOOLEAN)
                        .build()
                )
                .build()
        )
    }

    override fun generateBinds(builder: FunSpec.Builder, table: ViewTable, rClass: ClassName) {
        val listenerFunctionName = "on${modelName}${viewId.toCamelCase()}LongClickListener"
        val listenerBinding = """
            return@setOnLongClickListener if (::${getListenerVarName()}.isInitialized) {
                ${getListenerVarName()}.$listenerFunctionName(item, it)
            } else true
        """.trimIndent()
        if (viewId == "itemView") {
            builder.addStatement("itemView.${defaultListenerFormat}", listenerBinding)
        } else {
            var variableName = table.resolve(viewId, viewClassName.canonicalName)
            if (variableName.isEmpty()) {
                variableName = table.define(viewId, viewClassName.canonicalName)
                declareViewVariable(builder, variableName, viewClassName, viewId, rClass)
            }
            builder.addStatement("$variableName.${defaultListenerFormat}", listenerBinding)
        }
    }
}