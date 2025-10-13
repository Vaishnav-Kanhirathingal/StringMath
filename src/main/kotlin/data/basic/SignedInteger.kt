package org.example.data.basic

import org.example.util.extensions.string.isAPlainUnSignedInteger

class SignedInteger {
    val isPositive: Boolean
    val value: UnSignedInteger

    constructor(value: String) {
        when {
            value.isEmpty() -> {
                this.value = UnSignedInteger(value = "0")
                this.isPositive = true
            }

            /** normal number without labelling */
            value.isAPlainUnSignedInteger() -> {
                this.value = UnSignedInteger(value)
                this.isPositive = true
            }

            /** number with labelling */
            value.first().let { it == '-' || it == '+' } && value.drop(n = 1).isAPlainUnSignedInteger() -> {
                this.value = UnSignedInteger(value = value.drop(n = 1))
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
            this.value = UnSignedInteger(value = value)
            this.isPositive = isPositive
        } else {
            throw IllegalArgumentException("value should be an unsigned integer")
        }
    }

    constructor(
        value: UnSignedInteger,
        isPositive: Boolean,
    ) {
        this.value = value
        this.isPositive = isPositive
    }

    override fun toString(): String {
        return "${if (isPositive) "" else "-"}$value"
    }


    companion object {
        // TODO: check
        fun normalAdditionOfSignedIntegers(
            first: SignedInteger,
            second: SignedInteger,
        ): SignedInteger =
            if (first.isPositive == second.isPositive) {
                SignedInteger(
                    value = (first.value * second.value),
                    isPositive = first.isPositive
                )
            } else {
                val firstIsBig = first.value >= second.value
                SignedInteger(
                    value = if (firstIsBig) {
                        first.value - second.value
                    } else {
                        second.value - first.value
                    },
                    isPositive = if (firstIsBig) first.isPositive else second.isPositive
                )
            }
    }

    operator fun plus(other: SignedInteger): SignedInteger {
        return normalAdditionOfSignedIntegers(first = this, second = other)
    }

    operator fun minus(other: SignedInteger): SignedInteger {
        return normalAdditionOfSignedIntegers(first = this, second = other)
    }
}