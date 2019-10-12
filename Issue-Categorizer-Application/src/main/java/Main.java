import weka.core.Utils;

import java.util.Arrays;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 * Main application class for executing whole application.
 *
 * @author xmokros
 */
public class Main {
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        LOGGER.log(INFO, "Started application with arguments: " + Arrays.toString(args));
        String csvFileName = Downloader.main(args);
        String[] converterArgs = new String[] {"-F", csvFileName};
        String arffFileName = Converter.main(converterArgs); //../data/IssueCategorizer-atom-atom-issues-open.arff
//        String arffFileName = "../data/IssueCategorizer-atom-atom-issues-open.arff";
//        String arffTestFileName = "../data/IssueCategorizer-atom-atom-issues-open-test.arff";
//        String[] modellerInput = {arffFileName, arffTestFileName}; //../data/IssueCategorizer-atom-atom-issues-open-test.arff
//        Modeller.main(modellerInput);
    }
}
