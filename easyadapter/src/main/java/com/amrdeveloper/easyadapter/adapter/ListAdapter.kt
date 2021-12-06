package com.amrdeveloper.easyadapter.adapter

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ListAdapter (
    val appPackageName : String,
    val layoutId : String,
    val diffUtilContent : String,
    val customClassName : String = "",
)