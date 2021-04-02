import java.util.ArrayList;

public class DataFrame implements DataFrameInterface {
    private final ArrayList<Column> columns;

    public DataFrame() {
        this.columns = new ArrayList<>();
    }

    public ArrayList<Column> getColumns() {
        return this.columns;
    }

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public ArrayList<String> getColumnNames() {
        ArrayList<String> columnNames = new ArrayList<>();
        for (Column column : this.columns) {
            columnNames.add(column.getName());
        }
        return columnNames;
    }

    public int getRowCount(String columnName) {
        for (Column column : this.columns) {
            if (column.getName().compareTo(columnName) == 0) {
                return column.getSize();
            }
        }
        return -1;
    }

    public String getValue(String columnName, int rowIndex) {
        for (Column column : this.columns) {
            if (column.getName().compareTo(columnName) == 0) {
                return column.getRowValue(rowIndex);
            }
        }
        return null;
    }

    public void putValue(String columnName, int rowIndex, String value) {
        for (Column column : this.columns) {
            if (column.getName().compareTo(columnName) == 0) {
                column.setRowValue(rowIndex, value);
            }
        }
    }

    public void addValue(String columnName, String value) {
        for (Column column : this.columns) {
            if (column.getName().compareTo(columnName) == 0) {
                column.addRowValue(value);
            }
        }
    }
}
