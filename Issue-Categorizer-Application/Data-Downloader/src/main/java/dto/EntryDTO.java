package dto;

/**
 * DTO Entry class for Issue extracted from github api page.
 *
 * @author xmokros 456442@mail.muni.cz
 */
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

    @Override
    public String toString() {
        return "EntryDTO{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
