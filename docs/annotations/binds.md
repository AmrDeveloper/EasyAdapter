# Binds Annotations:

### @BindText
Used to annotate String field to use it as a text for TextView

Parameters:

- viewId: the id of the TextView

Example:

```kotlin
@BindText("name")
val name : String
```

### @BindImage
Used to annotate String which represent the image path to load it into ImageView

Parameters:

- viewId: the id of the ImageView

- loader: can be ImageLoader.PICASSO, ImageLoader.GLIDE, ImageLoader.COIL

Example:

```kotlin
@BindImage(ImageLoader.COIL, "main_background")
val wallpaper : String
```

### @BindGif
Used to annotate integer which represent the gif raw file name to load it into ImageView

Parameters:

- viewId: the id of the ImageView

- loader: can be GifLoader.GLIDE, GifLoader.COIL

Example:

```kotlin
@BindImage(GifLoader.COIL, "main_background")
val wallpaper : Int
```

### @BindImageRes
Used to annotate int which represent the resource id to used as an resoruce for ImageView

Parameters:

- viewId: the id of the ImageView

Example:
```kotlin
@BindImageRes("main_background")
val wallpaper : Int = R.drawable.main_background
```


### @BindBackgroundRes

Used to annotate int which represent the resource id to used as an background for ImageView

Parameters:

- viewId: the id of the ImageView

Example:
```kotlin
@BindBackgroundRes("main_background")
val wallpaper : Int = R.drawable.main_background
```

### @BindBackgroundColor

Used to annotate int which represent the color to used as an background color for ImageView

Parameters:
- viewId: the id of the ImageView

Example:
```kotlin
@BindBackgroundRes("main_background")
val background : Int = Color.BLACK
```


### @BindAlpha

Used to annotate floats which represent the alpha value to used with View

Parameters:

- viewId: the id of the View

Example:

```kotlin
@BindAlpha("main_background")
val alphaVal : Float
```

### @BindVisibility

Used to annotate integers which represent View visibility

Parameters:

- viewId: the id of the View

Example:
```kotlin
@BindAlpha("main_background")
val alphaVal : Int = View.GONE
```