package com.amrdeveloper.easyadapter.bind

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class BindBackgroundColor (
    val viewId : String,
)