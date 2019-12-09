package entities;

/**
 * Class for holding entries of data containing information.
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class DataHolder {

    private String id;
    private String title;
    private String body;
    private final String label;

    public DataHolder(String id, String title, String body, String label) {
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
}
