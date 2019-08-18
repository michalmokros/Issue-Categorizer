import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
    public static void main(String[] args) throws IOException {
//        if (args.length > 1) {
//            String username = args[0];
//            String password = args[1];
//            System.out.println(username + " and " + password);
//        }
        getIssues();
    }

    public static void getIssues() throws IOException {
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
        while ((input = in.readLine()) != null) {
            content.append(input);
        }
        in.close();
        connection.disconnect();
    }
}
