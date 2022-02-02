# EasyAdapter
<p align="center">
  <img src="media/ea-logo.png" width="128px" height="128px"/>
</p>

![Maven Central](https://img.shields.io/maven-central/v/io.github.amrdeveloper/easyadapter?color=green)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/2b074c0ffc1e4ac3b0ddf085e09e940c)](https://www.codacy.com/gh/AmrDeveloper/EasyAdapter/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=AmrDeveloper/EasyAdapter&amp;utm_campaign=Badge_Grade)
[![CodeFactor](https://www.codefactor.io/repository/github/amrdeveloper/easyadapter/badge)](https://www.codefactor.io/repository/github/amrdeveloper/easyadapter)

Android Annotation Processor library to generate adapter class easily from your model with a lot of customization

```Kotlin
const val APP_ID = "com.amrdeveloper.app"
const val MODEL_LIST_ITEM = "list_item_model"

@BindListener(ListenerType.OnClick)
@BindListener(ListenerType.OnLongClick)
@BindListener(ListenerType.OnClick, "model_name")
@ListAdapter(APP_ID, MODEL_LIST_ITEM)
data class Model (

    @BindText("model_name")
    val name : String,

    @BindImage(ImageLoader.COIL, "model_avatar")
    val avatarUrl : String,

    @BindAlpha("model_avatar")
    val avatarAlpha : Float,
)
```

- Main Features
  - Everything done in Compile time.
  - Supports Kapt and KSP processors.
  - Supports List, Recycler and Array Adapters.
  - Supports Paging2 and Paging3 Adapters.
  - Supports Expandable List Adapter.
  - Supports image loading with Picasso, Glide and Coil.
  - Supports generating DiffUtil ItemCallback.
  - Supports generating refresh data method for RecyclerAdapter.
  - Supports generating listeners.
  - Supports type checking when using annotation with invalid data type.
  - Supports default and custom name for the generated Adapter class.
  - Show clear warns and errors.

- Documentations:
  - [Full Documentation](https://amrdeveloper.github.io/EasyAdapter/)
  - [Install](docs/install.md)  
  - [How to use](docs/how_to_use.md) 
  - [Adapters Annotations](docs/annotations/adapters.md)
  - [Binds Annotations](docs/annotations/binds.md)
  - [Listeners Annotation](docs/annotations/listeners.md)  

- Documentations for Contributors:
  - [Support New Adapter](docs/contribution/support_new_adapter.md)
  - [Support New Binds](docs/contribution/support_new_bind.md)  
  - [Support New Listener](docs/contribution/support_new_listener.md)
  - [Contribute in Docs](docs/contribution/documentation.md)

### License
```
MIT License

Copyright (c) 2021 Amr Hesham

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
