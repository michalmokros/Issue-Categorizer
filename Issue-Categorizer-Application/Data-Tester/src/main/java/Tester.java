import util.TesterUtil;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 * Tester Main class for handling the testing of classifiers create from issues
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class Tester {
    private final static Logger LOGGER = Logger.getLogger(Tester.class.getName());

    public static String test(String fileName, String classifier) throws Exception {
        LOGGER.log(INFO, "Initialized Tester for file for testing: " + fileName + "with classifier: " + classifier);

        return TesterUtil.testData(fileName, classifier);
    }
}
