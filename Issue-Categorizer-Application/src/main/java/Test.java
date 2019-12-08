import util.Utility;

import java.util.Arrays;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class Test {
    private final static Logger LOGGER = Logger.getLogger(Test.class.getName());

    public static void main(String[] args) throws Exception {
        LOGGER.log(INFO, "Started Tester task with arguments: " + Arrays.toString(args));

        String summary = test(args);

        LOGGER.log(INFO, "Finished Tester task\n=== SUMMARY === " + summary);
    }

    public static String test(String[] args) throws Exception {
        String testedFile = Utility.extractArg("f", args);
        boolean usingNB = Boolean.parseBoolean(Utility.extractArg("nb", args));
        boolean usingJ48 = Boolean.parseBoolean(Utility.extractArg("j48", args));
        boolean usingRF = Boolean.parseBoolean(Utility.extractArg("rf", args));

        if (testedFile == null) {
            throw new Exception("Missing file argument for diagnosis task.");
        }

        testedFile = Utility.addDataDirectoryToPathIfNot(testedFile);

        if (!Utility.isArffFile(testedFile)) {
            testedFile = Converter.convert(testedFile, false);
        }

        if (!usingNB && !usingJ48 && !usingRF) {
            throw new Exception("Missing at least one classifier argument for diagnosis task.");
        }

        return Tester.test(testedFile, usingNB, usingJ48, usingRF);
    }
}
