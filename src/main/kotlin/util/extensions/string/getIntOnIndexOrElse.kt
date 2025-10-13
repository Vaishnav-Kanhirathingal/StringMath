package org.example.util.extensions.string

fun String.getIntOnIndexOrElse(index: Int, default: Int = 0) =
    this.getOrNull(index = index)?.digitToIntOrNull() ?: default