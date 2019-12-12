import exceptions.MissingTaskArgumentException;
import util.Utility;

import java.util.Arrays;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

/**
 * Model application class for executing the "classify" task.
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class Model {
    private final static Logger LOGGER = Logger.getLogger(Model.class.getName());

    public final static String LABELED_FILE_ARGUMENT = "u";
    public final static String UNLABELED_FILE_ARGUMENT = "l";
    public final static String SMART_DATA_ARGUMENT = "sd";
    public final static String CLASSIFIER_ARGUMENT = "c";
    public final static String ORIGINAL_UNLABELED_FILE = "og";

    public static void main(String[] args) throws Exception {
        LOGGER.log(INFO, "Started Classify task with arguments: " + Arrays.toString(args));

        String classifiedFile = model(args);

        LOGGER.log(INFO, "Finished Classify task with classified File: " + classifiedFile);
    }

    /**
     * main function for extracting the arguments and calling the Modeller module
     * @param args user arguments
     * @return name of the file where the classified issues are stored
     * @throws Exception
     */
    public static String model(String[] args) throws Exception {
        String trainFile = Utility.extractArg(LABELED_FILE_ARGUMENT, args);
        if (trainFile == null || trainFile.isEmpty()) {
            LOGGER.log(WARNING, "No train file argument for classifying.");
            throw new MissingTaskArgumentException("Mandatory arguments for task missing: " + LABELED_FILE_ARGUMENT);
        }

        String testFile = Utility.extractArg(UNLABELED_FILE_ARGUMENT, args);
        if (testFile == null || testFile.isEmpty()) {
            LOGGER.log(WARNING, "No test file argument for classifying.");
            throw new MissingTaskArgumentException("Mandatory arguments for task missing: " + UNLABELED_FILE_ARGUMENT);
        }

        String classifier = Utility.extractArg(CLASSIFIER_ARGUMENT, args);
        if (classifier == null || classifier.isEmpty()) {
            LOGGER.log(WARNING, "No classifier argument for classifying.");
            throw new MissingTaskArgumentException("Mandatory arguments for task missing: " + CLASSIFIER_ARGUMENT);
        }

        String originalFile = Utility.extractArg(ORIGINAL_UNLABELED_FILE, args);
        boolean userSmartData = Boolean.parseBoolean(Utility.extractArg(SMART_DATA_ARGUMENT, args));

        trainFile = Utility.addDataDirectoryToPathIfNot(trainFile);
        testFile = Utility.addDataDirectoryToPathIfNot(testFile);

        if (!Utility.isArffFile(trainFile)) {
            trainFile = Converter.convert(trainFile, userSmartData);
        }

        if (!Utility.isArffFile(testFile)) {
            if (originalFile == null) {
                originalFile = testFile;
            }

            testFile = Converter.convert(testFile, false);
        }

        originalFile = Utility.addDataDirectoryToPathIfNot(originalFile);

        return Modeller.model(trainFile, testFile, originalFile, classifier);
    }
}
