/**
 * Main application class for executing whole programme.
 *
 * @author xmokros
 */
public class Main {
    public static void main(String[] args) throws Exception {
        for (String arg : args) {
            System.out.println(arg);
        }
        String[] downloaderInput = {"atom/atom", "open"};
        String csvFileName = Downloader.main(downloaderInput);
        String[] converterInput = {csvFileName};
        String arffFileName = Converter.main(converterInput);
    }
}
