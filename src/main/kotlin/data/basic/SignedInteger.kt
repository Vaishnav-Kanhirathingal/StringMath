package org.example.data.basic

import org.example.util.extensions.string.elongate
import org.example.util.extensions.string.getIntOnIndexOrElse
import org.example.util.extensions.string.isAPlainUnSignedInteger
import kotlin.math.max

class SignedInteger {


    companion object {
        fun String.removeInitialZeroes() = this.dropWhile { it == '0' }

        private object Unsigned {
            /** uses unsigned integers for addition
             * @throws IllegalArgumentException if both of them don't have matching polarity
             */
            fun addition(
                a: SignedInteger,
                b: SignedInteger
            ): SignedInteger {
                if (a.isPositive == b.isPositive) {
                    val totalLength = max(a = a.value.length, b = b.value.length)
                    val first = a.value.elongate(length = totalLength)
                    val second = b.value.elongate(length = totalLength)

                    var result = ""
                    var carry = 0
                    ((totalLength - 1) downTo -1).forEach { index -> // -1 accommodates a carry
                        (
                                first.getIntOnIndexOrElse(index = index) +
                                        second.getIntOnIndexOrElse(index = index) +
                                        carry
                                )
                            .toString()
                            .reversed()
                            .let {
                                result += it.getOrNull(index = 0)
                                carry = it.getOrNull(index = 1)?.digitToIntOrNull() ?: 0
                            }

                    }
                    return SignedInteger(
                        value = result.reversed().removeInitialZeroes(),
                        isPositive = a.isPositive
                    )
                } else {
                    throw IllegalArgumentException("both numbers [$a] [$b] should be positive")
                }
            }

            fun substraction(
                a: SignedInteger,
                b: SignedInteger
            ): SignedInteger {
                if (a.isPositive && b.isPositive) {
                    val firstIsGreater = if (a.value.length == b.value.length) {
                        (a.value.first().digitToIntOrNull() ?: 0) > (b.value.first().digitToIntOrNull() ?: 0)
                    } else {
                        a.value.length > b.value.length
                    }

                    val maxLength = max(a = a.value.length, b = b.value.length)
                    val first = (if (firstIsGreater) a else b).value.elongate(length = maxLength)
                    val second = (if (firstIsGreater) b else a).value.elongate(length = maxLength)
                    TODO()
                } else {
                    throw IllegalArgumentException("both numbers [$a] [$b] should be positive")
                }
            }
        }
    }

    val isPositive: Boolean
    val value: String

    constructor(value: String) {
        val numbers = "1234567890"

        when {
            value.isEmpty() -> {
                this.value = "0"
                this.isPositive = true
            }

            /** normal number without labelling */
            value.isAPlainUnSignedInteger() -> {
                this.value = value
                this.isPositive = true

            }

            /** number with labelling */
            value.first().let { it == '-' || it == '+' } && value.drop(n = 1).isAPlainUnSignedInteger() -> {
                this.value = value.drop(n = 1)
                this.isPositive = when (val first = value.first()) {
                    '-' -> false
                    '+' -> true
                    else -> throw IllegalArgumentException("initial character ($first) is illegal")
                }
            }

            else -> throw IllegalArgumentException("string $value is illegal")
        }
    }

    constructor(
        value: String,
        isPositive: Boolean,
    ) {
        if (value.isAPlainUnSignedInteger()) {
            this.value = value
            this.isPositive = isPositive
        } else {
            throw IllegalArgumentException("value should be an unsigned integer")
        }
    }

    override fun toString(): String {
        return "${if (isPositive) "" else "-"}$value"
    }

}