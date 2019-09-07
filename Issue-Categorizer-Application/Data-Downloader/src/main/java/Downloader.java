import org.json.simple.parser.ParseException;

import java.io.IOException;

import static util.DownloaderUtil.getIssues;

/**
 * Downloader Main class for handling execution of method for downloading of issues from github's api page.
 *
 * @author xmokros
 */
public class Downloader {
    public static void main(String[] args) throws IOException, ParseException {
//        if (args.length > 1) {
//            String username = args[0];
//            String password = args[1];
//            System.out.println(username + " and " + password);
//        }
        getIssues("atom", "atom", "open", "IssueCategorizerUsername", "IssueCategorizerPassword");
    }
}


