package fractions

import org.example.data.basic.SignedInteger
import org.example.data.basic.UnSignedInteger
import org.example.data.complex.Fraction
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FractionTests {
    @Test
    fun initiation() {
        assertEquals(
            expected = Fraction(
                top = UnSignedInteger(value = "20"),
                bottom = UnSignedInteger(value = "30"),
                isPositive = false
            ).toString(),
            actual = "-20/30"
        )

        assertEquals(
            expected = Fraction(
                top = SignedInteger(value = "-0123"),
                bottom = SignedInteger(value = "2")
            ).toString(),
            actual = "-123/2"
        )

        assertEquals(
            expected = Fraction(value = "0.002").toString(),
            actual = "+2/1000"
        )
    }

    enum class FractionActions { ADD, SUBTRACT, MULTIPLY, DIVIDE } // TODO: SIMPLIFY, FRACTIONALIZE

    private class FractionTestHelper(
        val a: String,
        val b: String,
        val addRes: String,
        val subRes: String,
        val mulRes: String,
        val divRes: String,
    )

    @Test
    fun calculations() {
        listOf(
            FractionTestHelper(
                a = "3",
                b = "10",
                addRes = "+13/1",
                subRes = "-7/1",
                mulRes = "+30/1",
                divRes = "+3/10"
            ),
            FractionTestHelper(
                a = "3",
                b = "-10",
                addRes = "-7/1",
                subRes = "+13/1",
                mulRes = "-30/1",
                divRes = "-3/10"
            ),
            FractionTestHelper(
                a = "-3",
                b = "10",
                addRes = "+7/1",
                subRes = "-13/1",
                mulRes = "-30/1",
                divRes = "-3/10"
            ),
            FractionTestHelper(
                a = "-3",
                b = "-10",
                addRes = "-13/1",
                subRes = "+7/1",
                mulRes = "+30/1",
                divRes = "+3/10"
            ),
        ).forEach { testObj ->
            val a = Fraction(value = testObj.a)
            val b = Fraction(value = testObj.b)
            FractionActions.entries.forEach {
                assertEquals(
                    expected = when (it) {
                        FractionActions.ADD -> testObj.addRes
                        FractionActions.SUBTRACT -> testObj.subRes
                        FractionActions.MULTIPLY -> testObj.mulRes
                        FractionActions.DIVIDE -> testObj.divRes
                    },
                    actual = when (it) {
                        FractionActions.ADD -> a + b
                        FractionActions.SUBTRACT -> a - b
                        FractionActions.MULTIPLY -> a * b
                        FractionActions.DIVIDE -> a / b
                    }.toString()
                )
            }
        }
    }
}