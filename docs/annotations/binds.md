# Binds Annotations:

### @BindText
Used to annotate String field to use it as a text for TextView

Parameters:

- viewId: the id of the TextView

- condition: a condition to used and apply the bind only if this condition is evaluated to true

Examples:

```kotlin
@BindText("name")
val name : String
```

```kotlin
@BindText("name", condition = "item.name.isNotEmpty()")
val name : String
```

### @BindImage
Used to annotate String which represent the image path to load it into ImageView

Parameters:

- viewId: the id of the ImageView

- loader: can be ImageLoader.PICASSO, ImageLoader.GLIDE, ImageLoader.COIL

- condition: a condition to used and apply the bind only if this condition is evaluated to true

Examples:

```kotlin
@BindImage(ImageLoader.COIL, "main_background")
val wallpaper : String
```

```kotlin
@BindImage(ImageLoader.COIL, "main_background", "item.wallpaper.isNotEmpty()")
val wallpaper : String
```

### @BindGif
Used to annotate integer which represent the gif raw file name to load it into ImageView

Parameters:

- viewId: the id of the ImageView

- loader: can be GifLoader.GLIDE, GifLoader.COIL

- condition: a condition to used and apply the bind only if this condition is evaluated to true

Examples:

```kotlin
@BindImage(GifLoader.COIL, "main_background")
val wallpaper : Int
```

```kotlin
@BindImage(GifLoader.COIL, "main_background", "item.wallpaper.isNotEmpty()")
val wallpaper : Int
```

### @BindImageRes
Used to annotate int which represent the resource id to used as an resource for ImageView

Parameters:

- viewId: the id of the ImageView

- condition: a condition to used and apply the bind only if this condition is evaluated to true

Examples:

```kotlin
@BindImageRes("main_background")
val wallpaper : Int = R.drawable.main_background
```

```kotlin
@BindImageRes("main_background", "item.wallpaper != -1")
val wallpaper : Int = R.drawable.main_background
```

### @BindBackgroundRes

Used to annotate int which represent the resource id to used as an background for ImageView

Parameters:

- viewId: the id of the ImageView

- condition: a condition to used and apply the bind only if this condition is evaluated to true

Examples:

```kotlin
@BindBackgroundRes("main_background")
val wallpaper : Int = R.drawable.main_background
```

```kotlin
@BindBackgroundRes("main_background", "item.wallpaper != -1")
val wallpaper : Int = R.drawable.main_background
```

### @BindBackgroundColor

Used to annotate int which represent the color to used as an background color for ImageView

Parameters:
- viewId: the id of the ImageView

- condition: a condition to used and apply the bind only if this condition is evaluated to true

Examples:

```kotlin
@BindBackgroundRes("main_background")
val background : Int = Color.BLACK
```

```kotlin
@BindBackgroundRes("main_background", "item.wallpaper != -1")
val background : Int = Color.BLACK
```

### @BindAlpha

Used to annotate floats which represent the alpha value to used with View

Parameters:

- viewId: the id of the View

- condition: a condition to used and apply the bind only if this condition is evaluated to true

Examples:

```kotlin
@BindAlpha("main_background")
val alphaVal : Float
```

```kotlin
@BindAlpha("main_background", "item.alphaVal > 0")
val alphaVal : Float
```

### @BindVisibility

Used to annotate integers which represent View visibility

Parameters:

- viewId: the id of the View

- condition: a condition to used and apply the bind only if this condition is evaluated to true

Examples:

```kotlin
@BindVisibility("main_background")
val backgroundVisibility : Int = View.GONE
```

```kotlin
@BindVisibility("main_background", "item.background.isEmpty()")
val backgroundVisibility : Int = View.GONE
```

### @BindTextColor

Used to annotate integers which represent Text color

Parameters:

- viewId: the id of the View

- condition: a condition to used and apply the bind only if this condition is evaluated to true

Examples:

```kotlin
@BindTextColor("user_name")
val titleColor : Int = Color.BLACK
```

```kotlin
@BindTextColor("user_name", "item.title.isEmpty()")
val titleColor : Int = Color.BLACK
```

### @BindLottieRaw

Used to annotate integers which represent lottie animation raw res id

Parameters:

- viewId: the id of the View

- condition: a condition to used and apply the bind only if this condition is evaluated to true

Examples:

```kotlin
@BindLottieRaw("lottie_view")
val lottieAnimation : Int = R.raw.animation_file
```

```kotlin
@BindLottieRaw("lottie_view", "item.state")
val lottieAnimation : Int = R.raw.animation_file
```

### @BindLottieUrl

Used to annotate integers which represent lottie animation url

Parameters:

- viewId: the id of the View

- condition: a condition to used and apply the bind only if this condition is evaluated to true

Examples:

```kotlin
@BindLottieUrl("lottie_view")
val lottieAnimationUrl : String
```

```kotlin
@BindLottieUrl("lottie_view", "item.state")
val lottieAnimationUrl : String
```