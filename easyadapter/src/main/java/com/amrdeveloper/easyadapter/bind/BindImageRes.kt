package com.amrdeveloper.easyadapter.bind

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class BindImageRes (
    val viewId : String,
)