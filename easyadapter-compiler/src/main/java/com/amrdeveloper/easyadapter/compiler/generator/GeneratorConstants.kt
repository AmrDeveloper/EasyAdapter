package com.amrdeveloper.easyadapter.compiler.generator

import com.squareup.kotlinpoet.ClassName

object GeneratorConstants {

    val viewGroupClassName = ClassName("android.view", "ViewGroup")
    val viewClassName = ClassName("android.view", "View")

    val recyclerAdapterClassName = ClassName("androidx.recyclerview.widget.RecyclerView", "Adapter")
    val recyclerViewHolderClassName = ClassName("androidx.recyclerview.widget.RecyclerView", "ViewHolder")

    val listClassName = ClassName("kotlin.collections", "List")

}