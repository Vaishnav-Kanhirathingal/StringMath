package org.example.data.basic

class SignedInteger {
    val isPositive: Boolean
    val value: UnSignedInteger

    constructor(value: String) {
        when {
            value.isEmpty() -> {
                this.value = UnSignedInteger(value = "0")
                this.isPositive = true
            }

            /** number with labelling */
            value.first() in listOf('-', '+') -> {
                this.value = UnSignedInteger(value = value.drop(n = 1))
                this.isPositive = when (val first = value.first()) {
                    '-' -> false
                    '+' -> true
                    else -> throw IllegalArgumentException("initial character ($first) is illegal")
                }
            }

            /** normal number without labelling */
            else -> {
                this.value = UnSignedInteger(value = value)
                this.isPositive = true
            }
        }
    }

    constructor(
        value: String,
        isPositive: Boolean,
    ) : this(
        value = UnSignedInteger(value = value),
        isPositive = isPositive
    )


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
        private fun normalAdditionOfSignedIntegers(
            first: SignedInteger,
            second: SignedInteger,
        ): SignedInteger =
            if (first.isPositive == second.isPositive) {
                SignedInteger(
                    value = (first.value + second.value),
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
        return normalAdditionOfSignedIntegers(first = this, second = -other)
    }

    operator fun times(other: SignedInteger): SignedInteger =
        SignedInteger(value = this.value * other.value, isPositive = this.isPositive == other.isPositive)

    /** reverses the polarity of number */
    operator fun unaryMinus(): SignedInteger = SignedInteger(value = this.value, isPositive = !this.isPositive)

}