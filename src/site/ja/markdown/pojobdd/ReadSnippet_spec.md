
## BddUtil.readSnipeet

Read snippets from within test codes.

`BddUtil.readSnippet(javaFilePathStr, methodName)`
 method locates the java source file
using a relative path under the current directory
and extracts a snippet from the file.

This method extracts a snippets surrounded by `%begin snippet : your_tag ... %end snippet your_tag`




### Example

- Given a source code path string and a code block indicator `%begin snippet : your_tag ... %end snippet your_tag`
- When you call `BddUtil.readSnippet`
- Then the code block (snippet) is returned as a string.


Code:


```
// src/test/java/com/github/oogasawa/pojobdd/ReadSnippetSpec.java
// %begin snippet : readSnippetSpec

String result = BddUtil.readSnippet("src/test/java/com/github/oogasawa/pojobdd/ReadSnippetSpec.java", "readSnippetSpec");

// %end snippet : readSnippetSpec


```

Result:

In this example, the result is the same as the code.



````
// src/test/java/com/github/oogasawa/pojobdd/ReadSnippetSpec.java
// %begin snippet : readSnippetSpec

String result = BddUtil.readSnippet("src/test/java/com/github/oogasawa/pojobdd/ReadSnippetSpec.java", "readSnippetSpec");

// %end snippet : readSnippetSpec


````
