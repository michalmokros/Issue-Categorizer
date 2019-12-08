package enums;

/**
 * Enum class for different types of columns.
 *
 * @author xmokros
 */
public enum Column {
    ID("Id"),
    TITLE("Title"),
    BODY("Body"),
    LABEL("Label");

    private final String column;

    Column(final String column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return column;
    }
}
