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

    @Test
    fun calculations() {
        assert(value = (UnSignedInteger(value = "200") + UnSignedInteger(value = "453")).value == "653")
        assert(value = (UnSignedInteger(value = "567") - UnSignedInteger(value = "123")).value == "444")
        assertThrows<IllegalStateException> {
            UnSignedInteger(value = "123") - UnSignedInteger(value = "567")
        }
        assert(value = (UnSignedInteger(value = "45") * UnSignedInteger(value = "222")).value == "9990")
    }
}