package com.github.oogasawa.pojobdd;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BddUtil {

    public static boolean assertTrue(PrintStream out, String expectation, String answer) {

        if (expectation.equals(answer)) {
            out.println("````");
            out.println(answer);
            out.println("````");

            return true;
        }
        else {
            out.println("````");
            out.println("expectation:");
            out.println(expectation);
            out.println("");
            out.println("answer:");
            out.println(answer);
            out.println("diff:");
            out.println(diff(expectation, answer));
            out.println("````");

            return false;
        }
    }


    public static boolean assertTrue(PrintStream out, String expectation, String answer, String message) {
        out.println(message);
        return assertTrue(out, expectation, answer);
    }


    public static boolean allTrue(List<Boolean> data) {

        if (data == null) {
            return false;
        }
        else if (data.size() == 0) {
            return false;
        }
        else {
            return data.stream()
                .allMatch(elem->{ return elem == true; });
        }

    }


    public static String diff(String data1, String data2) {
        List<String> list1 = Arrays.asList(data1.split("\n"));
        List<String> list2 = Arrays.asList(data2.split("\n"));

        StringJoiner joiner = new StringJoiner("\n");

        for (int i=0; i<list1.size(); i++) {
            if (i < list2.size()) {
                if (!list1.get(i).equals(list2.get(i))) {
                    joiner.add(String.format("%d: %s", i, list1.get(i)));
                }
            }
        }

        return joiner.toString();
    }



    public static int indentWidth(String headerSpaces, int tabWidth) {

        int width = 0;
        for (int i=0; i < headerSpaces.length(); i++) {
            char ch = headerSpaces.charAt(i);
            if (ch == '\t') {
                width += tabWidth;
            }
            else {
                width++;
            }
        }
        return width;
    }



    /**
     *  This method returns a PrintStream object for writing to the specified file if the pojobdd.basedir property is specified,
     *  or a PrintStream object to standard output if the property is not specified (ignoring the arguments).
     *
     *  <h4>Example:</h4>
     *
     *  <p>
     *   Invoke the program with:
     *
     *   <pre>{@code
     *   mvn package -Dpojobdd.basedir=target/site/pojobdd
     *   }</pre>
     * </p>
     *  <p>
     *  In the program, getPrintStream method is called as follows:
     *
     *  <pre>{@code
     *  getPrintStream("your_category/your_feature.md");
     *  }</pre>
     * </p>
     * <p>
     * Then, {@code target/site/pojobdd/your_category/your_feature.md} is created.
     * </p>
     *
     * @param filePath a relative path string under {@code pojobdd.basedir}.
     * @return A {@code PrintStream} object that represent stdout or a specified file.
     */
    public static PrintStream newPrintStream(String filePath) throws IOException {

        String basedir = System.getProperty("pojobdd.basedir");

        if (basedir == null) {
            return System.out;
        }
        else {
            Path pathObj = Path.of(filePath);
            Path filename = pathObj.getFileName();
            Path parent   = pathObj.getParent();
            Path basePath = Path.of(basedir);

            Path resolvedPath = null;
            if (parent == null) {
                resolvedPath = basePath;
            }
            else {
                resolvedPath = basePath.resolve(parent);
            }

            Files.createDirectories(resolvedPath);
            Path resolvedFilePath = resolvedPath.resolve(filename);
            PrintStream out = new PrintStream(resolvedFilePath.toFile());
            return out;
        }
    }



    /**
     * Returns an example of codes that is surrounded by special comments.
     *
     * With calling {@code readSnippt(filename, yourMethodName)},
     * this method returns following code surronded by {@code %% Reality} and {@code %% Expectation}.
     *
     * <pre>{@code
     * // %% Reality : yourMethodName
     *
     * your code that is returned by this method.
     *
     * // %% Expectation
     * }</pre>
     *
     */
    public static String readSnippet(String filePath, String methodName) {
        StringJoiner joiner = new StringJoiner("\n");

        // create a file path to the corresponding java file.
        String sourceDir = System.getProperty("pojobdd.sourcedir");
        if (sourceDir == null) {
            sourceDir = System.getenv("PWD");
        }
        Path sourceDirPath = Path.of(sourceDir);
        Path javaFilePath = sourceDirPath.resolve(filePath);

        // open the java file.
        try {
            List<String> lines = Files.readAllLines(javaFilePath);
            Pattern startPattern = Pattern.compile("^(\\s+)//\\s+%begin snippet\\s+:\\s+" + methodName);
            Pattern endPattern   = Pattern.compile("^\\s+//\\s+%end snippet\\s+:\\s+" + methodName);

            int flg = 0;
            int indentWidth = 0;
            for (int i=0; i<lines.size(); i++) {

                Matcher m1 = startPattern.matcher(lines.get(i));
                if (m1.find()) {
                    flg = 1;
                    indentWidth = indentWidth(m1.group(1), 4);
                    continue;
                }
                Matcher m2 = endPattern.matcher(lines.get(i));
                if (m2.find()) {
                    flg = 0;
                    continue;
                }

                if (flg == 1) {
                    joiner.add(lines.get(i).indent(-1*indentWidth));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return trimLines(joiner.toString()) + "\n";

    }


    public static String trimLines(String str) {

        StringJoiner joiner = new StringJoiner("\n");
        StringJoiner joiner2 = new StringJoiner("\n");
        String[] lines = str.split("\n");

        Pattern emptyLine = Pattern.compile("^\\s*$");

        int state = 0;
        for (int i=0; i<lines.length; i++) {
            Matcher m = emptyLine.matcher(lines[i]);
            if (state == 0 && m.matches()) {
                continue;
            }
            else if (state == 0 && !m.matches()) {
                state = 1;
                joiner.add(lines[i]);
            }
            else if (state == 1 && !m.matches()) {
                joiner.add(lines[i]);
            }
            else if (state == 1 && m.matches()) {
                state = 2;
                joiner2.add(lines[i]);
            }
            else if (state == 2 && m.matches()) {
                joiner2.add(lines[i]);
            }
            else if (state == 2 && !m.matches()) {
                joiner.merge(joiner2);
                joiner.add(lines[i]);
                joiner2 = new StringJoiner("\n");
            }

        }

        return joiner.toString();

    }



}
