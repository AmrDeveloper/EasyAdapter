package com.amrdeveloper.easyadapter.compiler.utils

fun String.toCamelCase(startUpper : Boolean = true) : String {
    val builder = StringBuilder()
    val firstChar = if (startUpper) this.first().titlecase() else this.first().lowercase()
    builder.append(firstChar)
    var shouldCapitalizeNext = false
    for (i in 1 until this.length) {
        if (this[i] == '_') {
            shouldCapitalizeNext = true
            continue
        }

        if (shouldCapitalizeNext) {
            builder.append(this[i].titlecase())
            shouldCapitalizeNext = false
            continue
        }

        builder.append(this[i])
    }
    return builder.toString()
}