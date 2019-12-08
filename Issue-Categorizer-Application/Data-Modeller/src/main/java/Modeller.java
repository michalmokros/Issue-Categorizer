import util.ModellerUtil;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 * Class for creating a model from arff file and classifying unlabeled arff file.
 *
 * @author xmokros
 */
public class Modeller {
    private final static Logger LOGGER = Logger.getLogger(Modeller.class.getName());

    public static String model(String trainFile, String testFile, String originalFile) throws Exception {
        LOGGER.log(INFO, "Initialized Modeller for train file: " + trainFile +  ", test file: " + testFile + ", original file: " + originalFile);

        return ModellerUtil.processData(trainFile, testFile, originalFile);
    }
}
