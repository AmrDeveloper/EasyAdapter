package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.compiler.generator.GeneratorConstants
import com.amrdeveloper.easyadapter.compiler.utils.ViewTable
import com.amrdeveloper.easyadapter.compiler.utils.toCamelCase
import com.amrdeveloper.easyadapter.option.ListenerType
import com.squareup.kotlinpoet.*

data class HoverListenerData(
    override val modelName: String,
    override val viewId: String,
    override val viewClassName: ClassName = GeneratorConstants.viewClassName,
    override val listenerType: ListenerType = ListenerType.OnHover,
    override val defaultListenerFormat : String = "setOnHoverListener{v, event->%L}"
) : ListenerData() {

    override fun generateInterfaceDeclarations(builder: TypeSpec.Builder, modelClassName: ClassName) {
        val listenerFunctionName = "on${modelName}${viewId.toCamelCase()}HoverListener"

        builder.addType(
            TypeSpec.funInterfaceBuilder(getListenerInterfaceName())
                .addFunction(
                    FunSpec.builder(listenerFunctionName)
                        .addModifiers(KModifier.ABSTRACT)
                        .addParameter("model", modelClassName)
                        .addParameter("view", GeneratorConstants.viewClassName)
                        .addParameter("event", GeneratorConstants.motionEventClassName)
                        .returns(BOOLEAN)
                        .build()
                )
                .build()
        )
    }

    override fun generateBinds(builder: FunSpec.Builder, table: ViewTable, rClass: ClassName) {
        val listenerFunctionName = "on${modelName}${viewId.toCamelCase()}HoverListener"
        val listenerBinding = """
            return@setOnHoverListener if (::${getListenerVarName()}.isInitialized) {
                ${getListenerVarName()}.$listenerFunctionName(item, v, event)
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