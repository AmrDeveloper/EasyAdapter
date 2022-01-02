package com.amrdeveloper.easyadapter.compiler.utils

import com.amrdeveloper.easyadapter.compiler.data.Variable

class ViewTable(vararg extraNames: String) {

    private val table = mutableMapOf<String, List<Variable>>()
    private val reservedNames = mutableSetOf("item", "itemView").plus(extraNames) as MutableSet<String>

    fun define(viewId: String, type : String): String {
        val camelCaseViewId = viewId.toCamelCase(false)
        var variableName = camelCaseViewId
        var counter = 1
        while (variableName in reservedNames) {
            variableName = "${camelCaseViewId}${counter}"
            counter += 1
        }
        table[viewId] = listOf(Variable(variableName, type))
        reservedNames.add(variableName)
        return variableName
    }

    fun resolve(viewId: String, type : String): String {
        val variables = table[viewId]
        if (variables.isNullOrEmpty()) return ""
        for (variable in variables) {
            if (variable.type == type) {
                return variable.name
            }
        }
        return ""
    }
}