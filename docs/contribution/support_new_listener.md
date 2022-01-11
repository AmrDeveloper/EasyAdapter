# Support new Listener

This page will provide a clear steps on how you can add support for new listener.

Supporting new listener doesn't require new annotation.

- Step 1: Add new Type to ListenerType enum.
```
enum class ListenerType(val shortName: String) {
    ...
    OnNew("New")
}
```

- Step 2: In easyadapter-compiler module create new class that extend ListenerData class.
```
data class NewListenerData (
    ...override abstract field.
) : ListenerData() {
    ...override abstract method
}
```

- Step 3: Support parsing this listener type in Adapter Parser.
```
ListenerType.OnNew -> NewListenerData(modelName, it.viewId)
```

- Step 4: Support parsing this listener type in KSP Adapter Parser.
```
ListenerType.OnNew -> NewListenerData(modelName, it.viewId)
```

Now you are done, enjoy the new listener type.