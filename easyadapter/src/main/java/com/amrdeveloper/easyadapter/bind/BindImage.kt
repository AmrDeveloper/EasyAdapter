package com.amrdeveloper.easyadapter.bind

import com.amrdeveloper.easyadapter.option.ImageLoader

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class BindImage (
    val loader : ImageLoader,
    val viewId : String,
    val value : String,
)