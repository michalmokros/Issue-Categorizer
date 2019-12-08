import util.Utility;

import java.util.Arrays;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

/**
 *
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class Download {
    private final static Logger LOGGER = Logger.getLogger(Download.class.getName());

    private final static String DEFAULT_ISSUES_STATUS = "open";
    private final static String DEFAULT_LABELS = String.join(",",
            "enhancement",
            "bug"
    );

    public static void main(String[] args) throws Exception {
        LOGGER.log(INFO, "Started Download task with arguments: " + Arrays.toString(args));

        String[] csvFileNames = download(args);

        LOGGER.log(INFO, "Finished Download task with arguments: " + Arrays.toString(csvFileNames));
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
}
