package integers

import org.example.data.basic.SignedInteger
import org.junit.jupiter.api.assertThrows
import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertEquals

class SignedIntegerTests {
    @Test
    fun initiation() {
        assertEquals(
            expected = SignedInteger(value = "").value.value,
            actual = "0"
        )

        assertEquals(
            expected = SignedInteger(value = "+123")
                .also { assert(value = it.isPositive) }
                .value
                .value,
            actual = "123"
        )
        assertEquals(
            expected = SignedInteger(value = "-123")
                .also { assert(value = !it.isPositive) }
                .value
                .value,
            actual = "123"
        )
        assertThrows<IllegalArgumentException> { SignedInteger(value = "+-1") }
        assertThrows<IllegalArgumentException> { SignedInteger(value = "q", isPositive = true) }
    }

    private enum class Action { ADDITION, SUBSTRACTION, MULTIPLICATION }

    private class TestCalculationData(
        val a: Long,
        val b: Long,
        val action: Action
    ) {
        val res
            get() = when (action) {
                Action.ADDITION -> (a + b)
                Action.SUBSTRACTION -> (a - b)
                Action.MULTIPLICATION -> (a * b)
            }
    }

    @Test
    fun calculations() {
        Action.entries.forEach { action ->
            listOf(
                // ------------------------------------------------------------------------------------------------ addition
                TestCalculationData(a = 123, b = 654, action = action),
                TestCalculationData(a = 123, b = -654, action = action),
                TestCalculationData(a = -123, b = 654, action = action),
                TestCalculationData(a = -123, b = -654, action = action),
                // -------------------------------------------------------------------------------------------- substraction
                // ------------------------------------------------------------------------------------------ multiplication
            ).forEach { testCalculationData ->
                val a = SignedInteger(value = testCalculationData.a.toString())
                val b = SignedInteger(value = testCalculationData.b.toString())
                assertEquals(
                    expected =
                        when (action) {
                            Action.ADDITION -> a + b
                            Action.SUBSTRACTION -> a - b
                            Action.MULTIPLICATION -> a * b
                        }
                            .also { res ->
                                assertEquals(
                                    expected = res.isPositive,
                                    actual =
                                        if (res.value.value == "0") res.isPositive
                                        else (testCalculationData.res > 0)
                                )
                            }
                            .value
                            .value,
                    actual = testCalculationData.res.absoluteValue.toString()
                )
            }
        }
    }
}