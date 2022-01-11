# Support new Adapter

This page will provide a clear steps on how you can add support for new adapter annotation

- Step 1: Create new Annotation for this adapter in easyadapter module.
```
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class NewAdapter (
    val appPackageName : String,
    val layoutId : String,
    val customClassName : String = "",
)
```

- Step 2: In easyadapter-compiler module create new class that extend AdapterData class.
```
data class NewAdapterData (

    ...override abstract field and method.
    ...Add any extra fields for your adapter.
    
) : AdapterData()
```

- Step 3: Create parser method for this adapter.
```
fun parseNewAdapter(element: Element) : NewAdapterData {
    ...Take the needed information from element
    ...Create and return instance of NewAdapterData with the informations.
}
```

- Step 4: Create parser method for the KSP Adapter Parser
```
@OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
fun parseNewAdapter(classDeclaration: KSClassDeclaration): NewAdapterData {
    ...Take the needed information from element
    ...Create and return instance of NewAdapterData with the informations.
}
```

- Step 5: Create new code generator for your adapter that extend AdapterGenerator class.
```
class NewAdapterGenerator(
    private val adapterData: NewAdapter
) : AdapterGenerator() {

    ...override abstract method
    ...Check Generator Helper to see if you need any helper extension from it
    ...add helper methods to help you generate code easily

}
```

- Step 6: Use the parser and generator to create new adapter file, in the default processor.
```
override fun process(annotations: MutableSet<out TypeElement>, environment: RoundEnvironment): Boolean {
    ...
    environment.getElementsAnnotatedWith(NewAdapter::class.java).forEach {
        val adapter = adapterParser.parseNewAdapter(it)
        generateAdapterSourceFile(adapter, NewAdapterGenerator(adapter))
    }
}
```

- Step 7: Use the parser and generator to create new adapter file, in the KSP Processor.
```
@OptIn(KspExperimental::class, KotlinPoetKspPreview::class)
override fun process(resolver: Resolver): List<KSAnnotated> {
    ...
    resolver.getSymbolsWithAnnotation(NewAdapter::class.java.name).forEach {
        if (it is KSClassDeclaration) {
            val adapterData = adapterParser.parseNewAdapter(it)
            val newAdapter = NewAdapterGenerator(adapter).generate()
            generateAdapterSourceFile(adapter, newAdapter, dependencies)
        }
    }
}
```

Now you are done, enjoy the new adapter annotation.