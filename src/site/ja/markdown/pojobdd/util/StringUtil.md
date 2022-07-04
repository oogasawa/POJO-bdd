
## StringUtil.splitByNewline

`splitByNewLine` splits a string by newline characters.
It is an improved version of `String.split` method in the standard library.
In the standard library, a blank line at the end of a string is deleted,
but in `StringUtil.splitByNewLine`, this is retained.


### Scenario : blank lines

- Given a here document that ends with a sequence of blank lines
- When you process it with `StringUtil::splitByNewLine`,
- then the trailing blank lines are retained.

Code:

```
// src/test/java/com/github/oogasawa/pojobdd/util/StringUtilSpec.java
// %begin snippet : splitByNewLineSpec01
String str = """


    abc


    def


    """;

StringJoiner joiner = new StringJoiner("\n");

ArrayList<String> lines = StringUtil.splitByNewLine(str);
for (int i=0; i<lines.size(); i++) {
    joiner.add(String.format("%02d : %s", i, lines.get(i)));
}
String result = joiner.toString() + "\n";
// %end snippet : splitByNewLineSpec01


```

Result:


````
00 : 
01 : 
02 : abc
03 : 
04 : 
05 : def
06 : 
07 : 
08 : 

````

## String.split (JDK)



### Scenario : blank lines

- Given a here document that ends with a sequence of blank lines
- When a string is split by a newline using `string.split`,
- then the trailing blank lines are removed.


Code:

```
// src/test/java/com/github/oogasawa/pojobdd/util/StringUtilSpec.java
// %begin snippet : stringSplitSpec01
String str = """


    abc


    def


    """;

StringJoiner joiner = new StringJoiner("\n");

String[] lines = str.split("\n");
for (int i=0; i<lines.length; i++) {
    joiner.add(String.format("%02d : %s", i, lines[i]));
}
String result = joiner.toString() + "\n";
// %end snippet : stringSplitSpec01


```

Result:


````
00 : 
01 : 
02 : abc
03 : 
04 : 
05 : def

````

## Java Text Blocks (here document)

The text block feature, added in Java 15,
makes it easy to provide string literals that span multiple lines.

This feature is called here document in other languages.

### Scenario : blank lines

In a text block, blank lines (new line characters) as well as ordinal characters
are placed in the string as is (without any conversion).

Code:

```
// src/test/java/com/github/oogasawa/pojobdd/util/StringUtilSpec.java
// %begin snippet : hereDocSpec
String str = """


    abc


    def


    """;

StringJoiner joiner = new StringJoiner("\n");
for (int i=0; i<str.length(); i++) {
    char ch = str.charAt(i);
    if (ch == '\n') {
        joiner.add(String.format("%02d : %s", i, "\\n"));
    }
    else {
        joiner.add(String.format("%02d : %s", i, ch));
    }

}
String result = joiner.toString() + "\n";
// %end snippet : hereDocSpec


```

Result:


````
00 : \n
01 : \n
02 : a
03 : b
04 : c
05 : \n
06 : \n
07 : \n
08 : d
09 : e
10 : f
11 : \n
12 : \n
13 : \n

````

## Arrays::asList

### Scenario : blank lines

- Given an array of strings
- When you process it with the standard libaray `Arrays.asList` method,
- then the trailing blank lines are preserved.

Code:

```
// src/test/java/com/github/oogasawa/pojobdd/util/StringUtilSpec.java
// %begin snippet : arraysAsListSpec01
String[] strArray = {
    "",
    "",
    "abc",
    "",
    "",
    "def",
    "",
    "",
    "",
};

List<String> strList = Arrays.asList(strArray);

StringJoiner joiner = new StringJoiner("\n");
for (int i=0; i<strList.size(); i++) {
    joiner.add(String.format("%02d : %s", i, strList.get(i)));
}
String result = joiner.toString() + "\n";
// %end snippet : arraysAsListSpec01


```

Result:


````
00 : 
01 : 
02 : abc
03 : 
04 : 
05 : def
06 : 
07 : 
08 : 

````

## Files.readAllLines

### Scenario : blank lines

- Given an array of strings
- When you process it with the standard libaray `Arrays.asList` method,
- then the trailing blank lines are preserved.

Code:

```
// src/test/java/com/github/oogasawa/pojobdd/util/StringUtilSpec.java
// %begin snippet : filesReadAllLinesSpec01
List<String> strList = Files.readAllLines(Path.of("src/test/resources/StringUtil_test.txt"));

StringJoiner joiner = new StringJoiner("\n");
for (int i = 0; i < strList.size(); i++) {
    joiner.add(String.format("%02d : %s", i, strList.get(i)));
}
result = joiner.toString() + "\n";
// %end snippet : filesReadAllLinesSpec01


```

Result:


````
00 : 
01 : 
02 : abc
03 : 
04 : 
05 : def
06 : 
07 : 
08 : 

````
