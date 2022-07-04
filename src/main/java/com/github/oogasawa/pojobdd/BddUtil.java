package com.github.oogasawa.pojobdd;

import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.oogasawa.pojobdd.util.StringUtil;

public class BddUtil {

    public static boolean assertTrue(PrintStream out, String expectation, String result) {

        if (expectation.equals(result)) {
            out.println("````");
            out.println(result);
            out.println("````");

            return true;
        }
        else {
            out.println("````");
            out.println("% result:");
            out.println(result);
            out.println("");

            out.println("% expectation:");
            out.println(expectation);
            out.println("");

            out.println("% diff:");
            out.println(diff(result, expectation));
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



    public static boolean allTrue(Boolean ... data) {

        if (data == null) {
            return false;
        }
        else if (data.length == 0) {
            return false;
        }
        else {
            return Arrays.stream(data)
                .allMatch(elem->{ return elem == true; });
        }

    }





    public static String diff(String data1, String data2) {

        List<String> list1 = StringUtil.splitByNewLine((data1));
        List<String> list2 = StringUtil.splitByNewLine((data2));


        Logger.getGlobal().info(String.format("list1.size() = %d", list1.size()));
        Logger.getGlobal().info(String.format("list2.size() = %d", list2.size()));

        StringJoiner joiner1 = new StringJoiner("\n");
        StringJoiner joiner2 = new StringJoiner("\n");

        boolean presenceOfDifference = false;
        int state = 0;
        int maxLine = Math.max(list1.size(), list2.size());
        for (int i=0; i<maxLine; i++) {

            String list1Line = i < list1.size() ? list1.get(i) : "[END]";
            String list2Line = i < list2.size() ? list2.get(i) : "[END]";

            int newState = list1Line.equals(list2Line) ? 0 : 1;

            if (state == 0 && newState == 0) { // no difference.
                continue;
            }
            else if (state == 0 && newState == 1) { // beginning of difference area.
                state = 1;
                presenceOfDifference = true;
                joiner1.add(String.format("%d: %s", i, list1Line));
                joiner2.add(String.format("%d: %s", i, list2Line));

            }
            else if (state == 1 && newState == 0) { // End of difference area.
                state = 0;

                // add a blank line to the results.
                joiner1.add("");
                joiner2.add("");
            }
            else if (state == 1 && newState == 1) {
                joiner1.add(String.format("%d: %s", i, list1Line));
                joiner2.add(String.format("%d: %s", i, list2Line));
            }

        }

        String result = "";
        if (presenceOfDifference) {
            result = joiner1.toString() + "\n%% -----\n" + joiner2.toString() + "\n";
        }
        return result;
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

        // create a file path to the corresponding java file.
        String sourceDir = System.getProperty("pojobdd.sourcedir");
        if (sourceDir == null) {
            sourceDir = System.getenv("PWD");
        }
        Path sourceDirPath = Path.of(sourceDir);
        Path javaFilePath = sourceDirPath.resolve(filePath);

        // Joining with empty characters 
        // since readAllLines preserve NewLine character of each line.
        StringJoiner joiner = new StringJoiner(""); 
        joiner.add("// " + filePath + "\n");
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
                    joiner.add(indent(lines.get(i), -1*indentWidth));
                    continue;
                }
                Matcher m2 = endPattern.matcher(lines.get(i));
                if (m2.find()) {
                    joiner.add(indent(lines.get(i), -1*indentWidth));
                    flg = 0;
                    break;
                }

                if (flg == 1) {
                    joiner.add(indent(lines.get(i), -1*indentWidth));

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return joiner.toString() + "\n";

    }


    public static String indent(String line, int width) {
        Logger.getGlobal().info(String.format("line.length() = %d, width = %d, line = %s", line.length(), width, line));
        if (line.length() > Math.abs(width)) {
            return line.indent(width);
        }
        else {
            return "\n";
        }
    }
    


}
