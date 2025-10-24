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

    enum class FractionActions { ADD, SUBTRACT } // TODO: MULTIPLY, DIVIDE } // TODO: SIMPLIFY, FRACTIONALIZE

    private class FractionTestHelper(
        val a: String,
        val b: String,
        val act: FractionActions,
        val res: String
    )

    @Test
    fun calculations() {
        listOf(
            FractionTestHelper(
                a = "3",
                b = "10",
                act = FractionActions.ADD,
                res = "+11/1"
            ),
            FractionTestHelper(
                a = "3",
                b = "-10",
                act = FractionActions.ADD,
                res = "-9/1"
            ),
            FractionTestHelper(
                a = "-3",
                b = "10",
                act = FractionActions.ADD,
                res = "+9/1"
            ),
            FractionTestHelper(
                a = "-3",
                b = "-10",
                act = FractionActions.ADD,
                res = "-11/1"
            ),
        ).forEach { testObj ->
            val a = Fraction(value = testObj.a)
            val b = Fraction(value = testObj.b)
            assertEquals(
                expected = testObj.res,
                actual = when (testObj.act) {
                    FractionActions.ADD -> a + b
                    FractionActions.SUBTRACT -> a - b
                }.toString()
            )
        }
    }
}