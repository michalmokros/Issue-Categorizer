import com.opencsv.CSVWriter;
import dto.EntryDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Downloader {
    public static void main(String[] args) throws IOException, ParseException {
//        if (args.length > 1) {
//            String username = args[0];
//            String password = args[1];
//            System.out.println(username + " and " + password);
//        }
        getIssues();
    }

    public static void getIssues() throws IOException, ParseException {
//        URL url = new URL("https://api.github.com/repos/" + user + "/issues?state=" + repository);
        String urlString = "https://api.github.com/repos/atom/atom/issues?state=open";

        List<EntryDTO> listOfEntries = extractEntriesRecursively(urlString);

        try (CSVWriter writer = new CSVWriter(new FileWriter(new File("../data/java-atom-atom-issues-all.csv")))) {
            String[] header = { "Title", "Body" , "Label" };
            writer.writeNext(header);

            for (EntryDTO entryDTO : listOfEntries) {
                String[] entry = { entryDTO.getTitle(), entryDTO.getBody(), entryDTO.getLabel() };
                writer.writeNext(entry);
            }
        }
    }

    private static List<EntryDTO> extractEntriesRecursively(String urlString) throws IOException, ParseException {
        URL url = new URL(urlString);
        System.out.println(url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        int status = connection.getResponseCode();
        BufferedReader  in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input;
        List<EntryDTO> listOfEntries = new ArrayList<>();

        while ((input = in.readLine()) != null) {
            listOfEntries = getEntries(input);
        }

        if (connection.getHeaderFields().containsKey("Link")) {
            Map<String, String> pagesMap = getPages(connection.getHeaderFields().get("Link").get(0));

            for (Map.Entry<String, String> pageEntry : pagesMap.entrySet()) {
                if (pageEntry.getKey().equals("next")) {
                    listOfEntries.addAll(extractEntriesRecursively(pageEntry.getValue()));
                }
            }
        }

        in.close();
        connection.disconnect();
        return listOfEntries;
    }

    private static List<EntryDTO> getEntries(String content) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(content);
        List<EntryDTO> listOfEntries = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            if (jsonObject.containsKey("pull_request")) {
                continue;
            }

            String label = getLabel((JSONArray) jsonObject.get("labels"));

            if (label.isEmpty()) {
                continue;
            }

            String title = jsonObject.get("title").toString();
            String body = jsonObject.get("body").toString();
            listOfEntries.add(new EntryDTO(title, body, label));
        }

        return listOfEntries;
    }

    private static String getLabel(JSONArray labels) {
        for (int i = 0; i < labels.size(); i++) {
            String label = ((JSONObject) labels.get(0)).get("name").toString();

            if (label.equals("bug")) {
                return "bug";
            } else if (label.equals("enhancement")) {
                return "enhancement";
            }
        }

        return "";
    }

    private static Map<String, String> getPages(String linkFromHeader) {
        String[] linkMap = linkFromHeader.split(", ");
        Map<String, String> pagesMap = new HashMap<>();

        for (String linkMapEntry : linkMap) {
            String[] linkDouble = linkMapEntry.split("; ");
            String link = linkDouble[0].substring(1, linkDouble[0].length() - 1);
            String key = linkDouble[1].substring(5, linkDouble[1].length() - 1);
            pagesMap.put(key, link);
        }

        return pagesMap;
    }
}


