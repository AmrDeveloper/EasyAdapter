# How to add listener?
To add listener all you need is to annotated the model class by `@BindListener`

BindListener take 2 information

1 - Listener type which can be

- OnClick

- OnLongClick

- OnTouch

- OnHover

- OnCheckedChange

- OnTextChange

2 - view id and you can ignore it if you want this listener to be for your list item layout.

Examples:

- To generate OnClickListener for your list item layout

```kotlin
@BindListener(ListenerType.OnClick)
```

- To generate onClickListener for view with id name
```kotlin
@BindListener(ListenerType.OnClick, "name")
```

Note that if you have declared the exact same listener more than one time, just one will be generated and the other will be ignored.
```