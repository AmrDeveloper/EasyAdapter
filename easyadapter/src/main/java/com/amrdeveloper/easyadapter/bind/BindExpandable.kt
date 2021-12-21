package com.amrdeveloper.easyadapter.bind

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class BindExpandable (
    val layoutId : String,
    val customClassName : String = "",
)