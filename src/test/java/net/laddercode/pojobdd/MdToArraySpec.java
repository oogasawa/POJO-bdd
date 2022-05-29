package net.laddercode.pojobdd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class MdToArraySpec {


    public static boolean exec() {
        //PrintStream out = new PrintStream(System.out);

        try (PrintStream out = BddUtil.newPrintStream("MdToArray_spec.md")) {

            // Checks if all the tests are succeeded.
            List<Boolean> results = new ArrayList<Boolean>();
            results.add(mdToArraySpec(out));

            out.flush();
            return BddUtil.allTrue(results);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;

    }


    public static boolean mdToArraySpec(PrintStream out) {

        // Description
        String[] description = {
            "",
            "### mdToArray(filename)",
            "",
            "- Given a filename of a markdown file",
            "- When the file exists",
            "- Then splits a text file into lines and returns a list of the lines.",
            "",
            };

        Arrays.stream(description)
            .forEach(out::println);

        // Reality
        StringJoiner answers = new StringJoiner("\n");
        try (InputStream in = MdToArraySpec.class.getResourceAsStream("/testdoc.md");
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            MdToArray.mdToArray(reader)
                .stream()
                .forEach(row -> answers.add(row));

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        String answer = answers.toString();


        // Expectations
        String[] expectations = {
            "\"\",",
            "\"# A Test Dcoument for MdToArraySpec.java\",",
            "\"\",",
            "\"\",",
            "\"Behavior-driven development\",",
            "\"\",",
            "\"Behavior-driven development (BDD) is an agile process designed to keep the focus on stakeholder value throughout the whole project; it is a form of TDD.\",",
            "\"Specifications are defined in advance, the implementation is done according to those specifications, and they are run periodically to validate the\",",
            "\"outcome. Besides those similarities, there are a few differences as well. Unlike TDD, which is based on unit tests, BDD encourages us to write multiple\",",
            "\"specifications (called scenarios) before starting the implementation (coding). Even though there is no specific rule, BDD tends to levitate towards\",",
            "\"higher-level functional requirements. While it can be employed at a unit level as well, the real benefits are obtained when taking a higher approach\",",
            "\"that can be written and understood by everyone. The audience is another difference --- BDD tries to empower everyone (coders, testers, managers, end users,\",",
            "\"business representatives, and so on).\",",
            "\"\",",
            "\"\",",
            "\"Test-Driven Java Development - Second Edition\",",
            "\"By Alex Garcia, Viktor Farcic\",",
            "\"PUBLISHED BY:Packt Publishing\",",
            "\"PUBLICATION DATE:March 2018\",",
            "\"\","
        };
        String expectation = String.join("\n", expectations);

        // Check the answer.
        boolean result = BddUtil.assertTrue(out, expectation, answer);
        assert result;
        return result;

    }


}
