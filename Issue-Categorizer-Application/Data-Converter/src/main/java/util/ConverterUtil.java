package util;

import entities.DataHolder;
import filtering.Cleaner;
import parsing.Parser;

import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public abstract class ConverterUtil {
    private final static Logger LOGGER = Logger.getLogger(ConverterUtil.class.getName());

    private ConverterUtil() {}

    public static String preprocessIssues(String csvFile, boolean useSmartData) throws Exception {
        LOGGER.log(INFO, "Beginning of pre-processing data from csv file --> " + csvFile + " <--");

        List<DataHolder> arffList;
        arffList = Parser.parseCsvFile(csvFile);
        Cleaner.cleanArffList(arffList);
        String arffFile = csvFile.substring(0, csvFile.length() - 3).concat("arff");
        Parser.createArffFile(arffFile, arffList, useSmartData);

        LOGGER.log(INFO, "Finished pre-processing data from csv file, created arff file --> " + arffFile + " <--");
        return arffFile;
    }
}
