package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.compiler.generator.GeneratorConstants
import com.amrdeveloper.easyadapter.option.ListenerType
import com.squareup.kotlinpoet.ClassName

data class TouchListenerData (
    override val viewId: String,
    override val listenerType: ListenerType = ListenerType.OnTouch,
    override val listenerInterfaceName: String = "On${viewId}TouchClickListener",
    override val listenerVarName: String = "on${viewId}TouchClickListener",
    override val listenerFunctionName: String = "on${viewId}TouchClick",
    override val listenerArgs: Map<String, ClassName> = mapOf (
        "view" to GeneratorConstants.viewClassName,
        "event" to GeneratorConstants.motionEventClassName
    ),
    override val listenerBind: String = "item, view, event",
    override val defaultListenerFormat : String = ".setOnTouchListener { view, event -> \n %L \n false}"
) : ListenerData()