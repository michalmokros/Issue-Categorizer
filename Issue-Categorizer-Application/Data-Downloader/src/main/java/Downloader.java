import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static util.DownloaderUtil.getIssues;

/**
 * Downloader Main class for handling execution of method for downloading of issues from github's api page.
 *
 * @author xmokros
 */
public class Downloader {
    private final static Logger LOGGER = Logger.getLogger(Downloader.class.getName());

    public static String[] download(String githubDownloadArgs, String issuesStatus, String trainLabels, String testLabels) throws Exception {
        LOGGER.log(INFO, "Initialized Downloader module with github user/repository: " + githubDownloadArgs
                + " status of the issues: " + issuesStatus + " for training labels: " + trainLabels + " and testing labels: " + testLabels);

        String[] githubDownloadArgsArray = githubDownloadArgs.split("/");
        String[] trainLabelsArray = trainLabels.split(",");

        if (testLabels != null) {
            String[] testLabelsArray = testLabels.split(",");
            return getIssues(githubDownloadArgsArray[0], githubDownloadArgsArray[1], issuesStatus, trainLabelsArray, testLabelsArray);
        }

        return getIssues(githubDownloadArgsArray[0], githubDownloadArgsArray[1], issuesStatus, trainLabelsArray, null);
    }
}


