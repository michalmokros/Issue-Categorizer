import util.Utility;

import java.util.Arrays;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class Model {
    private final static Logger LOGGER = Logger.getLogger(Model.class.getName());

    public static void main(String[] args) throws Exception {
        LOGGER.log(INFO, "Started Classify task with arguments: " + Arrays.toString(args));

        String classifiedFile = model(args);

        LOGGER.log(INFO, "Finished Classify task with classified File: " + classifiedFile);
    }

    public static String model(String[] args) throws Exception {
        String trainFile = Utility.extractArg("learn", args);
        String testFile = Utility.extractArg("csf", args);
        String originalFile = Utility.extractArg("og", args);
        boolean userSmartData = Boolean.parseBoolean(Utility.extractArg("sd", args));

        if (trainFile == null || testFile == null) {
            throw new Exception("For a modeller to be initialized there need to be 2 arguments.");
        }

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

        return Modeller.model(trainFile, testFile, originalFile);
    }
}
