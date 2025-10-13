package org.example.data.basic

import org.example.util.extensions.string.elongate
import org.example.util.extensions.string.getIntOnIndexOrElse
import org.example.util.extensions.string.isAPlainUnSignedInteger
import kotlin.math.max

// TODO: add support for underscore separated numbers
class UnSignedInteger {
    private var _value: String
    val value: String get() = _value

    constructor(value: String) {
        if (value.isAPlainUnSignedInteger()) {
            this._value = value
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
        return UnSignedInteger(value = result.reversed()).apply { this.trim() }
    }

    operator fun minus(other: UnSignedInteger): UnSignedInteger {
        if (this < other) {
            throw IllegalStateException("value of first integer should not be smaller than the second")
        } else {
            val length = max(a = this.value.length, b = other.value.length)
            val first = this.value.elongate(length = length)
            val second = other.value.elongate(length = length)
            var result = ""
            var deficit = 0
            ((length - 1) downTo -1).forEach { index ->
                val a = first.getIntOnIndexOrElse(index = index)
                val b = second.getIntOnIndexOrElse(index = index)
                ((a - deficit) - b).let {
                    if (it < 0) {
                        deficit = 1
                        it + 10
                    } else {
                        deficit = 0
                        it
                    }
                }.let { result += it }
            }
            return UnSignedInteger(value = result.reversed()).apply { this.trim() }
        }
    }

    operator fun times(other: UnSignedInteger): UnSignedInteger {
        fun singleDigitMultiplier(
            number: String,
            multiplier: Char
        ): String {
            var result = ""
            var carry = 0
            val multi = multiplier.digitToIntOrNull() ?: 0

            (number.lastIndex downTo -1).forEach { index ->
                ((number.getIntOnIndexOrElse(index = index) * multi) + carry)
                    .toString()
                    .elongate(length = 2)
                    .let {
                        carry = it.getIntOnIndexOrElse(index = 0)
                        result += it.getIntOnIndexOrElse(index = 1)
                    }

            }
            return result.reversed()
        }

        var result = UnSignedInteger(value = "0")
        other.value
            .reversed()
            .mapIndexed { index, ch ->
                UnSignedInteger(
                    value = singleDigitMultiplier(number = this.value, multiplier = ch) +
                            "".padStart(length = index, padChar = '0')
                )
            }
            .forEach {
                result += it
            }
        return result
    }

    override fun toString(): String {
        return "value = ${this.value}"
    }

    fun trim() {
        this._value = this._value.trimStart { it == '0' }
    }
}