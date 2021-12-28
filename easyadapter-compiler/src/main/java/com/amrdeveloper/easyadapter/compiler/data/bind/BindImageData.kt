package com.amrdeveloper.easyadapter.compiler.data.bind

import com.amrdeveloper.easyadapter.compiler.generator.GeneratorConstants
import com.amrdeveloper.easyadapter.option.ImageLoader
import com.amrdeveloper.easyadapter.option.ViewSetterType

data class BindImageData (
    override var fieldName: String,
    override var viewId: String,
    val imageLoader: ImageLoader,
    override var bindType: BindType = BindType.IMAGE,
    override var viewClassType: String = "android.widget.ImageView",
    override var viewClassSetter: String = "",
    override var viewSetterType: ViewSetterType = ViewSetterType.METHOD,
    override val poetFormat: String = when (imageLoader) {
        ImageLoader.PICASSO -> "${GeneratorConstants.picassoClassName}.get().load(%L).into(itemView.findViewById<%L>(%T.id.%L))"
        ImageLoader.COIL -> "${GeneratorConstants.coilImageRequestClassName}.Builder(itemView.context).data(%L).target(itemView.findViewById<%L>(%T.id.%L)).build()"
        ImageLoader.GLIDE -> "${GeneratorConstants.glideClassName}.with(itemView.context).load(%L).into(itemView.findViewById<%L>(%T.id.%L))"
    },
) : BindingData()