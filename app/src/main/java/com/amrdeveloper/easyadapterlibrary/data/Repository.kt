package com.amrdeveloper.easyadapterlibrary.data

import com.amrdeveloper.easyadapter.bind.BindExpandable
import com.amrdeveloper.easyadapter.bind.BindListener
import com.amrdeveloper.easyadapter.bind.BindText
import com.amrdeveloper.easyadapter.option.ListenerType

@BindListener(ListenerType.OnClick)
@BindExpandable("list_item_repository")
data class Repository (

    @BindText("repository_name")
    val name : String
)