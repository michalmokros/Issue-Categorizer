import util.ModellerUtil;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 * Modeller Main class for handling execution of method for classifying of unlabeled issues based on labeled issues.
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class Modeller {
    private final static Logger LOGGER = Logger.getLogger(Modeller.class.getName());

    public static String model(String trainFile, String testFile, String originalFile, String classifier) throws Exception {
        LOGGER.log(INFO, "Initialized Modeller for train file: " + trainFile +  ", test file: " + testFile + " with classifier: " + classifier);

        return ModellerUtil.processData(trainFile, testFile, originalFile, classifier);
    }
}
