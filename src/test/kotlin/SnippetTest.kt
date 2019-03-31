import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SnippetTest {

    private val exampleCode = "first\nsecond\nthird\nfourth"

    @Test
    fun `SimpleSnippet(code) should just store the code`() {
        val snippet1 = SimpleSnippet(exampleCode)
        val snippet2 = SimpleSnippet("other test code")
        runBlocking {
            assertEquals(exampleCode, snippet1.render())
            assertEquals("other test code", snippet2.render())
        }
    }

    @Test
    fun `fromLine n should drop first n-1 lines`() {
        val snippet1 = SimpleSnippet(exampleCode) fromLine 2
        val snippet2 = SimpleSnippet(exampleCode) fromLine 4
        runBlocking {
            assertEquals("second\nthird\nfourth", snippet1.render())
            assertEquals("fourth", snippet2.render())
        }
    }

    @Test
    fun `toLine n should keep first n lines`() {
        val snippet1 = SimpleSnippet(exampleCode) toLine 2
        val snippet2 = SimpleSnippet(exampleCode) toLine 4
        runBlocking {
            assertEquals("first\nsecond", snippet1.render())
            assertEquals("first\nsecond\nthird\nfourth", snippet2.render())
        }
    }

    @Test
    fun `change (x to y) should replace x to y`() {
        val snippet1 = SimpleSnippet(exampleCode) change ("first" to "one")
        val snippet2 = SimpleSnippet(exampleCode) change ("second" to "line2") change ("fourth" to "4")
        runBlocking {
            assertEquals("one\nsecond\nthird\nfourth", snippet1.render())
            assertEquals("first\nline2\nthird\n4", snippet2.render())
        }
    }

    @Test
    fun `takeLines IntRange(x, y) should slice code from line x to line y`() {
        val snippet1 = SimpleSnippet(exampleCode) takeLines 1..4
        val snippet2 = SimpleSnippet(exampleCode) takeLines 2..3
        runBlocking {
            assertEquals("first\nsecond\nthird\nfourth", snippet1.render())
            assertEquals("second\nthird", snippet2.render())
        }
    }
}