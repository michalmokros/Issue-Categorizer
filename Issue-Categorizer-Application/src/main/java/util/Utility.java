package util;

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
}
