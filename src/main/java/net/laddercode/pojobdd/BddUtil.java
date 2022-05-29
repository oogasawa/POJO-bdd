package net.laddercode.pojobdd;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

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


    // /** Converts a markdown string to a HTML string.
    //  *
    //  * @param mdStr Markdown file contents.
    //  * @return Converted HTML strings.
    // */
    // public static String mdToHtml(String mdStr) {
    //      // set options to support various Markdown notations
    //     MutableDataSet options = new MutableDataSet();
    //     options.set(Parser.EXTENSIONS,
    //         Arrays.asList(
    //                       TablesExtension.create() // table notation support.
    //                       ));


    //     Parser parser = Parser.builder(options).build();
    //     HtmlRenderer renderer = HtmlRenderer.builder(options).build();

    //     // convert Markdown to HTML.
    //     Node document = parser.parse(mdStr);
    //     String html = renderer.render(document);
    //     return html;
    // }



    // public static List<Path> convertRecursively(Path basedir) {
    //     List<Path> htmlFiles = null;

    //     Pattern pMd = Pattern.compile("\\.md$");
    //     try {

    //         htmlFiles = Files.walk(basedir)
    //             .filter(Files::isRegularFile) // stream of Path objects.
    //             .filter(p -> {
    //                     return p.getFileName().toString().endsWith(".md");
    //                 })
    //             // map to a stream of Optional<Path>
    //             // Here, the Path is a HTML file path.
    //             .map(mdPath->{
    //                     String mdPathStr = null;
    //                     String htmlPathStr = null;
    //                     String md = null;
    //                     String html = null;

    //                     try {
    //                         mdPathStr = mdPath.toString();
    //                         htmlPathStr = mdPathStr;
    //                         htmlPathStr = pMd.matcher(htmlPathStr).replaceAll(".html");
    //                         md = Files.readString(Path.of(mdPathStr));
    //                         html = mdToHtml(md);

    //                         // System.out.println(mdPathStr);
    //                         // System.out.println(htmlPathStr);
    //                         // System.out.println(md);
    //                         // System.out.println(html);

    //                         Path htmlPath = Path.of(htmlPathStr);
    //                         Files.writeString(htmlPath, html);

    //                         return Optional.of(Path.of(htmlPathStr));

    //                     } catch (IOException ex) {
    //                         System.err.println(mdPathStr);
    //                         System.err.println(htmlPathStr);
    //                         ex.printStackTrace();
    //                         return Optional.empty();
    //                     }
    //                 })
    //             .filter(p->p.isPresent())
    //             .map(o->{ return (Path)o.get();})
    //             .collect(toList());


    //     } catch (IOException ex) {
    //         ex.printStackTrace();

    //     }

    //     return htmlFiles;
    // }

}
