package com.amrdeveloper.easyadapter.bind

import com.amrdeveloper.easyadapter.option.ListenerType
import java.lang.annotation.Repeatable

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@Repeatable(BindListeners::class)
annotation class BindListener (
    val listenerType : ListenerType,
    val viewId : String = "itemView"
)