package util;

import com.opencsv.CSVWriter;
import dto.EntryDTO;
import org.apache.http.HttpException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

import static java.util.logging.Level.*;

/**
 * Non instantiable utility class for Downloader module with static methods for working with Issues online
 *
 * @author xmokros
 */
public final class DownloaderUtil {
    private final static Logger LOGGER = Logger.getLogger(DownloaderUtil.class.getName());

    private DownloaderUtil() {}

    /**
     * Wrapping method for downloading and writing Issues into file.
     *
     * @param githubDownloadUsername username of github account to download issues from
     * @param githubDownloadRepository repository of github's account to download issues from
     * @param issuesState state of Issues to be downloaded
     * @return name of csv file written into
     * @throws IOException
     * @throws ParseException
     */
    public static String getIssues(String githubDownloadUsername, String githubDownloadRepository, String issuesState, String[] labels)
            throws IOException, ParseException, HttpException {
        LOGGER.log(INFO, "Getting Issues for --> " + githubDownloadUsername + "/" + githubDownloadRepository + " <-- with State --> " + issuesState + " for labels: " + Arrays.toString(labels));

        String urlString = "https://api.github.com/repos/" + githubDownloadUsername + "/" + githubDownloadRepository + "/issues?state=" + issuesState;
        List<EntryDTO> listOfEntries = extractEntriesRecursively(0, urlString, labels);

        String csvFileName = "../data/IssueCategorizer-" + githubDownloadUsername + "-" + githubDownloadRepository + "-issues-" + issuesState + ".csv";
        writeEntriesIntoFile(csvFileName, listOfEntries);

        return csvFileName;
    }

    /**
     * Method for extracting entries from github api URL, used recursively.
     *
     * @param urlString url to be extracted from
     * @return list of recursively extracted entries
     * @throws IOException
     * @throws ParseException
     */
    private static List<EntryDTO> extractEntriesRecursively(long numOfCurrentEntries, String urlString, String[] labels)
            throws IOException, ParseException, HttpException {
        LOGGER.log(INFO, "Extracting Issues for --> " + urlString + " <--");

        URL url = new URL(urlString);
        String login = "IssueCategorizerUsername:IssueCategorizerPassword";
        String encoding = Base64.getEncoder().encodeToString((login).getBytes());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + encoding);
        connection.setRequestProperty("Content-Type", "application/json");
        int status = connection.getResponseCode();

        if (status != 200) {
            LOGGER.log(WARNING, "Connection status is " + status);
            throw new HttpException("Response code of connection: " + status);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        List<EntryDTO> listOfEntries = new ArrayList<>();
        String input;

        while ((input = in.readLine()) != null) {
            listOfEntries = getEntries(numOfCurrentEntries, input, labels);
        }

        if (connection.getHeaderFields().containsKey("Link")) {
            LOGGER.log(FINE, "Getting Link Pager from --> " + urlString + " <--");
            Map<String, String> pagesMap = getPages(connection.getHeaderFields().get("Link").get(0));

            for (Map.Entry<String, String> pageEntry : pagesMap.entrySet()) {
                if (pageEntry.getKey().equals("next")) {
                    listOfEntries.addAll(extractEntriesRecursively(
                            numOfCurrentEntries + listOfEntries.size(), pageEntry.getValue(), labels));
                }
            }
        }

        in.close();
        connection.disconnect();
        return listOfEntries;
    }

    /**
     * Method for getting entries from api page.
     *
     * @param content whole content of github api page
     * @return list of entries extracted from specific page
     * @throws ParseException
     */
    private static List<EntryDTO> getEntries(long numOfPreviousEntries, String content, String[] labels) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(content);
        List<EntryDTO> listOfEntries = new ArrayList<>();
        long id = numOfPreviousEntries;

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            if (jsonObject.containsKey("pull_request")) {
                continue;
            }

            LOGGER.log(FINE, "Getting Label for Issue with Order Number : " + (i + 1) + ". with Title --> " + jsonObject.get("title") + " <--");
            String label = getLabel((JSONArray) jsonObject.get("labels"), labels);

            if (label.isEmpty()) {
                continue;
            }

            String title = jsonObject.get("title").toString();
            String body = jsonObject.get("body").toString();
            EntryDTO entry = new EntryDTO(++id, title, body, label);

            LOGGER.log(FINE, "Adding Entry --> " + entry  + " <--");
            listOfEntries.add(entry);
        }

        return listOfEntries;
    }

    /**
     * Method for differencing and finding specific labels in json labels for issue.
     *
     * @param labels to be searched in
     * @return specific label for that issue's labels
     */
    private static String getLabel(JSONArray labels, String[] labelsToFind) {
        for (int i = 0; i < labels.size(); i++) {
            String label = ((JSONObject) labels.get(i)).get("name").toString();

            for (String labelToFind : labelsToFind) {
                if (labelToFind.equals(label)) {
                    return label;
                }
            }
        }

        return "";
    }

    /**
     * Method for extracting links to api pages from specific api page.
     *
     * @param linkFromHeader link value of api page
     * @return map of keys and links to specific api pages
     */
    private static Map<String, String> getPages(String linkFromHeader) {
        String[] linkMap = linkFromHeader.split(", ");
        Map<String, String> pagesMap = new HashMap<>();

        for (String linkMapEntry : linkMap) {
            String[] linkDouble = linkMapEntry.split("; ");
            String link = linkDouble[0].substring(1, linkDouble[0].length() - 1);
            String key = linkDouble[1].substring(5, linkDouble[1].length() - 1);

            LOGGER.log(FINE, "Adding Link Page : " + key + " with link --> " + " <--");

            pagesMap.put(key, link);
        }

        return pagesMap;
    }

    /**
     * Method for creating a csv file and writing list of entries into that file
     *
     * @param csvFileName csv file's name to be created and written into
     * @param listOfEntries list of entries to be written into file
     * @throws IOException
     */
    private static void writeEntriesIntoFile(String csvFileName, List<EntryDTO> listOfEntries) throws IOException {
        LOGGER.log(INFO, "Writing Entries into file --> " + csvFileName + " <--");

        try (CSVWriter writer = new CSVWriter(new FileWriter(new File(csvFileName)))) {
            String[] header = { "Id", "Title", "Body" , "Label" };
            writer.writeNext(header);

            for (EntryDTO entryDTO : listOfEntries) {
                String[] entry = { String.valueOf(entryDTO.getId()), entryDTO.getTitle(), entryDTO.getBody(), entryDTO.getLabel() };
                writer.writeNext(entry);
            }
        }
    }
}
