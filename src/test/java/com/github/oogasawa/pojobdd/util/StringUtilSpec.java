package com.github.oogasawa.pojobdd.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import com.github.oogasawa.pojobdd.BddUtil;

public class StringUtilSpec {

    public static boolean exec() {

        String docId = BddUtil.documentId("StringUtilSpec");
        Path mdPath = Path.of("util", docId, docId + ".md");

        // PrintStream out = new PrintStream(System.out);
        try (PrintStream out = BddUtil.newPrintStream(mdPath)) {
            // Checks if all the tests are succeeded.
            List<Boolean> results = new ArrayList<Boolean>();

            out.println(BddUtil.yamlHeader(docId, "StringUtil"));
            results.add(splitByNewLineSpec01(out));
            results.add(stringSplitSpec01(out));
            results.add(hereDocSpec(out));
            results.add(arraysAsListSpec01(out));
            results.add(filesReadAllLinesSpec01(out));


            out.flush();
            return BddUtil.allTrue(results);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }




    public static boolean splitByNewLineSpec01(PrintStream out) {

        String description = """

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
            {{snippet}}
            ```

            Result:

            """;


        // Reality
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

        String snippet = BddUtil.readSnippet(
                            Path.of("src/test/java/com/github/oogasawa/pojobdd/util/StringUtilSpec.java"),
                            "splitByNewLineSpec01");

        description = description.replace("{{snippet}}", snippet);
        out.println(description);

        // Expectations
        String[] expectations = {
            "00 : ",
            "01 : ",
            "02 : abc",
            "03 : ",
            "04 : ",
            "05 : def",
            "06 : ",
            "07 : ",
            "08 : ",

        };

        joiner = new StringJoiner("\n");
        for (int i=0; i<expectations.length; i++) {
            joiner.add(expectations[i]);
        }
        String expectation = joiner.toString() + "\n";
        // Check the answer.
        boolean passed = BddUtil.assertTrue(out, expectation, result);
        assert passed;
        return passed;


        //return true;
    }





    public static boolean stringSplitSpec01(PrintStream out) {

        String description = """

            ## String.split (JDK)

            

            ### Scenario : blank lines

            - Given a here document that ends with a sequence of blank lines
            - When a string is split by a newline using `string.split`,
            - then the trailing blank lines are removed.


            Code:

            ```
            {{snippet}}
            ```

            Result:

            """;


        // Reality
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

        String snippet = BddUtil.readSnippet(
                            Path.of("src/test/java/com/github/oogasawa/pojobdd/util/StringUtilSpec.java"),
                            "stringSplitSpec01");

        description = description.replace("{{snippet}}", snippet);
        out.println(description);

        // Expectations
        String[] expectations = {
            "00 : ",
            "01 : ",
            "02 : abc",
            "03 : ",
            "04 : ",
            "05 : def",
        };

        joiner = new StringJoiner("\n");
        for (int i=0; i<expectations.length; i++) {
            joiner.add(expectations[i]);
        }
        String expectation = joiner.toString() + "\n";
        // Check the answer.
        boolean passed = BddUtil.assertTrue(out, expectation, result);
        assert passed;
        return passed;


        //return true;
    }



    

    public static boolean hereDocSpec(PrintStream out) {

        String description = """

            ## Java Text Blocks (here document)

            The text block feature, added in Java 15,
            makes it easy to provide string literals that span multiple lines.

            This feature is called here document in other languages.

            ### Scenario : blank lines

            In a text block, blank lines (new line characters) as well as ordinal characters
            are placed in the string as is (without any conversion).

            Code:

            ```
            {{snippet}}
            ```

            Result:

            """;


        // Reality
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

        String snippet = BddUtil.readSnippet(
                            Path.of("src/test/java/com/github/oogasawa/pojobdd/util/StringUtilSpec.java"),
                            "hereDocSpec");
        description = description.replace("{{snippet}}", snippet);
        out.println(description);

        // Expectations
        String expectation = """
            00 : \\n
            01 : \\n
            02 : a
            03 : b
            04 : c
            05 : \\n
            06 : \\n
            07 : \\n
            08 : d
            09 : e
            10 : f
            11 : \\n
            12 : \\n
            13 : \\n
            """;

        // Check the answer.
        boolean passed = BddUtil.assertTrue(out, expectation, result);
        assert passed;
        return passed;


        //return true;
    }




    public static boolean arraysAsListSpec01(PrintStream out) {

        String description = """

            ## Arrays::asList

            ### Scenario : blank lines

            - Given an array of strings
            - When you process it with the standard libaray `Arrays.asList` method,
            - then the trailing blank lines are preserved.

            Code:

            ```
            {{snippet}}
            ```

            Result:

            """;


        // Reality
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

        String snippet = BddUtil.readSnippet(
                            Path.of("src/test/java/com/github/oogasawa/pojobdd/util/StringUtilSpec.java"),
                            "arraysAsListSpec01");

        description = description.replace("{{snippet}}", snippet);
        out.println(description);

        // Expectations
        String[] expectations = {
            "00 : ",
            "01 : ",
            "02 : abc",
            "03 : ",
            "04 : ",
            "05 : def",
            "06 : ",
            "07 : ",
            "08 : ",

        };

        joiner = new StringJoiner("\n");
        for (int i=0; i<expectations.length; i++) {
            joiner.add(expectations[i]);
        }
        String expectation = joiner.toString() + "\n";
        // Check the answer.
        boolean passed = BddUtil.assertTrue(out, expectation, result);
        assert passed;
        return passed;


        //return true;
    }



    public static boolean filesReadAllLinesSpec01(PrintStream out) {

        String description = """

            ## Files.readAllLines

            ### Scenario : blank lines

            - Given an array of strings
            - When you process it with the standard libaray `Arrays.asList` method,
            - then the trailing blank lines are preserved.

            Code:

            ```
            {{snippet}}
            ```

            Result:

            """;


        // Reality

        String result = null;
        String expectation = null;
        try {
            // %begin snippet : filesReadAllLinesSpec01
            List<String> strList = Files.readAllLines(Path.of("src/test/resources/StringUtil_test.txt"));

            StringJoiner joiner = new StringJoiner("\n");
            for (int i = 0; i < strList.size(); i++) {
                joiner.add(String.format("%02d : %s", i, strList.get(i)));
            }
            result = joiner.toString() + "\n";
            // %end snippet : filesReadAllLinesSpec01

            String snippet = BddUtil.readSnippet(
                                    Path.of("src/test/java/com/github/oogasawa/pojobdd/util/StringUtilSpec.java"),
                                    "filesReadAllLinesSpec01");

            description = description.replace("{{snippet}}", snippet);
            out.println(description);

            // Expectations
            String[] expectations = {
                "00 : ",
                "01 : ",
                "02 : abc",
                "03 : ",
                "04 : ",
                "05 : def",
                "06 : ",
                "07 : ",
                "08 : ",
            };

            joiner = new StringJoiner("\n");
            for (int i = 0; i < expectations.length; i++) {
                joiner.add(expectations[i]);
            }
            expectation = joiner.toString() + "\n";
            // Check the answer.

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        // Check the answer.
        boolean passed = BddUtil.assertTrue(out, expectation, result);
        assert passed;
        return passed;

        //return true;
    }


}
