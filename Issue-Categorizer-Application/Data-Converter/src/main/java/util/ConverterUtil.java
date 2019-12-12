package util;

import entities.DataHolder;
import filtering.Cleaner;
import parsing.Parser;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 * Non instantiable utility class for Converter module with static methods for working with Issues online
 *
 * @author xmokros 456442@mail.muni.cz
 */
public abstract class ConverterUtil {
    private final static Logger LOGGER = Logger.getLogger(ConverterUtil.class.getName());

    private ConverterUtil() {}

    public static String preprocessIssues(String csvFile, boolean useSmartData) throws Exception {
        LOGGER.log(INFO, "Beginning of pre-processing data from csv file --> " + csvFile + " <--" + (useSmartData ? "using Smart Data filter" : "not using Smart Data filter"));

        List<DataHolder> arffList;
        arffList = Parser.parseCsvFile(csvFile);
        Cleaner.cleanArffList(arffList);
        String arffFile = updateFileNameIfNeeded(csvFile.substring(0, csvFile.length() - 3).concat("arff"));
        Parser.createArffFile(arffFile, arffList, useSmartData);

        LOGGER.log(INFO, "Finished pre-processing data from csv file, created arff file --> " + arffFile + " <--");
        return arffFile;
    }

    public static String updateFileNameIfNeeded(String fileName) throws Exception {
        File file = new File(fileName);
        if (!file.exists() || file.isDirectory()) {
            return fileName;
        }

        String nameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf('.'));

        if (nameWithoutSuffix.length() <= 0) {
            throw new Exception("File does not have a name.");
        }

        String output;
        if (Character.isDigit(nameWithoutSuffix.charAt(nameWithoutSuffix.length() - 1))) {
            int num = Character.getNumericValue(nameWithoutSuffix.charAt(nameWithoutSuffix.length() - 1));
            output = nameWithoutSuffix.substring(0, nameWithoutSuffix.length() - 1) + (num + 1) + fileName.substring(fileName.lastIndexOf('.'));
        } else {
            output = nameWithoutSuffix + 1 + fileName.substring(fileName.lastIndexOf('.'));
        }

        return updateFileNameIfNeeded(output);
    }
}
