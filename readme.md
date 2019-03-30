So Lang? So what?
---
<img src=Logo.png width=200 height=200/>

### SoLang is the programming language of the future.
 - It's built around Stack Overflow Driven Development process and brings it to the next level
 - You don't ever need to write code which has been already written - just reference it!
 - Your code is always up to date - when someone update their answer on Stack Overflow, your code is automatically updated as well!
 - The code is usually more concise. Want to set-up a server with simple one-liner? That's the language for you!
 - You can write code which iterates over all solutions - find yourself the best one without copying and pasting code on and on again
 - Love writing viruses? Well, now you can write one which no antivirus software will detect. Nobody expect downloading malicious code from Stact Overflow.
 - You can fully control compilation process with all features of kotlin programming language
 - Parallel code fetching and compilation (so that it doesn't take ages)
 - Answer caching - if you fetch different fragments of code from the same answer - don't worry, the data will be cached. Stack Exchange API will be called only once.
 - It's great for DRM - your code can't be run without internet access

### Example code
##### FizzBuzz
```kotlin
fun main() = createCode {
    +StackOverflowSnippet(40427469, 7)
}.buildWith("python2", "script.py")
```
##### FizzBuzz but up to 50 and completely skips not matching numbers
```kotlin
fun main() = createCode {
    +StackOverflowSnippet(40427469, 7) toLine 7 change ("101" to "51")
}.buildWith("python2", "script.py")
```


### Installation

`build.gradle`
```groovy
repositories {
    maven{ url  "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.krzysztofsroga.solang:0.1'
}
```

`build.gradle.kts`
```kotlin 
repositories {
    maven(url = "https://jitpack.io") {
        name = "jitpack"
    }
}

dependencies {
    implementation(group = "com.github.krzysztofsroga", name = "solang", version = "0.1")
}
```





### Important notes
 - Without Stack Exchange API key you can download only 300 snippets every day. Snippets are downloaded every time you run your code. To compile more code get your key on on [Stack Apps](http://stackapps.com/apps/oauth/register)
 - To fulfill DRY principle, making copies of snippets is a little bit harder - every time you execute a method like `toLine`, `change`, the original snippet is modified. If you need the code in more than one place, please write a function.
 - Please, always use safe mode and verify the code if you fetch 'newest' answer version. It's because stack overflow answers can be changed. You don't want to see your computer executing `rm -rf ~/`.

### Release ready TODO
 - [ ] Upload to [Jitpack](https://jitpack.io/)
 - [x] Memoize downloaded snippets [MnemoniK](https://github.com/aballano/MnemoniK)
 - [x] Cleanup dependencies
 - [x] Add option to start code block numbering from 1
 - [ ] Real time code output and interaction (for some reason `inheritIO` does not work)
 - [x] Design Logo
 - [ ] Write tests
 - [ ] Safe mode which shows generated code before execution (well, answer can be edited... it isn't quite safe)
 - [ ] Allow usage of specific answer version
 - [ ] Api key usage
 - [ ] Fix `toLine` after `fromLine` usage
### Read
 - [Is there a limit of api requests?](https://stackapps.com/questions/3055/is-there-a-limit-of-api-requests)

### Future plans
 - Allow self-referencing SoLang code on Stack Overflow. Sharing recursive snippet could be funny.
 - Write plugins for most popular IDEs and editors - it should show you hints how do your snippets look like.
 - Support Microsoft Windows target.
 - Downloading code from other Stack Exchange pages.
 - Downloading code from GitHub.
 - Add support for plugins (other code sources).
 - Create add-ons for chrome and firefox.

##### Disclaimer: This is NOT official language created by Stack Overflow. 
