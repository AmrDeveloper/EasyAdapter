package com.amrdeveloper.easyadapter.bind

import com.amrdeveloper.easyadapter.option.ListenerType

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class BindListener (
    val listenerType : ListenerType,
    val viewId : String = "itemView"
)