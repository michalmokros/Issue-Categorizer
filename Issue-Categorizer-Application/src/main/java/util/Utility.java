package util;

/**
 * Class for storing utility static methods
 *
 * @author xmokros 456442@mail.muni.cz
 */
public abstract class Utility {
    private Utility() {}

    public static String extractArg(String label, String[] args) {
        label = label.startsWith("-") ? label + "=" : "-" + label + "=";

        for (String arg : args) {
            if (arg.toLowerCase().startsWith(label.toLowerCase())) {
                return arg.substring(label.length());
            }
        }

        return null;
    }

    //../data/IssueCategorizer-atom-atom-issues-open-test.arff
    public static String addDataDirectoryToPathIfNot(String filePath) {
        if (filePath == null) {
            return null;
        }

        if (filePath.startsWith("../data/")) {
            return filePath;
        }

        return "../data/" + filePath;
    }

    public static boolean isArffFile(String fileName) throws Exception {
        String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);

        if (suffix.equals("arff")) {
            return true;
        } else if (suffix.equals("csv")) {
            return false;
        }

        throw new Exception("Unknown file suffix.");
    }

    public static String addArgPrefixSuffix(String value) {
        return "-" + value + "=";
    }

    public static String calculateTheBestClassifier(String nbSummary, String j48Summary, String rfSummary) {
        double nb = findPercentage(nbSummary);
        double j48 = findPercentage(j48Summary);
        double rf = findPercentage(rfSummary);

        double max = Math.max(Math.max(nb, j48), rf);

        if (Double.compare(max, nb) == 0) {
            return "nb";
        } else if (Double.compare(max, j48) == 0) {
            return "j48";
        }

        return "rf";
    }

    public static double findPercentage(String summary) {
        int index = summary.indexOf("%");;
        String percentage = summary.substring(index - 10, index).trim();
        return Double.parseDouble(percentage);
    }
}
