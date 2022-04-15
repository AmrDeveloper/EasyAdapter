package com.amrdeveloper.easyadapter.compiler.parser

import com.amrdeveloper.easyadapter.adapter.*
import com.amrdeveloper.easyadapter.bind.*
import com.amrdeveloper.easyadapter.compiler.data.adapter.*
import com.amrdeveloper.easyadapter.compiler.data.bind.*
import com.amrdeveloper.easyadapter.compiler.data.listener.*
import com.amrdeveloper.easyadapter.compiler.utils.EasyAdapterLogger
import com.amrdeveloper.easyadapter.option.ListenerType
import javax.lang.model.element.Element
import javax.lang.model.type.DeclaredType
import javax.lang.model.util.Elements

class AdapterParser(private val elementUtils: Elements, private val logger: EasyAdapterLogger) {

    fun parseRecyclerAdapter(element: Element) : RecyclerAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation(RecyclerAdapter::class.java)
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val generateUpdateData = annotation.generateUpdateData
        val adapterClassName = annotation.customClassName.ifEmpty { "${className}RecyclerAdapter" }
        val viewBindingDataList = parseAdapterBindingList(element.enclosedElements)
        val adapterListeners = parseAdapterListeners(element)

        return RecyclerAdapterData (
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            adapterListeners,
            generateUpdateData,
        )
    }

    fun parseListAdapter(element: Element) : ListAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation(ListAdapter::class.java)
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val diffUtilContent = annotation.diffUtilContent
        val adapterClassName = annotation.customClassName.ifEmpty { "${className}ListAdapter" }
        val viewBindingDataList = parseAdapterBindingList(element.enclosedElements)
        val adapterListeners = parseAdapterListeners(element)

        return ListAdapterData (
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            adapterListeners,
            diffUtilContent,
        )
    }

    fun parsePagingDataAdapter(element: Element) : PagingAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation(PagingDataAdapter::class.java)
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val diffUtilContent = annotation.diffUtilContent
        val adapterClassName = annotation.customClassName.ifEmpty { "${className}PagingDataAdapter" }
        val viewBindingDataList = parseAdapterBindingList(element.enclosedElements)
        val adapterListeners = parseAdapterListeners(element)

        return PagingAdapterData (
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            adapterListeners,
            diffUtilContent,
        )
    }

    fun parsePagedListAdapter(element: Element) : PagedListAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation(PagedListAdapter::class.java)
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val diffUtilContent = annotation.diffUtilContent
        val adapterClassName = annotation.customClassName.ifEmpty { "${className}PagedListAdapter" }
        val viewBindingDataList = parseAdapterBindingList(element.enclosedElements)
        val adapterListeners = parseAdapterListeners(element)

        return PagedListAdapterData (
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            adapterListeners,
            diffUtilContent,
        )
    }

    fun parseArrayAdapter(element: Element) : ArrayAdapterData {
        val className = element.simpleName.toString()
        val adapterPackageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation(ArrayAdapter::class.java)
        val appPackageName = annotation.appPackageName
        val layoutId = annotation.layoutId
        val adapterClassName = annotation.customClassName.ifEmpty { "${className}ArrayAdapter" }
        val viewBindingDataList = parseAdapterBindingList(element.enclosedElements)
        val adapterListeners = parseAdapterListeners(element)

        return ArrayAdapterData (
            appPackageName,
            adapterPackageName,
            adapterClassName,
            className,
            layoutId,
            viewBindingDataList,
            adapterListeners,
        )
    }

    fun parseExpandableAdapter(element : Element, expandableMap : Map<String, BindExpandableData>) : ExpandableAdapterData? {
        val className = element.simpleName.toString()
        val adapterPackageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation(ExpandableAdapter::class.java)
        val appPackageName = annotation.appPackageName
        val adapterClassName = annotation.customClassName.ifEmpty { "${className}ExpandableAdapter" }

        var expandableAdapter : ExpandableAdapterData? = null
        val enclosedElements = element.enclosedElements
        for (property in enclosedElements) {
            property.getAnnotation(BindExpandableMap::class.java)?.let {
                val declaredType = property.asType() as DeclaredType
                val arguments = declaredType.typeArguments
                val groupClassName = arguments[0].toString()
                val groupExpandableData = expandableMap[groupClassName]
                if (groupExpandableData == null) {
                    logger.error("Can't find expandable group class with name $groupExpandableData", property)
                    return@let
                }

                val itemClassName = (arguments[1] as DeclaredType).typeArguments[0].toString()
                val itemExpandableData = expandableMap[itemClassName]
                if (itemExpandableData == null) {
                    logger.error("Can't find expandable item class with name $itemExpandableData", property)
                    return@let
                }

                expandableAdapter = ExpandableAdapterData (
                    appPackageName,
                    adapterPackageName,
                    adapterClassName,
                    groupExpandableData,
                    itemExpandableData
                )
            }
        }

        return expandableAdapter
    }

    fun parseExpandableClass(element: Element) : BindExpandableData {
        val className = element.simpleName.toString()
        val classPackageName = elementUtils.getPackageOf(element).toString()
        val annotation = element.getAnnotation(BindExpandable::class.java)
        val layoutId = annotation.layoutId
        val viewBindingDataList = parseAdapterBindingList(element.enclosedElements)
        val adapterListeners = parseAdapterListeners(element)

        return BindExpandableData(
            className,
            classPackageName,
            layoutId,
            viewBindingDataList,
            adapterListeners,
        )
    }

    private fun parseAdapterBindingList(enclosedElements : List<Element>) : List<BindingData> {
        val viewBindingDataList = mutableListOf<BindingData>()
        for (element in enclosedElements) {
            val elementName = element.simpleName.toString()
            val elementType = element.asType().toString()

            element.getAnnotation(BindText::class.java)?.let {
                val binding = BindingTextData(elementName, it.viewId, it.condition)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindImageRes::class.java)?.let {
                if (elementType != "int") {
                    logger.error("@BindImageRes can used only with int data type", element)
                }
                val binding = BindImageResData(elementName, it.viewId, it.condition)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindBackgroundRes::class.java)?.let {
                if (elementType != "int") {
                    logger.error("@BindBackgroundRes can used only with int data type", element)
                }
                val binding = BindBackgroundResData(elementName, it.viewId, it.condition)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindBackgroundColor::class.java)?.let {
                if (elementType != "int") {
                    logger.error("@BindBackgroundColor can used only with int data type", element)
                }
                val binding = BindBackgroundColorData(elementName, it.viewId, it.condition)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindVisibility::class.java)?.let {
                if (elementType != "int") {
                    logger.error("@BindVisibility can used only with int data type", element)
                }
                val binding = BindVisibilityData(elementName, it.viewId, it.condition)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindImage::class.java)?.let {
                if (elementType != "java.lang.String") {
                    logger.error("@BindImage can used only with String data type", element)
                }
                val binding = BindImageData(elementName, it.viewId, it.loader, it.condition)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindGif::class.java)?.let {
                if (elementType != "int") {
                    logger.error("@BindGif can used only with int data type", element)
                }
                val binding = BindGifData(elementName, it.viewId, it.loader, it.condition)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindAlpha::class.java)?.let {
                if (elementType != "float") {
                    logger.error("@BindAlpha can used only with float data type", element)
                }
                val binding = BindAlphaData(elementName, it.viewId, it.condition)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindTextColor::class.java)?.let {
                if (elementType != "int") {
                    logger.error("@BindTextColor can used only with integer data type", element)
                }
                val binding = BindTextColorData(elementName, it.viewId, it.condition)
                viewBindingDataList.add(binding)
            }

            element.getAnnotation(BindLottieRaw::class.java)?.let {
                if (elementType != "int") {
                    logger.error("@BindLottieRaw can used only with integer data type", element)
                }
                val binding = BindLottieRawData(elementName, it.viewId, it.condition)
                viewBindingDataList.add(binding)
            }
        }
        return viewBindingDataList
    }

    private fun parseAdapterListeners(element: Element) : Set<ListenerData> {
        val modelName = element.simpleName.toString()
        val listeners = mutableSetOf<ListenerData>()
        element.getAnnotationsByType(BindListener::class.java).forEach { listener ->
            val listenerData = when (listener.listenerType) {
                ListenerType.OnClick -> ClickListenerData(modelName, listener.viewId)
                ListenerType.OnLongClick -> LongClickListenerData(modelName, listener.viewId)
                ListenerType.OnTouch -> TouchListenerData(modelName, listener.viewId)
                ListenerType.OnHover -> HoverListenerData(modelName, listener.viewId)
                ListenerType.OnCheckedChange -> CheckedListenerData(modelName, listener.viewId)
                ListenerType.OnTextChange -> TextChangedListenerData(modelName, listener.viewId)
            }
            val isUnique = listeners.add(listenerData)
            if (isUnique.not()) {
                logger.warn("You have declared ${listener.listenerType} Listener more than one time in the same class", element)
            }
        }
        return listeners
    }
}