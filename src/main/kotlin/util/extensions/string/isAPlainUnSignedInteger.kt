package org.example.util.extensions.string

private const val NUMBERS = "1234567890"

fun String.isAPlainUnSignedInteger(): Boolean = this.all { it in NUMBERS }