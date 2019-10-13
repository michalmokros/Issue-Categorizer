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
}
