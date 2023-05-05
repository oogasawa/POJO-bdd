package com.github.oogasawa.pojobdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReadSnippetSpec {

    public static boolean exec() {
        
        String docId = BddUtil.documentId("ReadSnippetSpec");
        Path mdPath = Path.of(docId, docId + ".md");

        try (PrintStream out = BddUtil.newPrintStream(mdPath)) {
            // Checks if all the tests are succeeded.
            List<Boolean> results = new ArrayList<Boolean>();

            out.println(BddUtil.yamlHeader(docId, "ReadSnippet"));
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


    public static boolean readSnippetSpec(PrintStream out) {

        // Description
        String description = """

            ## BddUtil.readSnipeet

            Read snippets from within test codes.
            
            `BddUtil.readSnippet(javaFilePathStr, methodName)`
             method locates the java source file
            using a relative path under the current directory
            and extracts a snippet from the file.

            This method extracts a snippets surrounded by `%begin snippet : your_tag ... %end snippet your_tag` 


            """;

        out.println(description);

        return BddUtil.allTrue(
                               readSnippetSpec01(out)
                               //trimLinesSpec(out)
                               );

    }


    public static boolean readSnippetSpec01(PrintStream out) {

        String description = """

            ### Example 

            - Given a source code path string and a code block indicator `%begin snippet : your_tag ... %end snippet your_tag`
            - When you call `BddUtil.readSnippet`
            - Then the code block (snippet) is returned as a string.

            
            Code:
            
            
            ```
            {{snippet}}
            ```

            Result:

            In this example, the result is the same as the code.
                            

            """;


        // Reality
        // %begin snippet : readSnippetSpec

        String result = BddUtil.readSnippet(
                            Path.of("src/test/java/com/github/oogasawa/pojobdd/ReadSnippetSpec.java"),
                            "readSnippetSpec");

        // %end snippet : readSnippetSpec

        description = description.replace("{{snippet}}", result);
        out.println(description);


        // Expectation
        String expectation = """
            // src/test/java/com/github/oogasawa/pojobdd/ReadSnippetSpec.java
            // %begin snippet : readSnippetSpec

            String result = BddUtil.readSnippet(
                                Path.of("src/test/java/com/github/oogasawa/pojobdd/ReadSnippetSpec.java"),
                                "readSnippetSpec");

            // %end snippet : readSnippetSpec

            """;

        // Check the answer.
        boolean passed = BddUtil.assertTrue(out, expectation, result);
        assert passed;
        return passed;

    }



}
