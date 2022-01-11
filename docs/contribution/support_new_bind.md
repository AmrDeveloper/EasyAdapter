# Support new Bind

This page will provide a clear steps on how you can add support for new binds 

- Step 1: Create new Annotation for this bind in easyadapter module.
```
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class BindNew (
    val viewId : String,
    ...Extra fields if needed
)
```

- Step 2: In easyadapter-compiler module create new class that extend BindingData class.
```
data class BindingNewData (
    ...Override abstract fields.
    ...Add Extra fields if you need them.
) : BindingData() {
    ...Override generateFieldBinding method to write code generator for this bind
}
```

- Step 3: Support parsing this bind annotation in Adapter Parser.
```
 element.getAnnotation(BindNew::class.java)?.let {
    ...You can check the annotated field type if needed 
    val binding = BindingNewData(elementName, it.viewId)
    viewBindingDataList.add(binding)
}
```

- Step 4: Support parsing this bind annotation in KSP Adapter Parser.
```
if (it.isAnnotationPresent(BindNew::class)) {
    ...You can check the annotated field type if needed 
    val annotation = it.getAnnotationsByType(BindNew::class).first()
    val binding = BindingNewData(elementName, annotation.viewId)
    viewBindingDataList.add(binding)
}
```

Now you are done, enjoy the new bind annotation.