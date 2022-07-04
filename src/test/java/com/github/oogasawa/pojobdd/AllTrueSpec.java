package com.github.oogasawa.pojobdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;



public class AllTrueSpec {


    public static boolean exec() {
        // PrintStream out = new PrintStream(System.out);
        try (PrintStream out = BddUtil.newPrintStream("AllTrue_spec.md")) {
            // Checks if all the tests are succeeded.
            List<Boolean> results = new ArrayList<Boolean>();
            results.add(allTrueSpec01(out));
            results.add(allTrueSpec02(out));

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
        String description = """
            
            ## `BddUtil.allTrue(List<Boolean>)`

            ### Scenario 01: a non-empty boolean list
            
            - Given a `Boolean` list
            - When the size of the list >= 1
            - Then returns true if all the elements of given list are true, otherwise false.

            Code:

            ```
            {{snippet}}
            ```

            Result:
                                           
            """;



        // Reality
        // %begin snippet : allTrueSpec01
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
        String result = answers.toString();
        // %end snippet : allTrueSpec01


        String snippet = BddUtil.readSnippet(
                            "src/test/java/com/github/oogasawa/pojobdd/AllTrueSpec.java",
                            "allTrueSpec01");
        description = description.replace("{{snippet}}", snippet);
        out.println(description);

        

        // Expectations
        String[] expectations = {
            "[true] => true",
            "[true, true] => true",
            "[true, false] => false",
        };
        String expectation = String.join("\n",expectations);



        // Check the answer.
        boolean passed = BddUtil.assertTrue(out, expectation, result);
        assert passed;
        return passed;


        //return true;
    }

    
    public static boolean allTrueSpec02(PrintStream out) {

        // Description
        String description = """

            ### Scenario 02: a `null` or an empty list
            
            - Given a Boolean list
            - When list is `null` or the size of the list == 0
            - Then returns false


            Code:

            ```
            {{snippet}}
            ```

            Result:

            
            """;


        // Reality
        // %begin snippet : allTrueSpec02
        ArrayList<ArrayList<Boolean>> examples = new ArrayList<>();
        ArrayList<Boolean> row1 = null;
        examples.add(row1);
        ArrayList<Boolean> row2 = new ArrayList<>();
        examples.add(row2);


        StringJoiner results = new StringJoiner("\n");

        results.add(String.format("%s => %s", "null", BddUtil.allTrue(examples.get(0))));
        for (int i=1; i<examples.size(); i++) {
            ArrayList<Boolean> row = examples.get(i);
            results.add(String.format("%s => %s",
                                      row.toString(),
                                      BddUtil.allTrue(examples.get(i))));
        }
        String result = results.toString();
        // %end snippet : allTrueSpec02



        String snippet = BddUtil.readSnippet(
                            "src/test/java/com/github/oogasawa/pojobdd/AllTrueSpec.java",
                            "allTrueSpec02");
        description = description.replace("{{snippet}}", snippet);
        out.println(description);


        
        // Expectations
        String[] expectations = {
            "null => false",
            "[] => false"
        };
        String expectation = String.join("\n",expectations);



        // Check the answer.
        boolean passed = BddUtil.assertTrue(out, expectation, result);
        assert passed;
        return passed;

    }

}
