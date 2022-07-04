
## BddUtil.diff(String s1, String s2)

A super simple string difference detector.

This method returns a string representing the difference between two strings.



### Scenario 01 : no difference

If there is no difference between two strings, a zero-length string is returned.

Code:

```
// src/test/java/com/github/oogasawa/pojobdd/DiffSpec.java
// %begin snippet : diffSpec01
String data1 = "abc";
String data2 = "abc";
String result = BddUtil.diff(data1, data2);
// %end snippet : diffSpec01


```

Result:

A zero-length string is returend.


````

````

### Scenario 02 : different strings consisting of a single line.

- If the strings to be compared are given as single line strings,
- and if there is a difference between the two strings,
- then this methid returns the difference detected in the strings.

Code:

```
// src/test/java/com/github/oogasawa/pojobdd/DiffSpec.java
// %begin snippet : diffSpec02
String str1 = "abc";
String str2 = "123";
String result = BddUtil.diff(str1, str2);
// %end snippet : diffSpec02


```

Result:

This methid returns following string that represents the difference detected in the strings.


````
0: abc
%% -----
0: 123

````

### Scenario 03 : different strings consisting of multiple lines.

- If each string consists of multiple lines,
- and if there is a difference between the two strings,
- then this methid returns the difference detected in the strings.

Code:

```
// src/test/java/com/github/oogasawa/pojobdd/DiffSpec.java
// %begin snippet : diffSpec03
String str1 = """

    abc
    def

    """;

String str2 = """

    abc

    def
    123



    """;

String result = BddUtil.diff(str1, str2);
// %end snippet : diffSpec03


```

Result:

This methid returns following string that represents the difference detected in the strings.


````
2: def
3: 
4: 
5: [END]
6: [END]
7: [END]
8: [END]
%% -----
2: 
3: def
4: 123
5: 
6: 
7: 
8: 

````
