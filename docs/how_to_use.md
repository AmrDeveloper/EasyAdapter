# How to use?

EasyAdapter has 3 type of annotations that help you to generate your adapter

- Adapters Annotations:
Used to select the Adapter type and set the list item for it.

- Binds Annotations:
Used to bind this field value into view setter.

- Listeners:
Used to annotate the model class to select which listener you want to generate.

## Example
```
const val appId = "com.amrdeveloper.app"
const val modelListItem = "list_item_model"

@BindListener(ListenerType.OnClick)
@BindListener(ListenerType.OnLongClick)
@BindListener(ListenerType.OnClick, "model_name")
@ListAdapter(appId, modelListItem)
data class Model (

    @BindText("model_name")
    val name : String,

    @BindImage(ImageLoader.COIL, "model_avatar")
    val avatarUrl : String,

    @BindAlpha("model_avatar")
    val avatarAlpha : Float,
)
```