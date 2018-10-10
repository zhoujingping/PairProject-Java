package team.onebeltoneroad.wordCount.dataprocess.tool;

import org.apache.commons.cli.*;
import team.onebeltoneroad.wordCount.dataprocess.entity.WordCounterInfo;

public class CommandParser {
    public static void parseCommadLine(String[] args, WordCounterInfo wordCounterInfo) {
        Options options = new Options();
        options.addOption("i", "input", true, "input file path.");
        options.addOption("o", "output", true, "result file path.");
        options.addOption("w", "weight", true, "set weight factor.");
        options.addOption("m", "length", true, "word group length.");
        options.addOption("n", "number", true, "word frequency output number.");
        options.addOption("h", "help", false, "print options' information");

        CommandLineParser parser = new PosixParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            if (commandLine.hasOption("h")) {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp("Options", options);
            } else {
                wordCounterInfo.setInputFile(commandLine.getOptionValue("i"));
                wordCounterInfo.setOutputFile(commandLine.getOptionValue("o"));
                wordCounterInfo.setWeightFactor(Integer.parseInt(commandLine.getOptionValue("w")));
                if (commandLine.hasOption("m")) {
                    wordCounterInfo.setWordGroupLength(Integer.parseInt(commandLine.getOptionValue("m")));
                }
                if (commandLine.hasOption("n")) {
                    wordCounterInfo.setWordFrequencyOutNum(Integer.parseInt(commandLine.getOptionValue("n")));
                }
            }
        } catch (ParseException e) {
            System.out.println("Arguments format wrong.");
            e.printStackTrace();
        }
    }
}
