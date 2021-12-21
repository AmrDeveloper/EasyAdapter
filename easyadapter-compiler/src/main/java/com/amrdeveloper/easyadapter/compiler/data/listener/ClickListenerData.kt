package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.compiler.generator.GeneratorConstants
import com.amrdeveloper.easyadapter.option.ListenerType
import com.squareup.kotlinpoet.ClassName

data class ClickListenerData (
    override val modelName: String,
    override val viewId: String,
    override val listenerType: ListenerType = ListenerType.OnClick,
    override val listenerInterfaceName: String = "On${modelName}${viewId}ClickListener",
    override val listenerVarName: String = "on${modelName}${viewId}ClickListener",
    override val listenerFunctionName: String = "on${viewId}Click",
    override val listenerArgs: Map<String, ClassName> = mapOf (
        "view" to GeneratorConstants.viewClassName
    ),
    override val listenerBind: String = "item, view",
    override val defaultListenerFormat : String = ".setOnClickListener { view -> \n%L}",
) : ListenerData()