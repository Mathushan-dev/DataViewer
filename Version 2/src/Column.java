import java.util.ArrayList;

public class Column {
    private final String name;
    private final ArrayList<String> rows;

    public Column(String name) {
        this.name = name;
        this.rows = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getRows() {
        return this.rows;
    }

    public int getSize() {
        return this.rows.size();
    }

    public String getRowValue(int index) {
        return this.rows.get(index);
    }

    public void setRowValue(int index, String value) {
        this.rows.set(index, value);
    }

    public void addRowValue(String value) {
        this.rows.add(value);
    }
}
