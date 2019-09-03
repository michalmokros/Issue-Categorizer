package dto;

public class EntryDTO {
    private String title;
    private String body;
    private String label;

    public EntryDTO(String title, String body, String label) {
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

    public void setLabel(String label) {
        this.label = label;
    }
}
