package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.compiler.generator.GeneratorConstants
import com.amrdeveloper.easyadapter.option.ListenerType
import com.squareup.kotlinpoet.*

data class CheckedListenerData (
    override val modelName: String,
    override val viewId: String,
    override val viewClassName: ClassName = GeneratorConstants.compoundButtonClassName,
    override val listenerType: ListenerType = ListenerType.OnCheckedChange,
    override val defaultListenerFormat : String = "setOnCheckedChangeListener{ _, isChecked -> \n%L}",
) : ListenerData() {

    override fun generateInterfaceDeclarations(builder: TypeSpec.Builder, modelClassName: ClassName) {
        val listenerFunctionName = "on${modelName}${getFormattedViewId()}CheckedListener"

        builder.addType(
            TypeSpec.funInterfaceBuilder(getListenerInterfaceName())
                .addFunction(
                    FunSpec.builder(listenerFunctionName)
                        .addModifiers(KModifier.ABSTRACT)
                        .addParameter("model", modelClassName)
                        .addParameter("isChecked", BOOLEAN)
                        .build()
                )
                .build()
        )
    }

    override fun generateBinds(builder: FunSpec.Builder, rClassName: ClassName) {
        val listenerFunctionName = "on${modelName}${getFormattedViewId()}CheckedListener"
        val listenerBinding = """
            if (::${getListenerVarName()}.isInitialized) {
                ${getListenerVarName()}.$listenerFunctionName(item, isChecked)
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