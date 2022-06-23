package com.github.oogasawa.pojobdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class BddUtilSpec {


    public static boolean exec() {
        // PrintStream out = new PrintStream(System.out);
        try (PrintStream out = BddUtil.newPrintStream("BddUtil_spec.md")) {
            // Checks if all the tests are succeeded.
            List<Boolean> results = new ArrayList<Boolean>();
            results.add(allTrueSpec01(out));
            results.add(allTrueSpec02(out));
            // results.add(convertRecursivelySpec(out));
            results.add(readSnippetSpec(out));

            out.flush();
            return BddUtil.allTrue(results);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean allTrueSpec01(PrintStream out) {

        // Description
        String[] description = {
            "",
            "## `allTrue(List<Boolean>)`",
            "",
            "- Given a Boolean list",
            "- When the size of the list >= 1",
            "- Then returns true if all the elements of given array are true, otherwise false",
            "",
            };

        Arrays.stream(description)
            .forEach(out::println);

        // Reality
        ArrayList<ArrayList<Boolean>> examples = new ArrayList<>();
        ArrayList<Boolean> row1 = new ArrayList<>();
        row1.add(true);
        examples.add(row1);
        ArrayList<Boolean> row2 = new ArrayList<>();
        row2.add(true);
        row2.add(true);
        examples.add(row2);
        ArrayList<Boolean> row3 = new ArrayList<>();
        row3.add(true);
        row3.add(false);
        examples.add(row3);


        StringJoiner answers = new StringJoiner("\n");
        for (int i=0; i<examples.size(); i++) {
            ArrayList<Boolean> row = examples.get(i);
            answers.add(String.format("%s => %s",
                                      row.toString(),
                                      BddUtil.allTrue(examples.get(i))));
        }
        String answer = answers.toString();


        // Expectations
        String[] expectations = {
            "[true] => true",
            "[true, true] => true",
            "[true, false] => false",
        };
        String expectation = String.join("\n",expectations);



        // Check the answer.
        boolean result = BddUtil.assertTrue(out, expectation, answer);
        assert result;
        return result;


        //return true;
    }

    public static boolean allTrueSpec02(PrintStream out) {

        // Description
        String[] description = {
            "",
            "- Given a Boolean list",
            "- When list is `null` or the size of the list == 0",
            "- Then returns false",
            "",
            };

        Arrays.stream(description)
            .forEach(out::println);

        // Reality
        ArrayList<ArrayList<Boolean>> examples = new ArrayList<>();
        ArrayList<Boolean> row1 = null;
        examples.add(row1);
        ArrayList<Boolean> row2 = new ArrayList<>();
        examples.add(row2);


        StringJoiner answers = new StringJoiner("\n");

        answers.add(String.format("%s => %s", "null", BddUtil.allTrue(examples.get(0))));
        for (int i=1; i<examples.size(); i++) {
            ArrayList<Boolean> row = examples.get(i);
            answers.add(String.format("%s => %s",
                                      row.toString(),
                                      BddUtil.allTrue(examples.get(i))));
        }
        String answer = answers.toString();


        // Expectations
        String[] expectations = {
            "null => false",
            "[] => false"
        };
        String expectation = String.join("\n",expectations);



        // Check the answer.
        boolean result = BddUtil.assertTrue(out, expectation, answer);
        assert result;
        return result;

    }



    public static boolean readSnippetSpec(PrintStream out) {

        // Description
        String description = """

            ## Read snippets from within test codes.

            ### Example

            `BddUtil.readSnippet(javaFilePathStr, methodName)`
             method locates the java source file
            using a relative path under the current directory
            and extracts a snippet from the file.

            The snippets are surrounded by `%begin snippet : ... %end snippet` as follows.

            The string `your_method_name` does not necessarily have to be a method name, but can be any string.

            ```
            // %begin snippet : your_method_name
            {{snippet}}
            // %end snippet : your_method_name
            ```

            The execution result is as follows.

            Leading and trailing blank lines are stripped.

            """;


        // Reality
        // %begin snippet : readSnippetSpec

        String answer = BddUtil.readSnippet("src/test/java/com/github/oogasawa/pojobdd/BddUtilSpec.java", "readSnippetSpec");

        // %end snippet : readSnippetSpec

        description = description.replace("{{snippet}}", answer);
        out.println(description);


        // Expectation
        String expectation = """
            String answer = BddUtil.readSnippet("src/test/java/com/github/oogasawa/pojobdd/BddUtilSpec.java", "readSnippetSpec");
            """;

        // Check the answer.
        boolean result = BddUtil.assertTrue(out, expectation, answer);
        assert result;
        return result;

    }






    // public static boolean convertRecursivelySpec(PrintStream out) {

    //     // Description
    //     String[] description = {
    //         "",
    //         "## convertRecursively",
    //         "",
    //         "- Given a base directory of markdown files",
    //         "- When the directory contains no less than one markdown files",
    //         "- Then convert each markdown file to a HTML file, with replacing its extention .md to .html",
    //         "",
    //         };

    //     Arrays.stream(description)
    //         .forEach(out::println);

    //     // Reality
    //     // recursively list markdown files.
    //     String basedir = System.getProperty("pojobdd.basedir");
    //     if (basedir == null) {
    //         System.out.println("pojobdd.basedir is not specified.");
    //     }
    //     else {
    //         Path basedirPath = Path.of(basedir);
    //         List<Path> htmlPaths = BddUtil.convertRecursively(basedirPath);
    //         System.out.println(htmlPaths);
    //     }
    //     // return result;

    //     return true;
    // }



}
