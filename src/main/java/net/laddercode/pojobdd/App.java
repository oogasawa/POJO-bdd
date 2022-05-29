package net.laddercode.pojobdd;

/**
 * The main class of the POJO-BDD.
 *
 * <p>
 * POJO-BDD framework is intended to be used as a library.
 * </p>
 * This class is the main class, but the
 */
public class App
{
    /** Converts given file contents into array of string literals of lines.
     *
     */
    public static void main( String[] args )
    {

        Options options = createCliOptions();

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("l")) {
                System.out.println(cmd.getOptionValue("l"));
            }
            if (cmd.hasOption("logfile")) {
                System.out.println(cmd.getOptionValue("logfile"));
            }
            if (cmd.hasOption("h") || cmd.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("Util-java", options);
            }

            // cmd.getArgList().stream().forEach(item->{
            //         System.out.print(item + " ");
            // });
            // System.out.println();

        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Util-java", options);
        }
    }

    public static Options createCliOptions() {
        Options options = new Options();

        options.addOption(Option.builder()
                          .option("h")
                          .longOpt("help")
                          .hasArg(false)
                          // .argName("file")
                          .desc("print help message")
                          .required(false)
                          .build());

        options.addOption(Option.builder()
                          .option("l")
                          .longOpt("logfile")
                          .hasArg(true)
                          .argName("file")
                          .desc("use given file for log")
                          .required(false)
                          .build());

        return options;
    }

}
