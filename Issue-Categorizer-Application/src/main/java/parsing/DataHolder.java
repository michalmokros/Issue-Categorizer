package parsing;

public class DataHolder {

    private final String title;
    private final String body;
    private final String label;

    public DataHolder(String title, String body, String label) {
        this.title = title;
        this.body = body;
        this.label = label;
    }

    public String getTitle() { return title; }
    public String getBody() { return body; }
    public String getLabel() { return label; }
}
