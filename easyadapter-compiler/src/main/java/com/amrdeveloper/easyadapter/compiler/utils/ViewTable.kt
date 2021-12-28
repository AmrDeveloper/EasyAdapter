package com.amrdeveloper.easyadapter.compiler.utils

class ViewTable {

    private val table = mutableMapOf<String, String>()

    fun define(viewId: String): String {
        val variableName = viewId.toCamelCase(false)
        table[viewId] = variableName
        return variableName
    }

    fun resolve(viewId: String): String {
        return table.getOrDefault(viewId, "")
    }

    fun resolveOrDefine(viewId: String): String {
        val resolvedName = resolve(viewId)
        return resolvedName.ifEmpty { define(viewId) }
    }
}