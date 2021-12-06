package com.amrdeveloper.easyadapter.compiler.generator

import com.squareup.kotlinpoet.ClassName

object GeneratorConstants {

    val viewGroupClassName = ClassName("android.view", "ViewGroup")
    val viewClassName = ClassName("android.view", "View")

    val recyclerListAdapterClassname = ClassName("androidx.recyclerview.widget", "ListAdapter")
    val recyclerAdapterClassName = ClassName("androidx.recyclerview.widget.RecyclerView", "Adapter")
    val recyclerViewHolderClassName = ClassName("androidx.recyclerview.widget.RecyclerView", "ViewHolder")
    val diffUtilCallbackClassName = ClassName("androidx.recyclerview.widget.DiffUtil", "ItemCallback")

    val listClassName = ClassName("kotlin.collections", "List")
}