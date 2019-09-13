/**
 * Main application class for executing whole programme.
 *
 * @author xmokros
 */
public class Main {
    public static void main(String[] args) throws Exception {
        String[] downloaderInput = {"atom/atom", "open"};
        String csvFileName = Downloader.main(downloaderInput);
        String[] converterInput = {csvFileName};
        String arffFileName = Converter.main(converterInput);
    }
}
