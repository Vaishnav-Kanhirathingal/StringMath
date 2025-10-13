package org.example.util.extensions.string

fun String.elongate(length: Int) = this.padStart(length = length, padChar = '0')