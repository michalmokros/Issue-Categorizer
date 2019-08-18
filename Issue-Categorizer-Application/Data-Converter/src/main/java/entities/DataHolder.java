package entities;

public class DataHolder {

    private String title;
    private String body;
    private final String label;

    public DataHolder(String title, String body, String label) {
        this.title = title;
        this.body = body;
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLabel() {
        return label;
    }
}
