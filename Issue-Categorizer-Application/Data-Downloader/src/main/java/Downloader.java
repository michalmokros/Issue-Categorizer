import com.opencsv.CSVWriter;
import dto.EntryDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        URL url = new URL("https://api.github.com/repos/atom/atom/issues?state=open");
        System.out.println(url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        int status = connection.getResponseCode();
        BufferedReader  in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input;
        StringBuffer content = new StringBuffer();

        List<EntryDTO> listOfEntries = new ArrayList<>();
        while ((input = in.readLine()) != null) {
            getEntries(listOfEntries, input);
        }

        if (connection.getHeaderFields().containsKey("link")) {
            
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(new File("../data/java-atom-atom-issues-all.csv")))) {
            String[] header = { "Title", "Body" , "Label" };
            writer.writeNext(header);

            for (EntryDTO entryDTO : listOfEntries) {
                String[] entry = { entryDTO.getTitle(), entryDTO.getBody(), entryDTO.getLabel() };
                writer.writeNext(entry);
            }
        }

        in.close();
        connection.disconnect();
    }

    private static void getEntries(List<EntryDTO> listOfEntries, String content) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(content);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            String label = getLabel((JSONArray) jsonObject.get("labels"));

            if (label.isEmpty()) {
                continue;
            }

            String title = jsonObject.get("title").toString();
            String body = jsonObject.get("body").toString();
            listOfEntries.add(new EntryDTO(title, body, label));
        }
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
}


