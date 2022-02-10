# How to use?

EasyAdapter has 3 types of annotations that help you to generate your adapter

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

Now after you build the project you will find a generated adapter called `ModelListAdapter`,
this default name come from the model class name (`Model`) + the adapter name (`ListAdapter`),
you can change it easily by passing your custom name in the adapter Annotation,

And now you can use this adapter like any other normal adapter

```
val adapter = ModelListAdapter()
recyclerView.adapter = adapter
```

You can find a full documentation and example for each annotation in it section.
