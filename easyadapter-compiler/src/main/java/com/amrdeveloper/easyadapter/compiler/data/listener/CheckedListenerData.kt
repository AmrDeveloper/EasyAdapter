package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.compiler.generator.GeneratorConstants
import com.amrdeveloper.easyadapter.option.ListenerType
import com.squareup.kotlinpoet.BOOLEAN
import com.squareup.kotlinpoet.ClassName

data class CheckedListenerData (
    override val modelName: String,
    override val viewId: String,
    override val viewClassName: ClassName = GeneratorConstants.compoundButtonClassName,
    override val listenerType: ListenerType = ListenerType.OnCheckedChange,
    override val listenerArgs: Map<String, ClassName> = mapOf (
        "isChecked" to BOOLEAN
    ),
    override val listenerBind: String = "item, isChecked",
    override val defaultListenerFormat : String = ".setOnCheckedChangeListener{ _, isChecked -> \n%L}",
) : ListenerData()