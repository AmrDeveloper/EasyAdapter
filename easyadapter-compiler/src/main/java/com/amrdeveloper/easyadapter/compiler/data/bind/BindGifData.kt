package com.amrdeveloper.easyadapter.compiler.data.bind

import com.amrdeveloper.easyadapter.compiler.generator.GeneratorConstants
import com.amrdeveloper.easyadapter.compiler.utils.ViewTable
import com.amrdeveloper.easyadapter.option.GifLoader
import com.amrdeveloper.easyadapter.option.ViewSetterType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec

data class BindGifData(
    override var fieldName: String,
    override var viewId: String,
    val gifLoader: GifLoader,
    override var bindType: BindType = BindType.GIF,
    override var viewClassType: String = "android.widget.ImageView",
    override var viewClassSetter: String = "",
    override var viewSetterType: ViewSetterType = ViewSetterType.METHOD,
) : BindingData() {

    override fun generateFieldBinding(builder: FunSpec.Builder, table: ViewTable, rClass: ClassName) {
        val variableName = declareViewVariableIfNotExists(builder, table, rClass)
        val bindingFormat = when(gifLoader) {
            GifLoader.GLIDE -> {
                "${GeneratorConstants.glideClassName}.with(itemView.context).asGif().load(%L).into(%L)"
            }
            GifLoader.COIL -> {
                """
                    coil.ImageLoader.Builder(itemView.context).componentRegistry {
                        if(android.os.Build.VERSION.SDK_INT > 28)
                            add(coil.decode.ImageDecoderDecoder(itemView.context))
                        else 
                            add(coil.decode.GifDecoder())
                    }.build()
                    .enqueue(${GeneratorConstants.coilImageRequestClassName}
                    .Builder(itemView.context).data(%L).target(%L)
                    .build()
                )
                """.trimIndent()
            }
        }
        builder.addStatement (bindingFormat, getBindingValueSetter(), variableName)
    }

}