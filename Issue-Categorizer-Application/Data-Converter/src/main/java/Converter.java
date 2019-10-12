import weka.core.Utils;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static util.ConverterUtil.preprocessIssues;

/**
 * Converter Main class for handling pre-processing of issues
 *
 * @author xmokros
 */
public class Converter {
    private final static Logger LOGGER = Logger.getLogger(Converter.class.getName());

    public static String main(String[] args) throws Exception {
        String arffFile = null;
        if (args.length < 1) {
            LOGGER.log(INFO, "Initialized Converter with only " + args.length + " arguments.");
        } else {
            String csvFile = Utils.getOption("F", args);

            arffFile = preprocessIssues(csvFile);
        }

        return arffFile;
    }
}
