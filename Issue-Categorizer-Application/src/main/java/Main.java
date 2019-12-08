import org.apache.commons.lang3.ArrayUtils;
import util.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 * Main application class for executing the "run" task.
 *
 * @author xmokros
 */
public class Main {
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        LOGGER.log(INFO, "Started run task with arguments: " + Arrays.toString(args));

        String[] csvFileNames = callDownloader(args);
        String[] arffFileNames = callConverter(csvFileNames);
        callTester(csvFileNames);
        String cstClassifiedFileNames = callModeller(ArrayUtils.addAll(arffFileNames, csvFileNames[1])); //../data/IssueCategorizer-atom-atom-issues-open-test.arff

        LOGGER.log(INFO, "Finished run task\nClassified file: " + cstClassifiedFileNames);
    }

    private static String[] callDownloader(String[] args) throws Exception {
        String testLabels = Utility.extractArg("c", args);

        if (testLabels == null) {
            return Download.download(ArrayUtils.addAll(args, "-c="));
        }

        return Download.download(args);
    }

    private static String[] callConverter(String[] args) throws Exception {
        List<String> output = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            boolean useSmartData = i == 0;
            output.add(Convert.convert(new String[] { "-f=" + args[i], "-sd=" + useSmartData}));
        }

        return output.toArray(new String[0]);
    }

    private static String callModeller(String[] args) throws Exception {
        if (args.length == 3) {
            String trainFile = "-learn=" + args[0];
            String testFile = "-csf=" + args[1];
            String originalFile = "-og=" + args[2];

            return Model.model(new String[] {trainFile, testFile, originalFile});
        } else {
            throw new Exception("For a modeller to be initialized there need to be 2 arguments.");
        }
    }

    private static void callTester(String[] args) throws Exception {
        if (args.length > 0) {
            String testedFile = "-f=" + args[0];

            String withCs = "-nb=true" ;
            Test.test(new String[] {testedFile, withCs});
            withCs = "-j48=true";
            Test.test(new String[] {testedFile, withCs});
            withCs = "-rf=true";
            Test.test(new String[] {testedFile, withCs});
        } else {
            throw new Exception("Not enough arguments for diagnose to begin.");
        }
    }
}
