package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.compiler.generator.GeneratorConstants
import com.amrdeveloper.easyadapter.option.ListenerType
import com.squareup.kotlinpoet.*

data class LongClickListenerData (
    override val modelName: String,
    override val viewId: String,
    override val viewClassName: ClassName = GeneratorConstants.viewClassName,
    override val listenerType: ListenerType = ListenerType.OnLongClick,
    override val defaultListenerFormat : String = ".setOnLongClickListener{ view -> \n %L \n true}"
) : ListenerData() {

    override fun generateDeclarations(builder: TypeSpec.Builder, modelClassName: ClassName) {
        val listenerClassName = ClassName("", getListenerInterfaceName())

        val parameters = mutableListOf<ParameterSpec>()
        parameters.add(ParameterSpec("model", modelClassName))
        parameters.add(ParameterSpec("view", GeneratorConstants.viewClassName))

        builder.addType(
            TypeSpec.funInterfaceBuilder(getListenerInterfaceName())
                .addFunction(
                    FunSpec.builder(getListenerFunctionName())
                        .addModifiers(KModifier.ABSTRACT)
                        .addParameters(parameters)
                        .build()
                )
                .build()
        )

        builder.addProperty(
            PropertySpec.builder(getListenerVarName(), listenerClassName)
                .mutable(true)
                .addModifiers(KModifier.LATEINIT)
                .addModifiers(KModifier.PRIVATE)
                .build()
        )

        builder.addFunction(
            FunSpec
                .builder("set${getListenerVarName().replaceFirstChar(Char::titlecase)}Listener")
                .addParameter("listener", listenerClassName)
                .addStatement("${getListenerVarName()} = listener")
                .build()
        )
    }

    override fun generateBinds(builder: FunSpec.Builder, rClassName: ClassName) {
        val listenerBinding = """
            if (::${getListenerVarName()}.isInitialized) {
                ${getListenerVarName()}.${getListenerFunctionName()}(item, view)
            }
        """.trimIndent()
        if (viewId == "itemView") {
            builder.addStatement("itemView${defaultListenerFormat}", listenerBinding)
        } else {
            builder.addStatement(
                "itemView.findViewById<%L>(%L.id.${viewId})${defaultListenerFormat}",
                viewClassName,
                rClassName,
                listenerBinding
            )
        }
    }
}