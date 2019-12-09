import exceptions.MissingTaskArgumentException;
import util.Utility;

import java.util.Arrays;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

/**
 * Convert application class for executing the "convert" task.
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class Convert {
    private final static Logger LOGGER = Logger.getLogger(Convert.class.getName());

    public final static String FILE_ARGUMENT = "f";
    public final static String SMART_DATA_ARGUMENT = "sd";

    public static void main(String[] args) throws Exception {
        LOGGER.log(INFO, "Started Convert task with arguments: " + Arrays.toString(args));

        String arffFileName = convert(args);

        LOGGER.log(INFO, "Finished Convert task, created: " + arffFileName);
    }

    /**
     * main function for extracting the arguments and calling the Converter module
     * @param args user arguments
     * @return name of the file where the converted issues are stored
     * @throws Exception
     */
    public static String convert(String[] args) throws Exception {
        String csvFileName = Utility.extractArg(FILE_ARGUMENT, args);
        if (csvFileName == null || csvFileName.isEmpty()) {
            LOGGER.log(WARNING, "No file argument for converting.");
            throw new MissingTaskArgumentException("Mandatory arguments for task missing: " + FILE_ARGUMENT);
        }

        boolean useSmartData = Boolean.parseBoolean(Utility.extractArg(SMART_DATA_ARGUMENT, args));

        csvFileName = Utility.addDataDirectoryToPathIfNot(csvFileName);

        return Converter.convert(csvFileName, useSmartData);
    }
}
