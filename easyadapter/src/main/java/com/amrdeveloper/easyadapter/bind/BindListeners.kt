package com.amrdeveloper.easyadapter.bind

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class BindListeners (
    val value : Array<BindListener>
)