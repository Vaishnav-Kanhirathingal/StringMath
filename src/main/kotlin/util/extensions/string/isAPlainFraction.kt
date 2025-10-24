package org.example.util.extensions.string

fun String.isAPlainFraction(): Boolean =
    this.toMutableList()
        .apply { remove(element = '.') }
        .joinToString(separator = "")
        .isAPlainUnSignedInteger()