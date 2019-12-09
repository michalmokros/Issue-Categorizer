import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static util.ConverterUtil.preprocessIssues;

/**
 * Converter Main class for handling pre-processing of issues
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class Converter {
    private final static Logger LOGGER = Logger.getLogger(Converter.class.getName());

    public static String convert(String csvFile, boolean useSmartData) throws Exception {
        LOGGER.log(INFO, "Initialized Converter for file: " + csvFile + ", using Smart Data: " + useSmartData);

        String arffFile = preprocessIssues(csvFile, useSmartData);

        return arffFile;
    }
}
