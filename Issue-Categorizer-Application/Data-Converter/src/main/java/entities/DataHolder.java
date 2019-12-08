package entities;

/**
 * Class for holding entries of data containing information.
 *
 * @author xmokros
 */
public class DataHolder {

    private final Long id;
    private String title;
    private String body;
    private final String label;

    public DataHolder(Long id, String title, String body, String label) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.label = label;
    }

    public Long getId() {
        return this.id;
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
