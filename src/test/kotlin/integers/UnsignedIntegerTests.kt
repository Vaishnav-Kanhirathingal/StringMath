package integers

import org.example.data.basic.UnSignedInteger
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class UnsignedIntegerTests {
    @Test
    fun unsignedInitiation() {
        assertEquals(
            expected = "0",
            actual = UnSignedInteger(value = "").value
        )
        assertEquals(
            expected = "0",
            actual = UnSignedInteger(value = "0").value
        )
        assertEquals(
            expected = "1000",
            actual = UnSignedInteger(value = "1_000").value
        )
        assertEquals(
            expected = "3",
            actual = UnSignedInteger(value = "0003").value
        )
        listOf("12.3", "we", "+-2").forEach {
            assertThrows<IllegalArgumentException> { UnSignedInteger(value = it) }.printStackTrace()
        }
    }

    @Test
    fun calculations() {
        assertEquals(
            expected = "653",
            actual = (UnSignedInteger(value = "200") + UnSignedInteger(value = "453")).value
        )
        assertEquals(
            expected = "444",
            actual = (UnSignedInteger(value = "567") - UnSignedInteger(value = "123")).value
        )
        assertThrows<IllegalStateException> {
            UnSignedInteger(value = "123") - UnSignedInteger(value = "567")
        }
        assertEquals(
            expected = "9990",
            actual = (UnSignedInteger(value = "45") * UnSignedInteger(value = "222")).value
        )
    }
}