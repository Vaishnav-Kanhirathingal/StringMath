import org.example.data.basic.UnSignedInteger
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UnsignedIntegerTests {
    @Test
    fun unsignedInitiation() {
        assert(value = UnSignedInteger(value = "").value == "0")
        assert(value = UnSignedInteger(value = "0").value == "0")
        assert(value = UnSignedInteger(value = "1_000").value == "1000")
        assert(value = UnSignedInteger(value = "0003").value == "0003")
        listOf("12.3", "we", "+-2").forEach {
            assertThrows<IllegalArgumentException> { UnSignedInteger(value = it) }.printStackTrace()
        }
    }
}