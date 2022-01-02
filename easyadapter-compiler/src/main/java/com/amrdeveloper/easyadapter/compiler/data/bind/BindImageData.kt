package com.amrdeveloper.easyadapter.compiler.data.bind

import com.amrdeveloper.easyadapter.compiler.generator.GeneratorConstants
import com.amrdeveloper.easyadapter.compiler.utils.ViewTable
import com.amrdeveloper.easyadapter.option.ImageLoader
import com.amrdeveloper.easyadapter.option.ViewSetterType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec

data class BindImageData (
    override var fieldName: String,
    override var viewId: String,
    val imageLoader: ImageLoader,
    override var bindType: BindType = BindType.IMAGE,
    override var viewClassType: String = "android.widget.ImageView",
    override var viewClassSetter: String = "",
    override var viewSetterType: ViewSetterType = ViewSetterType.METHOD,
) : BindingData() {

    override fun generateFieldBinding(builder: FunSpec.Builder, table: ViewTable, rClass: ClassName) {
        val variableName = declareViewVariableIfNotExists(builder, table, rClass)

        val bindingFormat = when (imageLoader) {
            ImageLoader.PICASSO -> "${GeneratorConstants.picassoClassName}.get().load(%L).into(%L)"
            ImageLoader.GLIDE -> "${GeneratorConstants.glideClassName}.with(itemView.context).load(%L).into(%L)"
            ImageLoader.COIL -> """
                ${GeneratorConstants.coilClassName}.imageLoader(itemView.context).enqueue(
                    ${GeneratorConstants.coilImageRequestClassName}.Builder(itemView.context).data(%L).target(%L).build()
                )
            """.trimIndent()
        }

        builder.addStatement (bindingFormat, getBindingValueSetter(), variableName)
    }
}