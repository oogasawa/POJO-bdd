package com.github.oogasawa.pojobdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;

import com.github.oogasawa.pojobdd.util.ArrayUtil;

public class DiffSpec {


    public static boolean exec() {
        // PrintStream out = new PrintStream(System.out);
        try (PrintStream out = BddUtil.newPrintStream("Diff_spec.md")) {
            // Checks if all the tests are succeeded.
            List<Boolean> results = new ArrayList<Boolean>();
            results.add(diffDesc(out));
            results.add(diffSpec01(out));
            results.add(diffSpec02(out));
            results.add(diffSpec03(out));

            results.add(diffNullStringSpec01(out));

            out.flush();
            return BddUtil.allTrue(results);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean diffDesc(PrintStream out) {

        // Description
        String description = """

            ## BddUtil.diff(String s1, String s2)

            A super simple string difference detector.

            This method returns a string representing the difference between two strings.

            """;

        out.println(description);

        return true;
    }


    public static boolean diffSpec01(PrintStream out) {

        String description = """

            ### Scenario 01 : no difference

            If there is no difference between two strings, a zero-length string is returned.

            Code:

            ```
            {{snippet}}
            ```

            Result:

            A zero-length string is returend.

            """;


        // Reality
        // %begin snippet : diffSpec01
        String data1 = "abc";
        String data2 = "abc";
        String result = BddUtil.diff(data1, data2);
        // %end snippet : diffSpec01


        String snippet = BddUtil.readSnippet("src/test/java/com/github/oogasawa/pojobdd/DiffSpec.java", "diffSpec01");
        description = description.replace("{{snippet}}", snippet);
        out.println(description);

        // Expectations
        String expectation = "";

        // Check the answer.
        boolean passed = BddUtil.assertTrue(out, expectation, result);
        assert passed;
        return passed;


        //return true;
    }



    public static boolean diffSpec02(PrintStream out) {

        String description = """

            ### Scenario 02 : different strings consisting of a single line.

            - If the strings to be compared are given as single line strings,
            - and if there is a difference between the two strings,
            - then this methid returns the difference detected in the strings.

            Code:

            ```
            {{snippet}}
            ```

            Result:

            This methid returns following string that represents the difference detected in the strings.

            """;


        // Reality
        // %begin snippet : diffSpec02
        String str1 = "abc";
        String str2 = "123";
        String result = BddUtil.diff(str1, str2);
        // %end snippet : diffSpec02

        //System.out.println(answer);

        String snippet = BddUtil.readSnippet("src/test/java/com/github/oogasawa/pojobdd/DiffSpec.java", "diffSpec02");
        description = description.replace("{{snippet}}", snippet);
        out.println(description);

        // Expectations
        String expectation = """
            0: abc
            %% -----
            0: 123
            """;


        // Check the answer.
        boolean isPassed = BddUtil.assertTrue(out, expectation, result);
        assert isPassed;
        return isPassed;


        //return true;
    }


    public static boolean diffSpec03(PrintStream out) {

        Logger.getGlobal().info("enter the method.");

        String description = """

            ### Scenario 03 : different strings consisting of multiple lines.

            - If each string consists of multiple lines,
            - and if there is a difference between the two strings,
            - then this methid returns the difference detected in the strings.

            Code:

            ```
            {{snippet}}
            ```

            Result:

            This methid returns following string that represents the difference detected in the strings.

            """;


        // Reality
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

        //System.out.println(answer);

        String snippet = BddUtil.readSnippet("src/test/java/com/github/oogasawa/pojobdd/DiffSpec.java", "diffSpec03");
        description = description.replace("{{snippet}}", snippet);
        out.println(description);

        // Expectations
        String[] expectations = {
          "2: def",
          "3: ",
          "4: ",
          "5: [END]",
          "6: [END]",
          "7: [END]",
          "8: [END]",
          "%% -----",
          "2: ",
          "3: def",
          "4: 123",
          "5: ",
          "6: ",
          "7: ",
          "8: ",
        };

        String expectation = ArrayUtil.join(expectations, "\n") + "\n";

        // Check the answer.
        boolean isPassed = BddUtil.assertTrue(out, expectation, result);
        assert isPassed;
        return isPassed;


        //return true;
    }



    public static boolean diffNullStringSpec01(PrintStream out) {


        String description = """

            ### Scenario 04 : Handling of null strings

            null is treated as an empty string.

            Code:

            ```
            {{snippet}}
            ```

            Result:

            This methid returns following string that represents the difference detected in the strings.

            """;


        // Reality
        // %begin snippet : diffNullStringSpec01
        StringJoiner result = new StringJoiner("\n\n");
        result.add(BddUtil.diff(null, "Hello"));
        result.add(BddUtil.diff("What's up", null));
        result.add(BddUtil.diff(null, null));
        
        // %end snippet : diffNullStringSpec01


        String snippet = BddUtil.readSnippet(
                            "src/test/java/com/github/oogasawa/pojobdd/DiffSpec.java",
                            "diffNullStringSpec01");
        description = description.replace("{{snippet}}", snippet);
        out.println(description);

        // Expectations
        String[] expectations = {
          "0: ",
          "%% -----",
          "0: Hello",
          "",
          "",
          "0: What's up",
          "%% -----",
          "0: ",
          "",
          "",
        };

        String expectation = ArrayUtil.join(expectations, "\n") + "\n";

        // Check the answer.
        boolean isPassed = BddUtil.assertTrue(out, expectation, result.toString());
        assert isPassed;
        return isPassed;


        //return true;
    }



}
