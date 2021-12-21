package com.amrdeveloper.easyadapter.adapter

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ExpandableAdapter (
    val appPackageName : String,
    val customClassName : String = "",
)