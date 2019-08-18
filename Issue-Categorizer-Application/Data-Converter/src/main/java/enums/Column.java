package enums;

public enum Column {
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
