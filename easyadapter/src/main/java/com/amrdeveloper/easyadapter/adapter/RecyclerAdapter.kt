package com.amrdeveloper.easyadapter.adapter

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class RecyclerAdapter (
    val appPackageName : String,
    val layoutId : String,
    val customClassName : String = "",
)