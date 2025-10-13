package org.example.data.basic

import org.example.util.extensions.string.elongate
import org.example.util.extensions.string.getIntOnIndexOrElse
import org.example.util.extensions.string.isAPlainUnSignedInteger
import kotlin.math.max

// TODO: add support for underscore separated numbers
class UnSignedInteger {
    val value: String

    constructor(value: String) {
        if (value.isAPlainUnSignedInteger()) {
            this.value = value
        } else {
            throw IllegalArgumentException("value of $value is not a plain unsigned integer")
        }
    }

    operator fun compareTo(other: UnSignedInteger): Int {
        return when {
            (this.value.length > other.value.length) -> 1
            (this.value.length < other.value.length) -> -1
            else -> {
                val a = this.value.firstOrNull()?.digitToIntOrNull() ?: 0
                val b = other.value.firstOrNull()?.digitToIntOrNull() ?: 0
                when {
                    (a > b) -> 1
                    (a < b) -> -1
                    else -> 0
                }
            }
        }
    }

    operator fun plus(other: UnSignedInteger): UnSignedInteger {
        val length = max(a = this.value.length, b = other.value.length)
        val a = this.value.elongate(length = length)
        val b = other.value.elongate(length = length)
        var result = ""
        var carry = 0
        ((length - 1) downTo -1).forEach { index ->
            val total = (
                    a.getIntOnIndexOrElse(index = index) +
                            b.getIntOnIndexOrElse(index = index) +
                            carry
                    )
                .toString()
                .elongate(length = 2)
            carry = total.getIntOnIndexOrElse(index = 0)
            result += total.getOrNull(index = 1) ?: '0'
        }
        return UnSignedInteger(value = result.reversed().trimStart { it == '0' })
    }

    operator fun minus(other: UnSignedInteger): UnSignedInteger {
        TODO()
    }

    operator fun times(other: UnSignedInteger): UnSignedInteger {
        TODO()
    }

    override fun toString(): String {
        return "value = ${this.value}"
    }
}