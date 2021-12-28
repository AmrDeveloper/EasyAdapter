package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.compiler.generator.GeneratorConstants
import com.amrdeveloper.easyadapter.option.ListenerType
import com.squareup.kotlinpoet.*

data class TextChangedListenerData (
    override val modelName: String,
    override val viewId: String,
    override val viewClassName: ClassName = GeneratorConstants.textViewClassName,
    override val listenerType: ListenerType = ListenerType.OnTextChange,
    override val defaultListenerFormat: String = """
        addTextChangedListener(object: android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                %L
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                %L
            }

            override fun afterTextChanged(s: Editable) {
                %L
            }
        })
    """.trimIndent()
) : ListenerData() {

    override fun generateInterfaceDeclarations(builder: TypeSpec.Builder, modelClassName: ClassName) {
        builder.addType (
            TypeSpec.interfaceBuilder(getListenerInterfaceName())
                .addFunction(
                    FunSpec.builder("beforeTextChanged")
                        .addModifiers(KModifier.ABSTRACT)
                        .addParameter(ParameterSpec("model", modelClassName))
                        .addParameter(ParameterSpec("start", INT))
                        .addParameter(ParameterSpec("count", INT))
                        .addParameter(ParameterSpec("after", INT))
                        .build()
                )
                .addFunction(
                    FunSpec.builder("onTextChanged")
                        .addModifiers(KModifier.ABSTRACT)
                        .addParameter(ParameterSpec("model", modelClassName))
                        .addParameter(ParameterSpec("start", INT))
                        .addParameter(ParameterSpec("before", INT))
                        .addParameter(ParameterSpec("count", INT))
                        .build()
                )
                .addFunction(
                    FunSpec.builder("afterTextChanged")
                        .addModifiers(KModifier.ABSTRACT)
                        .addParameter(ParameterSpec("model", modelClassName))
                        .addParameter(ParameterSpec("s", GeneratorConstants.editableClassName))
                        .build()
                ).build()
        )
    }

    override fun generateBinds(builder: FunSpec.Builder, rClassName: ClassName) {
        val beforeTextChangedBinding = """
            if (::${getListenerVarName()}.isInitialized) {
                ${getListenerVarName()}.beforeTextChanged(item, start, count, after)
            }
        """.trimIndent()

        val onTextChangedBinding = """
            if (::${getListenerVarName()}.isInitialized) {
                ${getListenerVarName()}.onTextChanged(item, start, before, count)
            }
        """.trimIndent()

        val afterTextChangedBinding = """
            if (::${getListenerVarName()}.isInitialized) {
                ${getListenerVarName()}.afterTextChanged(item, s)
            }
        """.trimIndent()

        if (viewId == "itemView") {
            builder.addStatement(
                "itemView.${defaultListenerFormat}",
                beforeTextChangedBinding,
                onTextChangedBinding,
                afterTextChangedBinding
            )
        } else {
            builder.addStatement(
                "itemView.findViewById<%L>(%L.id.${viewId}).${defaultListenerFormat}",
                viewClassName,
                rClassName,
                beforeTextChangedBinding,
                onTextChangedBinding,
                afterTextChangedBinding
            )
        }
    }

}