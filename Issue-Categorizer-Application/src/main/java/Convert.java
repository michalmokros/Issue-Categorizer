import util.Utility;

import java.util.Arrays;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

public class Convert {
    private final static Logger LOGGER = Logger.getLogger(Convert.class.getName());

    public static void main(String[] args) throws Exception {
        LOGGER.log(INFO, "Started Convert task with arguments: " + Arrays.toString(args));

        String arffFileName = convert(args);

        LOGGER.log(INFO, "Finished Convert task, created: " + arffFileName);
    }

    public static String convert(String[] args) throws Exception {
        String csvFileName = Utility.extractArg("f", args);

        if (csvFileName == null) {
            LOGGER.log(WARNING, "No file argument for converting.");
            throw new Exception("No file argument for converting.");
        }

        boolean useSmartData = Boolean.parseBoolean(Utility.extractArg("sd", args));

        csvFileName = Utility.addDataDirectoryToPathIfNot(csvFileName);

        return Converter.convert(csvFileName, useSmartData);
    }
}
