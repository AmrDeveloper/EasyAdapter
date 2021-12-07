package com.amrdeveloper.easyadapter.adapter

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class PagedListAdapter (
    val appPackageName : String,
    val layoutId : String,
    val diffUtilContent : String,
    val customClassName : String = "",
)