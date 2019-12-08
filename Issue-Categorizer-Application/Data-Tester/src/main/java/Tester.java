import util.TesterUtil;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class Tester {
    private final static Logger LOGGER = Logger.getLogger(Tester.class.getName());

    public static String test(String fileName, boolean usingNB, boolean usingJ48, boolean usingRF) throws Exception {
        LOGGER.log(INFO, "Initialized Tester for file for testing: " + fileName);

        return TesterUtil.testData(fileName, usingNB, usingJ48, usingRF);
    }
}
