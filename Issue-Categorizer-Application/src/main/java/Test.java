import exceptions.MissingTaskArgumentException;
import util.Utility;

import java.util.Arrays;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

/**
 * Test application class for executing the "diagnose" task.
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class Test {
    private final static Logger LOGGER = Logger.getLogger(Test.class.getName());

    public final static String FILE_ARGUMENT = "f";
    public final static String CLASSIFIER_ARGUMENT = "c";
    public final static String NAIVE_BAYES_ARGUMENT = "nb";
    public final static String J48_ARGUMENT = "j48";
    public final static String RANDOM_FOREST_ARGUMENT = "rf";

    public static void main(String[] args) throws Exception {
        LOGGER.log(INFO, "Started Tester task with arguments: " + Arrays.toString(args));

        String summary = test(args);

        LOGGER.log(INFO, "Finished Tester task\n=== SUMMARY === " + summary);
    }

    /**
     * main function for extracting the arguments and calling the Tester module
     * @param args user arguments
     * @return name of the classifier with best results
     * @throws Exception
     */
    public static String test(String[] args) throws Exception {
        String testedFile = Utility.extractArg(FILE_ARGUMENT, args);
        if (testedFile == null || testedFile.isEmpty()) {
            LOGGER.log(WARNING, "No file argument for testing.");
            throw new MissingTaskArgumentException("Mandatory arguments for task missing: " + FILE_ARGUMENT);
        }

        String classifier = Utility.extractArg(CLASSIFIER_ARGUMENT, args);
        if (classifier == null || classifier.isEmpty() ||
                !(classifier.equals(NAIVE_BAYES_ARGUMENT) || classifier.equals(J48_ARGUMENT) || classifier.equals(RANDOM_FOREST_ARGUMENT))) {
            LOGGER.log(WARNING, "Wrong classifier argument for testing.");
            throw new MissingTaskArgumentException("Mandatory arguments for task missing: " + CLASSIFIER_ARGUMENT);
        }

        testedFile = Utility.addDataDirectoryToPathIfNot(testedFile);

        if (!Utility.isArffFile(testedFile)) {
            testedFile = Converter.convert(testedFile, false);
        }

        return Tester.test(testedFile, classifier);
    }
}
