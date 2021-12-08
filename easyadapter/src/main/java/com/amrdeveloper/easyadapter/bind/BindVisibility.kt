package com.amrdeveloper.easyadapter.bind

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class BindVisibility (
    val viewId : String,
    val value : String,
)