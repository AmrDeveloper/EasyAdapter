package com.amrdeveloper.easyadapter.compiler.data.listener

import com.amrdeveloper.easyadapter.option.ListenerType
import com.squareup.kotlinpoet.ClassName

abstract class ListenerData {
    abstract val modelName : String
    abstract val viewId : String
    abstract val listenerType : ListenerType
    abstract val listenerInterfaceName : String
    abstract val listenerFunctionName : String
    abstract val listenerVarName : String
    abstract val listenerArgs : Map<String, ClassName>
    abstract val listenerBind : String
    abstract val defaultListenerFormat : String
}