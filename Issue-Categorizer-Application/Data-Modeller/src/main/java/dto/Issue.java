package dto;

import java.util.Objects;

public class Issue {
    private String id;
    private String title;
    private String body;
    private String label;

    public Issue(String id, String title, String body, String label) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(id, issue.id) &&
                Objects.equals(title, issue.title) &&
                Objects.equals(body, issue.body) &&
                Objects.equals(label, issue.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, label);
    }
}
