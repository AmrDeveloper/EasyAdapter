package com.amrdeveloper.easyadapter.bind

import com.amrdeveloper.easyadapter.option.ImageLoader

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class BindImage (
    val loader : ImageLoader,
    val viewId : String,
)