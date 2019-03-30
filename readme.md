Stack Overflow Programming Language
---

SoLang is the programming language of the future. Why so?
 - It's built around Stack Overflow Driven Development process and brings it to the next level
 - You don't ever need to write code which has been already written - just reference it!
 - Your code is always up to date - when someone updates their answer on Stack Overflow, your code is automatically updated as well!
 - The code is usually more concise. Want to set-up a server with simple one-liner? That's the language for you!
 - You can write code which iterates over all solutions - find yourself the best one without copying and pasting code on and on again
 - Love writing viruses? Well, now you can write one which no antivirus software will detect. Nobody expect downloading malicious code from Stact Overflow.
 - You can fully control compilation process with all features of kotlin programming language
 
### Important notes
 - Without Stack Exchange API key you can download only 300 snippets every day. Snippets are downloaded every time you run your code. To compile more code get your key on on [Stack Apps](http://stackapps.com/apps/oauth/register)

### TODO
 - upload to [Jitpack](https://jitpack.io/)
 - memoize downloaded snippets [MnemoniK](https://github.com/aballano/MnemoniK)
 - [Is there a limit of api requests?](https://stackapps.com/questions/3055/is-there-a-limit-of-api-requests)
 - Cleanup dependencies
 - Add option to start code block numbering from 1
 - Real time code output and interaction (for some reason `inheritIO` doesn't work)
 - Design Logo
 - Write tests
 - infix fun parametrize / parametrized with, sth like this
 - safe mode which shows generated code before execution (well, answer can be edited... it isn't quite safe)
 - forLoop returning snippet and not extending CodeBuilder but creating its own. It should be subclass of Snippet.
 
### Future plans
 - answers buffering
 - allow self-referencing SoLang code on Stack Overflow
 - write plugins for most popular IDEs and editors - it should show you hints how do your snippets look like
 - support windows target
 - downloading code from other Stack Exchange pages
 - downloading code from GitHub
 - support for plugins (other code sources)


To fulfill DRY principle, you can't make a copy of a snippet - every time you execute a method on it, the original snippet is modified.
If you wan't to use snippet in more than one place - create a function. The only snippets you can actually reuse are surrounding snippets (those are for fast creation of for example loops, functions)



```kotlin
createCode {
    +StackOverflowSnippet(answerNumber = 1234, codeBlockNumber = 9) fromLine 30 toLine 35
    +StackOverflowSnippet(answerNumber = 4251, codeBlockNumber = 7) toLine 3
}
```
