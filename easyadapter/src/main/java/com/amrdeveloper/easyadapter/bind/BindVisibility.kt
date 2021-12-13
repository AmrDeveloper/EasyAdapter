package com.amrdeveloper.easyadapter.bind

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class BindVisibility (
    val viewId : String,
)