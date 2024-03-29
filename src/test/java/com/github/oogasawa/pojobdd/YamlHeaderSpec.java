package com.github.oogasawa.pojobdd;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class YamlHeaderSpec {

    public static boolean exec() {

        String docId = BddUtil.documentId("YamlHeaderSpec");
        Path mdPath = Path.of(docId, docId + ".md");
        
        try (PrintStream out = BddUtil.newPrintStream(mdPath)) {
            // Checks if all the tests are succeeded.
            List<Boolean> results = new ArrayList<Boolean>();

            out.println(BddUtil.yamlHeader(docId, "yamlHeader"));
            results.add(storyDesc(out));
            results.add(basicExample01(out));

            out.flush();
            return BddUtil.allTrue(results);

        } catch (IOException ex) {
            ex.printStackTrace();
        } 

        return false;
    }


    public static boolean storyDesc(PrintStream out) {

        // Description
        String description = """

            ## yamlHeader(String docId, String title)

            Returns a YAML Header like the following:

            ```
            ---
            id: docId
            title: "your title string"
            ---
            ```

            """;

        out.println(description);

        return true;
    }


    public static boolean basicExample01(PrintStream out) {

        String description = """

            ### Example 01 : YAML header generation with docID and title.


            Code:

            ```
            {{snippet}}
            ```

            Result:

            """;


        // Reality
        // %begin snippet : basicExample01
        String docId = BddUtil.documentId("YamlHeaderExample");
        String result = BddUtil.yamlHeader(docId, "YAML header example");
        // %end snippet : basicExample01


        String snippet = BddUtil.readSnippet(
                            Path.of("src/test/java/com/github/oogasawa/pojobdd/YamlHeaderSpec.java"),
                            "basicExample01");
        description = description.replace("{{snippet}}", snippet);
        out.println(description);

        // Expectations
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("---");
        joiner.add("id: " + docId);
        joiner.add("title: " + "YAML header example");
        joiner.add("---\n\n");
        String expectation = joiner.toString();

        // Check the answer.
        boolean passed = BddUtil.assertTrue(out, expectation, result);
        assert passed;
        return passed;

    }
}
