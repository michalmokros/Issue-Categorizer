import weka.core.Utils;

/**
 * Main application class for executing whole programme.
 *
 * @author xmokros
 */
public class Main {
    public static void main(String[] args) throws Exception {
//        String messageName = Utils.getOption('m', args);
//        System.out.println(messageName);
//        String[] downloaderInput = {"atom/atom", "open"};
//        String csvFileName = Downloader.main(downloaderInput);
//        String[] converterInput = {csvFileName};
//        String arffFileName = Converter.main(converterInput); //../data/IssueCategorizer-atom-atom-issues-open.arff
        String arffFileName = "../data/IssueCategorizer-atom-atom-issues-open.arff";
        String arffTestFileName = "../data/IssueCategorizer-atom-atom-issues-open-test.arff";
        String[] modellerInput = {arffFileName, arffTestFileName}; //../data/IssueCategorizer-atom-atom-issues-open-test.arff
        Modeller.main(modellerInput);
    }
}
