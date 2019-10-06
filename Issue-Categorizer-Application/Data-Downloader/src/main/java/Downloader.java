import weka.core.Utils;

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

    public static String main(String[] args) throws Exception {
        String csvFileName = null;

        if (args.length < 3) {
            LOGGER.log(INFO, "Initialized Downloader with only " + args.length + " arguments.");
        } else {
            String[] githubDownloadArgs = Utils.getOption("R", args).split("/");
            String issuesStatus = Utils.getOption("S", args);
            String[] labels = Utils.getOption("L", args).split(",");

            csvFileName = getIssues(githubDownloadArgs[0], githubDownloadArgs[1], issuesStatus, labels);
        }

        return csvFileName;
    }
}


