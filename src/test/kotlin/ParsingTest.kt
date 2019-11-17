import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class ParsingTest {

    @Test
    fun `Unparsed post should find code between pre-code tags`() {
        val examplePost = """
<p>Since bogosort can take a very, very <code>long time to run as the array size </code>increases, you might be better off using <strong>iteration</strong> rather than <strong>recursion</strong>. This is because larger arrays will easily exceed the recursion depth.</p>

<p>Here, I have edited your code to use a loop. <pre>I also made some</pre> of your code more concise.</p>

<pre><code>def checkSorted(x):
    # A more concise way is to just return the comparison,
    # which will evaluate to either True or False.
    return x == sorted(x)
</code></pre>
<pre><code>def bogosort(x):
    # Instead of using recursion, you could use iteration.
    # List x will continue to be shuffled until it is sorted.
    while not checkSorted(x):
        random.shuffle(x)
    # Once x is sorted, return it.
    return x
</code></pre>

<p>I also used Python's built-in <code>random</code> module. Though if you need to, you can freely use <code>numpy</code> instead.</p>
"""
        val firstCodeBlock = """def checkSorted(x):
    # A more concise way is to just return the comparison,
    # which will evaluate to either True or False.
    return x == sorted(x)
"""
        val secondCodeBlock = """def bogosort(x):
    # Instead of using recursion, you could use iteration.
    # List x will continue to be shuffled until it is sorted.
    while not checkSorted(x):
        random.shuffle(x)
    # Once x is sorted, return it.
    return x
"""

        val parsedCodeBlocks = UnparsedPost(examplePost).getCodeBlocks()
        assertEquals(parsedCodeBlocks.size, 2)
        assertEquals(parsedCodeBlocks[0], firstCodeBlock)
        assertEquals(parsedCodeBlocks[1], secondCodeBlock)
    }
}