package dto;

/**
 * DTO Entry class for Issue extracted from github api page.
 *
 * @author xmokros
 */
public class EntryDTO {
    private long id;
    private String title;
    private String body;
    private String label;

    public EntryDTO(long id, String title, String body, String label) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "EntryDTO{" +
                "id='" + id + '\'' +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
