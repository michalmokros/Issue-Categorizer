import exceptions.MissingTaskArgumentException;
import util.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
import static util.Utility.addArgPrefixSuffix;

/**
 * Main application class for executing the "run" task.
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static final String REPOSITORY_ARGUMENT = "r";
    public static final String STATE_ARGUMENT = "s";
    public static final String TRAIN_LABELS_ARGUMENT = "cl";
    public static final String TEST_LABELS_ARGUMENT = "tl";

    private final static String DEFAULT_ISSUE_STATE = "open";
    private final static String DEFAULT_TRAIN_LABELS = String.join(",",
            "enhancement",
            "bug"
    );
    private final static String DEFAULT_TEST_LABELS = String.join(",",
            "unlabeled"
    );

    public static void main(String[] args) throws Exception {
        LOGGER.log(INFO, "Started run task with arguments: " + Arrays.toString(args));

        String[] csvFileNames = callDownloader(args);
//        String[] arffFileNames = callConverter(csvFileNames);
//        String bestClassifier = callTester(csvFileNames); @TODO output of tester
//        String cstClassifiedFileNames = callModeller(ArrayUtils.addAll(arffFileNames, csvFileNames[1]/*, bestClassifier*/)); //../data/IssueCategorizer-atom-atom-issues-open-test.arff

//        LOGGER.log(INFO, "Finished run task, classified issues saved into file: " + cstClassifiedFileNames);
    }

    private static String[] callDownloader(String[] args) throws Exception {
        String githubDownloadArgs = Utility.extractArg(REPOSITORY_ARGUMENT, args);
        if (githubDownloadArgs == null || githubDownloadArgs.isEmpty()) {
            LOGGER.log(WARNING, "No repository argument for downloading.");
            throw new MissingTaskArgumentException("Mandatory arguments for task missing: " + REPOSITORY_ARGUMENT);
        }

        String issuesState = Utility.extractArg(STATE_ARGUMENT, args);
        if (issuesState == null || issuesState.isEmpty()) {
            LOGGER.log(INFO, "No issues state argument, setting default value as: " + DEFAULT_ISSUE_STATE);
            issuesState = DEFAULT_ISSUE_STATE;
        }

        String trainLabels = Utility.extractArg(TRAIN_LABELS_ARGUMENT, args);
        if (trainLabels == null || trainLabels.isEmpty()) {
            LOGGER.log(INFO, "No labels for issues to create classifier, setting default values as: " + DEFAULT_TRAIN_LABELS);
            trainLabels = DEFAULT_TRAIN_LABELS;
        }

        String testLabels = Utility.extractArg(TEST_LABELS_ARGUMENT, args);
        if (testLabels == null || trainLabels.isEmpty()) {
            LOGGER.log(INFO, "No labels for issues to be classified, setting default values as: " + DEFAULT_TEST_LABELS);
            testLabels = DEFAULT_TEST_LABELS;
        }

        String githubDownloadArg = addArgPrefixSuffix(Download.REPOSITORY_ARGUMENT) + githubDownloadArgs;
        String issuesStateArg = addArgPrefixSuffix(Download.STATE_ARGUMENT) + issuesState;
        String labelsArg = addArgPrefixSuffix(Download.LABELS_ARGUMENT) + trainLabels;
        String[] newArgs = new String[] { githubDownloadArg, issuesStateArg, labelsArg };
        String csvTrainFile = Download.download(newArgs);

        labelsArg = addArgPrefixSuffix(Download.LABELS_ARGUMENT) + testLabels;
        String excludeLabelsArg = addArgPrefixSuffix(Download.EXCLUDE_LABELS_ARGUMENT) + trainLabels;
        newArgs = new String[] { githubDownloadArg, issuesStateArg, labelsArg, excludeLabelsArg };
        String csvTestFile = Download.download(newArgs);

        return new String[] { csvTrainFile, csvTestFile };
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
        if (args.length == 4) {
            String trainFile = "-learn=" + args[0];
            String testFile = "-csf=" + args[1];
            String originalFile = "-og=" + args[2];
            String classifier = "-cf=" + args[3];

            return Model.model(new String[] {trainFile, testFile, originalFile, classifier});
        } else {
            throw new Exception("For a modeller to be initialized there need to be 3 arguments.");
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
