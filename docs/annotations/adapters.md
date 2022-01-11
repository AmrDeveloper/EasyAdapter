# Adapter Annotations:

### @ArrayAdapter
Used to generate ArrayAdapter

Parameters:

- appPackageName(require): Take the app package name

- layoutId(require): the list item layout name

- customClassName(optional): custom name for the generated file

Example:
```kotlin
@ArrayAdapter("com.amrdeveloper.app", "list_item_model")
data class Model
```

### @RecyclerAdapter
Used to generate RecyclerAdapter

Parameters:

- appPackageName(require): Take the app package name

- layoutId(require): the list item layout name

- customClassName(optional): custom name for the generated file

Example:
```kotlin
@RecyclerAdapter("com.amrdeveloper.app", "list_item_model")
data class Model
```

### @ListAdapter
Used to generate ListAdapter

Parameters:

- appPackageName(require): Take the app package name

- layoutId(require): the list item layout name

- diffUtilContent(require): field name that will used in DiffUtil.ItemCallback

- customClassName(optional): custom name for the generated file

Example:
```kotlin
@ListAdapter("com.amrdeveloper.app", "list_item_model", "name")
data class Model (
   val name : String
)
```

### @PagingDataAdapter
Used to generate PagingDataAdapter (Paging3)

Parameters:

- appPackageName(require): Take the app package name

- layoutId(require): the list item layout name

- diffUtilContent(require): field name that will used in DiffUtil.ItemCallback

- customClassName(optional): custom name for the generated file

Example:
```kotlin
@PagingDataAdapter("com.amrdeveloper.app", "list_item_model", "name")
data class Model (
   val name : String
)
```

### @PagedListAdapter
Used to generate PagedListAdapter (Paging2)

Parameters:

- appPackageName(require): Take the app package name

- layoutId(require): the list item layout name

- diffUtilContent(require): field name that will used in DiffUtil.ItemCallback

- customClassName(optional): custom name for the generated file

Example:
```kotlin
@PagedListAdapter("com.amrdeveloper.app", "list_item_model", "name")
data class Model (
   val name : String
)
```

### @ExpandableAdapter
Used to generate BaseExpandableListAdapter.

Parameters:

- appPackageName(require): Take the app package name

- customClassName(optional): custom name for the generated file

- the model must have a map of Group model and a list of child model annotated with @BindExpandableMap

- the group and child models must annotated with @BindExpandable

Example:
```kotlin
@BindExpandable("list_item_model1")
data class Model1

@BindExpandable("list_item_model2")
data class Model2

@ExpandableAdapter("com.amrdeveloper.app")
data class ExpandableModel (

    @BindExpandableMap
    val data: Map<Model1, List<Model2>>
)
```