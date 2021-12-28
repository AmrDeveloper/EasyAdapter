package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.compiler.generator.GeneratorConstants
import com.amrdeveloper.easyadapter.compiler.utils.toCamelCase
import com.amrdeveloper.easyadapter.option.ListenerType
import com.squareup.kotlinpoet.*

data class ClickListenerData (
    override val modelName: String,
    override val viewId: String,
    override val viewClassName: ClassName = GeneratorConstants.viewClassName,
    override val listenerType: ListenerType = ListenerType.OnClick,
    override val defaultListenerFormat : String = "setOnClickListener{ view -> \n%L}",
) : ListenerData() {

    override fun generateInterfaceDeclarations(builder: TypeSpec.Builder, modelClassName: ClassName) {
        val listenerFunctionName = "on${modelName}${viewId.toCamelCase()}ClickListener"

        builder.addType(
            TypeSpec.funInterfaceBuilder(getListenerInterfaceName())
                .addFunction(
                    FunSpec.builder(listenerFunctionName)
                        .addModifiers(KModifier.ABSTRACT)
                        .addParameter(ParameterSpec("model", modelClassName))
                        .addParameter(ParameterSpec("view", GeneratorConstants.viewClassName))
                        .build()
                ).build()
        )
    }

    override fun generateBinds(builder: FunSpec.Builder, rClassName: ClassName) {
        val listenerFunctionName = "on${modelName}${viewId.toCamelCase()}ClickListener"
        val listenerBinding = """
            if (::${getListenerVarName()}.isInitialized) {
                ${getListenerVarName()}.$listenerFunctionName(item, view)
            }
        """.trimIndent()
        if (viewId == "itemView") {
            builder.addStatement("itemView.${defaultListenerFormat}", listenerBinding)
        } else {
            builder.addStatement(
                "itemView.findViewById<%L>(%L.id.${viewId}).${defaultListenerFormat}",
                viewClassName,
                rClassName,
                listenerBinding
            )
        }
    }
}