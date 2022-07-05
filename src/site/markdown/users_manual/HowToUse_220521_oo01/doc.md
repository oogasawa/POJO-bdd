
## Installation

At this time, the software is not registered in the Maven Central Repository, 
so it is installed in the local environment as follows.

```
git clone https://github.com/oogasawa/POJO-bdd
mvn compile package
mvn install
```


## Creating a project to use POJO-bdd

Follow the normal Apache Maven usage procedure to create a project skeleton as follows


```
$ mvn archetype:generate -DgroupId=net.example -DartifactId=your-project -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```


Tip: POJO-bdd uses the [POJO test function](https://maven.apache.org/surefire/maven-surefire-plugin/examples/pojo-test.html) of `maven-surefire-plugin`, 
so the following must be written in `pom.xml`. 
If you have generated a template using `maven-archetype-quickstart`, etc. as described above, 
this description is written in `pom.xml` from the beginning.


```xml
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>2.22.1</version>
        </plugin>
```


Add the POJO-bdd dependency to the created pom.xml.

```xml
    <dependency>
      <groupId>com.github.oogasawa</groupId>
      <artifactId>POJO-bdd</artifactId>
      <version>1.4.0</version>
      <scope>test</scope>
    </dependency>
```




## Creating test codes (1) Hello world example

In the [POJO test](https://maven.apache.org/surefire/maven-surefire-plugin/examples/pojo-test.html) of `maven-surfire-plugin`:

> A test class should be named `**/*Test` and should contain `test*` methods which will each be executed by Surefire.


For example, if you create a single file named `AppTest.java` and create a single method named `testAll` in it, 
`maven-surfire-plugin` will call it during `mvn test`. 
The `testAll` method will then call the test code one after another. 
The `maven-surfire-plugin` will detect if there is an error or not by `assert`.


```
$ tree src/test
src/test
|-- java
|   `-- com
|       `-- example
|           `-- pojobdd
|               `-- StoryTest.java
`-- resources
```


AppTest.java

```java
package com.example.pojobdd;

import com.github.oogasawa.pojobdd.BddUtil;

public class StoryTest {

    public void testAll() {

        assert execAcceptanceCriterion01();
        // assert execAcceptanceCriterion02();
        // ...

    }
    
    public static boolean execAcceptanceCriterion01() {
    
        try (PrintStream out = BddUtil.newPrintStream("Hello_spec.md")) {

            // Checks if all the tests are succeeded.
            List<Boolean> results = new ArrayList<Boolean>();
            results.add(description());
            results.add(example01(out));
            // results.add(example02(out));
            // ...
            
            out.flush();
            return BddUtil.allTrue(results);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    
    public static boolean description(PrintStream out) {
        String description = """
        
        ## Acceptance Criterion
        
        Hello world.
        
        """;
        
        out.println(description);
        return true;
    }


    public static boolean example01(PrintStream out) {
        String description = """
        
        ### Example 1
        
        detail of example 1
        
        """;
        
        out.println(description);
        return true;
    }


    // ...


}
```




## How to run the test


### Outputting Test Results to Standard Output


To run tests using Maven, `mvn test` is executed as usual.


```
mvn test
```

This will cause a report of the results to be displayed on the standard output.


### Outputting test results to files


Since the result reports are generally long, it is convenient to save them in a specified directory.

To do this, specify the directory name in the `pojobdd.basedir` system property.


```
mvn test -Dpojobdd.basedir=src/site/markdown/pojobdd
```


If the report is written to be output in markdown format 
and a series of reports are saved in the directory `src/site/markdown/pojobdd`, 
the reports can be used as part of the maven site by using the `mvn site:site` command.


## Creating Test Codes (2) Practical Examples

Refer to the following URL for an actual example of test code.

https://github.com/oogasawa/POJO-bdd/tree/main/src/test/java/com/github/oogasawa/pojobdd


## Introduction to each method

Specification by Example

- [BddUtil.allTrue](https://github.com/oogasawa/POJO-bdd/blob/main/src/site/markdown/pojobdd/AllTrue_spec.md)
- BddUtil.assertTrue
- [BddUtil.readSnippet](https://github.com/oogasawa/POJO-bdd/blob/main/src/site/markdown/pojobdd/ReadSnippet_spec.md)



