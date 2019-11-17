import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.UnstableDefault
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SnippetTest {

    private val exampleCode = "first\nsecond\nthird\nfourth"

    @Test
    fun `SimpleSnippet(code) should just store the code`() = runBlocking {
        val snippet1 = SimpleSnippet(exampleCode)
        val snippet2 = SimpleSnippet("other test code")
        assertEquals(exampleCode, snippet1.render())
        assertEquals("other test code", snippet2.render())
    }

    @Test
    fun `fromLine n should drop first n-1 lines`() = runBlocking {
        val snippet1 = SimpleSnippet(exampleCode) fromLine 2
        val snippet2 = SimpleSnippet(exampleCode) fromLine 4
        assertEquals("second\nthird\nfourth", snippet1.render())
        assertEquals("fourth", snippet2.render())
    }


    @Test
    fun `toLine n should keep first n lines`() = runBlocking {
        val snippet1 = SimpleSnippet(exampleCode) toLine 2
        val snippet2 = SimpleSnippet(exampleCode) toLine 4
        assertEquals("first\nsecond", snippet1.render())
        assertEquals("first\nsecond\nthird\nfourth", snippet2.render())
    }


    @Test
    fun `change (x to y) should replace x to y`() = runBlocking {
        val snippet1 = SimpleSnippet(exampleCode) change ("first" to "one")
        val snippet2 = SimpleSnippet(exampleCode) change ("second" to "line2") change ("fourth" to "4")
        assertEquals("one\nsecond\nthird\nfourth", snippet1.render())
        assertEquals("first\nline2\nthird\n4", snippet2.render())
    }

    @Test
    fun `takeLines IntRange(x, y) should slice code from line x to line y`() = runBlocking {
        val snippet1 = SimpleSnippet(exampleCode) takeLines 1..4
        val snippet2 = SimpleSnippet(exampleCode) takeLines 2..3
        assertEquals("first\nsecond\nthird\nfourth", snippet1.render())
        assertEquals("second\nthird", snippet2.render())
    }

    @UnstableDefault
    @Test
    fun `StackOverflowSnippet should ask StackOverflowConnection object for answer body`() = runBlocking {
        val exampleBody = "there is no <code>code</code> <pre><code>FIRST BLOCK</code></pre>there is no code<pre><code>SECOND BLOCK</code></pre>thereisnocode"
        mockkObject(StackOverflowConnection, recordPrivateCalls = true)
        coEvery { StackOverflowConnection invoke "downloadAnswerBody" withArguments  listOf(40427469) } returns exampleBody

        val snippet1 = StackOverflowSnippet(40427469, 1)
        val snippet2 = StackOverflowSnippet(40427469, 2)
        assertEquals("FIRST BLOCK", snippet1.render())
        assertEquals("SECOND BLOCK", snippet2.render())

        unmockkObject(StackOverflowConnection)
    }
}
