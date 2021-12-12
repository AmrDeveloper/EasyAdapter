package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.compiler.generator.GeneratorConstants
import com.amrdeveloper.easyadapter.option.ListenerType
import com.squareup.kotlinpoet.ClassName

data class LongClickListenerData (
    override val viewId: String,
    override val listenerType: ListenerType = ListenerType.OnLongClick,
    override val listenerInterfaceName: String = "On${viewId}LongClickListener",
    override val listenerVarName: String = "on${viewId}LongClickListener",
    override val listenerFunctionName: String = "on${viewId}LongClick",
    override val listenerArgs: Map<String, ClassName> = mapOf (
        "view" to GeneratorConstants.viewClassName
    ),
    override val listenerBind: String = "item, view",
    override val defaultListenerFormat : String = ".setOnLongClickListener { view -> \n %L \n true}"
) : ListenerData()