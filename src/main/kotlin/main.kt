fun main() = createCode {
    +StackOverflowSnippet(40427469, 6)
}.buildWith("python2", "script.py")


//val code = """for count in range(1, 101):
//    if count % 5 == 0 and count % 3 == 0:
//        print "FizzBuzz"
//    elif count % 5 == 0:
//        print "Buzz"
//    elif count % 3 == 0 and count % 5 == 0:
//        print "Fizz"
//    else:
//        print count"""


/* Some more advanced code

val forLoop = surroundingSnippet {
    before = StackOverflowSnippet(1341, 234) toLine 5 change ("u" to "x")
    after = StackOverflowSnippet(1341, 234) fromLine 10
}
val code = createCode {
    +StackOverflowSnippet(40427469, 6) fromLine 3 toLine 10

    forLoop {
        +StackOverflowSnippet(1234, 9) fromLine 30 toLine 35
        +StackOverflowSnippet(234, 3) change ( "count" to "i")
    }

    +SimpleSnippet("helo")
}


 */

