import org.apache.commons.lang3.ArrayUtils;
import util.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

/**
 * Main application class for executing whole application.
 *
 * @author xmokros
 */
public class Main {
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    private final static String DEFAULT_ISSUES_STATUS = "open";
    private final static String DEFAULT_LABELS = String.join(",",
            "enhancement",
            "bug"
    );

    public static void main(String[] args) throws Exception {
        LOGGER.log(INFO, "Started application with arguments: " + Arrays.toString(args));

        String[] csvFileNames = callDownloader(args);
        String[] arffTrainFileName = callConverter(csvFileNames); //../data/IssueCategorizer-atom-atom-issues-open.arff
//        String arffTestFileName = Converter.main(converterArgs);
//        String arffFileName = "../data/IssueCategorizer-atom-atom-issues-open.arff";
//        String arffTestFileName = "../data/IssueCategorizer-atom-atom-issues-open-test.arff";
//        String[] modellerInput = {arffFileName, arffTestFileName}; //../data/IssueCategorizer-atom-atom-issues-open-test.arff
//        Modeller.main(modellerInput);

    }

    private static String[] callDownloader(String[] args) throws Exception {
        String testLabels = Utility.extractArg("c", args);

        if (testLabels == null) {
            return download(ArrayUtils.addAll(args, "-c="));
        }

        return download(args);
    }

    private static String[] callConverter(String[] args) throws Exception {
        List<String> output = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            boolean useSmartData = i == 0;
            output.add(convert(new String[] { "-f=" + args[i], "-sd=" + useSmartData}));
        }

        return output.stream().toArray(String[]::new);
    }

    public static String[] download(String[] args) throws Exception {
        String githubDownloadArgs = Utility.extractArg("r", args);

        if (githubDownloadArgs == null) {
            LOGGER.log(WARNING, "No repository argument for downloading.");
            throw new Exception("No repository argument for downloading."); //@TODO create own exception for missing argument
        }

        String issuesStatus = Utility.extractArg("s", args);
        issuesStatus = issuesStatus == null ? DEFAULT_ISSUES_STATUS : issuesStatus;

        String trainLabels = Utility.extractArg("l", args);
        trainLabels = trainLabels == null ? DEFAULT_LABELS : trainLabels;

        String testLabels = Utility.extractArg("c", args);

        return Downloader.download(githubDownloadArgs, issuesStatus, trainLabels, testLabels);
    }

    public static String convert(String[] args) throws Exception {
        String csvFileName = Utility.extractArg("f", args);

        if (csvFileName == null) {
            LOGGER.log(WARNING, "No file argument for converting.");
            throw new Exception("No file argument for converting.");
        }

        boolean useSmartData = Boolean.parseBoolean(Utility.extractArg("sd", args));

        return Converter.convert(csvFileName, useSmartData);
    }
}
