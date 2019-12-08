import exceptions.MissingTaskArgumentException;
import util.Utility;

import java.util.Arrays;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

/**
 * Download application class for executing the "download" task.
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class Download {
    private final static Logger LOGGER = Logger.getLogger(Download.class.getName());

    public final static String REPOSITORY_ARGUMENT = "r";
    public final static String STATE_ARGUMENT = "s";
    public final static String LABELS_ARGUMENT = "l";
    public final static String EXCLUDE_LABELS_ARGUMENT = "e";

    private final static String DEFAULT_ISSUES_STATUS = "all";
    private final static String DEFAULT_LABELS = String.join(",",
            "all"
    );

    public static void main(String[] args) throws Exception {
        LOGGER.log(INFO, "Started download task with arguments: " + Arrays.toString(args));

        String csvFileName = download(args);

        LOGGER.log(INFO, "Finished download task, created file: " + csvFileName);
    }

    public static String download(String[] args) throws Exception {
        String githubDownloadArgs = Utility.extractArg(REPOSITORY_ARGUMENT, args);
        if (githubDownloadArgs == null || githubDownloadArgs.isEmpty()) {
            LOGGER.log(WARNING, "No repository argument for downloading.");
            throw new MissingTaskArgumentException("Mandatory arguments for task missing: " + REPOSITORY_ARGUMENT);
        }

        String issuesStatus = Utility.extractArg(STATE_ARGUMENT, args);
        if (issuesStatus == null || issuesStatus.isEmpty()) {
            LOGGER.log(INFO, "No issues state argument, setting default value as: " + DEFAULT_ISSUES_STATUS);
            issuesStatus = DEFAULT_ISSUES_STATUS;
        }

        String labels = Utility.extractArg(LABELS_ARGUMENT, args);
        if (labels == null || labels.isEmpty()) {
            LOGGER.log(INFO, "No labels for issues to create classifier, setting default values as: " + DEFAULT_LABELS);
            labels = DEFAULT_LABELS;
        }

        String excludedLabels = Utility.extractArg(EXCLUDE_LABELS_ARGUMENT, args);

        return Downloader.download(githubDownloadArgs, issuesStatus, labels, excludedLabels);
    }
}
