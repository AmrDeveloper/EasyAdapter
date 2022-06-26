package com.amrdeveloper.easyadapter.compiler.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asTypeName

object GeneratorConstants {

    val contextClassName = ClassName("android.content", "Context")
    val viewGroupClassName = ClassName("android.view", "ViewGroup")
    val viewClassName = ClassName("android.view", "View")
    val textViewClassName = ClassName("android.widget", "TextView")
    val editableClassName = ClassName("android.text", "Editable")
    val compoundButtonClassName = ClassName("android.widget", "CompoundButton")
    val layoutInflaterClassName = ClassName("android.view", "LayoutInflater")

    val motionEventClassName = ClassName("android.view", "MotionEvent")

    val arrayAdapterClassName = ClassName("android.widget", "ArrayAdapter")

    val recyclerListAdapterClassname = ClassName("androidx.recyclerview.widget", "ListAdapter")
    val recyclerAdapterClassName = ClassName("androidx.recyclerview.widget.RecyclerView", "Adapter")

    val pagingDataAdapterClassName = ClassName("androidx.paging", "PagingDataAdapter")
    val pagedListAdapterClassName = ClassName("androidx.paging", "PagedListAdapter")

    val recyclerViewHolderClassName = ClassName("androidx.recyclerview.widget.RecyclerView", "ViewHolder")
    val diffUtilCallbackClassName = ClassName("androidx.recyclerview.widget.DiffUtil", "ItemCallback")

    val baseExpandableAdapterClassName = ClassName("android.widget", "BaseExpandableListAdapter")

    val mapClassName = ClassName("kotlin.collections", "Map")
    val listClassName = ClassName("kotlin.collections", "List")
    val picassoClassName = ClassName("com.squareup.picasso", "Picasso")
    val coilClassName = ClassName("coil", "Coil")
    val coilImageRequestClassName = ClassName("coil.request", "ImageRequest")
    val glideClassName = ClassName("com.bumptech.glide", "Glide")
}