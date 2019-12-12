import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static util.DownloaderUtil.getIssues;

/**
 * Downloader Main class for handling execution of method for downloading of issues from GitHub's API page.
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class Downloader {
    private final static Logger LOGGER = Logger.getLogger(Downloader.class.getName());

    public static String download(String githubDownloadArgs, String issuesStatus, String labels, String excludedLabels) throws Exception {
        LOGGER.log(INFO, "Initialized Downloader module with github user/repository: " + githubDownloadArgs
                + " status of the issues: " + issuesStatus + " for labels: " + labels + (excludedLabels == null ? "" : " and excluding labels: " + excludedLabels));

        String[] githubDownloadArgsArray = githubDownloadArgs.split("/");
        String[] labelsArray = labels.split(",");
        String[] excludedLabelsArray = (excludedLabels == null ? null : excludedLabels.split(","));

        return getIssues(githubDownloadArgsArray[0], githubDownloadArgsArray[1], issuesStatus, labelsArray, excludedLabelsArray);
    }
}


