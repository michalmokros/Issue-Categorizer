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

    public static String main(String[] args) throws Exception {
        if (args.length < 2) {
            LOGGER.log(INFO, "Initialized Modeller with only " + args.length + " arguments.");
        } else {
            String trainFile = args[0];
            String testFile = args[1];
            return ModellerUtil.processData(trainFile, testFile);
        }

        return null;
    }
}
