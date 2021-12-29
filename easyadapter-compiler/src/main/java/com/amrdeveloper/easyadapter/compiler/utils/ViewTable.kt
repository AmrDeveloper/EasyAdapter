package com.amrdeveloper.easyadapter.compiler.utils

class ViewTable(vararg extraNames: String) {

    private val table = mutableMapOf<String, String>()
    private val reservedNames = mutableSetOf("item", "itemView").plus(extraNames)

    fun define(viewId: String): String {
        val camelCaseViewId = viewId.toCamelCase(false)
        var variableName = camelCaseViewId
        var counter = 1
        while (variableName in reservedNames || variableName in table.values) {
            variableName = "${camelCaseViewId}${counter}"
            counter += 1
        }
        table[viewId] = variableName
        return variableName
    }

    fun resolve(viewId: String): String {
        return table.getOrDefault(viewId, "")
    }
}