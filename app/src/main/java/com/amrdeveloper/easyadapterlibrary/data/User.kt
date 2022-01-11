package com.amrdeveloper.easyadapterlibrary.data

import com.amrdeveloper.easyadapter.adapter.ArrayAdapter
import com.amrdeveloper.easyadapter.adapter.ListAdapter
import com.amrdeveloper.easyadapter.adapter.PagingDataAdapter
import com.amrdeveloper.easyadapter.adapter.RecyclerAdapter
import com.amrdeveloper.easyadapter.bind.BindExpandable
import com.amrdeveloper.easyadapter.bind.BindImage
import com.amrdeveloper.easyadapter.bind.BindListener
import com.amrdeveloper.easyadapter.bind.BindText
import com.amrdeveloper.easyadapter.option.ImageLoader
import com.amrdeveloper.easyadapter.option.ListenerType

private const val userListItem = "list_item_user"

@BindListener(ListenerType.OnHover)
@BindExpandable(userListItem)
@ArrayAdapter(appPackageId, userListItem)
@RecyclerAdapter(appPackageId, userListItem)
@ListAdapter(appPackageId, userListItem, "username")
@PagingDataAdapter(appPackageId, userListItem, "username")
data class User (

    @BindText("user_name")
    val username : String,

    @BindImage(ImageLoader.COIL, "use_avatar")
    val avatarUrl : String,
)