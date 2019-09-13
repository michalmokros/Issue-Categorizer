import org.json.simple.parser.ParseException;
import util.DownloaderUtil;

import java.io.IOException;
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

    public static String main(String[] args) throws IOException, ParseException {
        String csvFileName = null;

        if (args.length < 2) {
            LOGGER.log(INFO, "Initialized Downloader with only " + args.length + " arguments.");
        } else {
            String[] githubDownloadArgs = args[0].split("/");
            String issuesStatus = args[1];
            String githubUsername = "IssueCategorizerUsername";
            String githubPassword = "IssueCategorizerPassword";

            if (args.length == 4) {
                githubUsername = args[2];
                githubPassword = args[3];
            }

            csvFileName = getIssues(githubDownloadArgs[0], githubDownloadArgs[1], issuesStatus, githubUsername, githubPassword);
        }

        return csvFileName;
    }
}


