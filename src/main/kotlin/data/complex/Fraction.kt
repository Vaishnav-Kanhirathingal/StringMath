package org.example.data.complex

import org.example.data.basic.SignedInteger
import org.example.data.basic.UnSignedInteger
import org.example.util.extensions.character.isPlusOrMinus
import org.example.util.extensions.string.isAPlainFraction

// TODO: Bottom cannot be zero
class Fraction {
    private val top: UnSignedInteger
    private val bottom: UnSignedInteger
    private val isPositive: Boolean

    companion object {
        private fun String.getDecimalDenominator(): UnSignedInteger {
            if (this.isAPlainFraction()) {
                val decimalIndex = this.indexOf(char = '.').takeUnless { it == -1 }
                return UnSignedInteger(
                    value =
                        if (decimalIndex == null) "1"
                        else "1".padEnd(length = this.length - decimalIndex, padChar = '0')
                )
            } else {
                throw IllegalAccessException("only to be called if the string is a plain fraction")
            }
        }

        private fun sameSignAddition(
            a: Fraction,
            b: Fraction
        ): Fraction {
            if (a.isPositive == b.isPositive) {
                return Fraction(
                    top = (a.top * b.bottom) + (b.top * a.bottom),
                    bottom = a.bottom * b.bottom,
                    isPositive = a.isPositive
                )
            } else {
                throw IllegalArgumentException("both parameters should have same polarity")
            }
        }

        private fun normalSubstraction(
            a: Fraction,
            b: Fraction
        ): Fraction {
            if (a.isPositive == b.isPositive) {
                val topX = (a.top * b.bottom)
                val topY = (a.bottom * b.top)
                val retainsPolarity = topX > topY
                val newTop = if (retainsPolarity) {
                    topX - topY
                } else {
                    topY - topX
                }

                return Fraction(
                    top = newTop,
                    bottom = a.bottom * b.bottom,
                    isPositive = if (retainsPolarity) {
                        a.isPositive
                    } else {
                        !a.isPositive
                    }
                )
            } else {
                throw IllegalArgumentException("both parameters should have same polarity")
            }

        }
    }

    constructor(
        top: UnSignedInteger,
        bottom: UnSignedInteger = UnSignedInteger(value = "1"),
        isPositive: Boolean
    ) {
        if (bottom.value == "0") {
            throw IllegalStateException("denominator cannot be zero")
        } else {
            this.top = top
            this.bottom = bottom
            this.isPositive = isPositive
        }
    }

    constructor(
        top: SignedInteger,
        bottom: SignedInteger = SignedInteger(value = "1"),
    ) : this(
        top = top.value,
        bottom = bottom.value,
        isPositive = (top.isPositive == bottom.isPositive)
    )

    constructor(value: String) {
        fun String.removeDecimal() =
            this.toMutableList().apply { this.remove(element = '.') }.joinToString(separator = "")

        when {
            value.isEmpty() -> {
                this.top = UnSignedInteger(value = "0")
                this.bottom = UnSignedInteger(value = "1")
                this.isPositive = true
            }

            value.isAPlainFraction() -> {
                this.top = UnSignedInteger(value = value.removeDecimal())
                this.bottom = value.getDecimalDenominator()
                this.isPositive = true
            }

            (value.first().isPlusOrMinus()) && value.drop(n = 1).isAPlainFraction() -> {
                val fraction = value.drop(n = 1)
                this.top = UnSignedInteger(value = fraction.removeDecimal())
                this.bottom = fraction.getDecimalDenominator()
                this.isPositive = (value.first() != '-')
            }

            value.contains("/") -> throw UnsupportedOperationException("support for \'/\' is pending")
            else -> throw IllegalArgumentException("value = \"$value\" is not an allowed fraction string format")
        }
    }

    operator fun plus(other: Fraction): Fraction =
        if (this.isPositive == other.isPositive) sameSignAddition(a = this, b = other)
        else normalSubstraction(a = this, b = -other)


    operator fun minus(other: Fraction): Fraction =
        if (this.isPositive == other.isPositive) normalSubstraction(a = this, b = other)
        else sameSignAddition(a = this, b = -other)

    operator fun unaryMinus(): Fraction =
        Fraction(
            top = this.top,
            bottom = this.bottom,
            isPositive = !this.isPositive
        )

    operator fun times(other: Fraction): Fraction =
        Fraction(
            top = this.top * other.top,
            bottom = this.bottom * other.bottom,
            isPositive = (this.isPositive == other.isPositive)
        )


    operator fun div(other: Fraction): Fraction =
        Fraction(
            top = this.top * other.bottom,
            bottom = this.bottom * other.top,
            isPositive = (this.isPositive == other.isPositive)
        )

    override fun toString(): String {
        return "${if (this.isPositive) "+" else "-"}${this.top.value}/${this.bottom.value}"
    }

    // TODO: divide, multiply
}