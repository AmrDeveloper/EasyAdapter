package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.option.ListenerType
import com.squareup.kotlinpoet.ClassName

abstract class ListenerData {

    abstract val modelName : String
    abstract val viewId : String
    abstract val viewClassName : ClassName
    abstract val listenerType : ListenerType
    abstract val listenerArgs : Map<String, ClassName>
    abstract val listenerBind : String
    abstract val defaultListenerFormat : String

    private fun getFormattedViewId() : String {
        val builder = StringBuilder()
        builder.append(viewId.first().titlecase())
        var shouldCapitalizeNext = false
        for (i in 1 until viewId.length) {
            if (viewId[i] == '_') {
                shouldCapitalizeNext = true
                continue
            }

            if (shouldCapitalizeNext) {
                builder.append(viewId[i].titlecase())
                shouldCapitalizeNext = false
                continue
            }

            builder.append(viewId[i])
        }
        return builder.toString()
    }

    fun getListenerInterfaceName() : String {
        return "On${modelName}${getFormattedViewId()}${listenerType.shortName}Listener"
    }

    fun getListenerFunctionName() : String {
        return "on${modelName}${getFormattedViewId()}${listenerType.shortName}Listener"
    }

    fun getListenerVarName() : String {
        return "on${getFormattedViewId()}${listenerType.shortName}"
    }
}