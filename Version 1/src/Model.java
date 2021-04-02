import java.util.ArrayList;

public class Model {
    private final DataFrame dataFrame;
    private DataFrame queriedDataFrame;
    private DataFrame searchedDataFrame;
    private DataFrame orderedDataFrame;

    public Model() {
        this.dataFrame = new DataLoader().obtainDataFrame();
    }

    public DataFrame getDataFrame() {
        return this.dataFrame;
    }

    public String[] getQueriedColumns() {
        return retrieveColumns(this.queriedDataFrame);
    }

    public String[][] getQueriedData() {
        return retrieveData(this.queriedDataFrame);
    }

    public String[] getSearchedColumns() {
        return retrieveColumns(this.searchedDataFrame);
    }

    public String[][] getSearchedData() {
        return retrieveData(this.searchedDataFrame);
    }

    public String[] getOrderedColumns() {
        return retrieveColumns(this.orderedDataFrame);
    }

    public String[][] getOrderedData() {
        return retrieveData(this.orderedDataFrame);
    }

    private String[] retrieveColumns(DataFrame dataFrame) {
        String[] columnNames = new String[dataFrame.getColumns().size()];
        int i = 0;
        for (Column column : dataFrame.getColumns()) {
            columnNames[i] = column.getName().strip();
            i++;
        }
        return columnNames;
    }

    private String[][] retrieveData(DataFrame dataFrame) {
        String[][] columnData = new String[dataFrame.getColumns().get(0).getSize()][dataFrame.getColumns().size()];
        int i = 0;
        for (Column column : dataFrame.getColumns()) {
            for (int j = 0; j < dataFrame.getColumns().get(0).getSize(); j++) {
                columnData[j][i] = column.getRowValue(j);
            }
            i++;
        }
        return columnData;
    }

    public ArrayList<String> getQueryNames() {
        return this.dataFrame.getColumnNames();
    }

    public void queryTable(ArrayList<String> query) {
        this.queriedDataFrame = new DataFrame();
        this.processQuery(query);
    }

    private void processQuery(ArrayList<String> query) {
        for (String queryName : query) {
            for (Column column : this.dataFrame.getColumns()) {
                if (queryName.strip().compareToIgnoreCase(column.getName().strip()) == 0) {
                    this.queriedDataFrame.addColumn(column);
                }
            }
        }
    }

    public void searchEntry(String field, String search) {
        this.searchedDataFrame = new DataFrame();
        addMatchingColumnNames(this.searchedDataFrame, this.dataFrame);

        Column selectedColumn = getSelectedColumn(field, this.dataFrame);
        if (selectedColumn == null) {
            return;
        }

        for (int i = 0; i < selectedColumn.getSize(); i++) {
            if (selectedColumn.getRowValue(i).compareToIgnoreCase(search) == 0) {
                copyIndexElements(this.searchedDataFrame, this.dataFrame, i);
            }
        }
    }

    public void searchMax(String field, boolean max) {
        this.orderedDataFrame = new DataFrame();
        addMatchingColumnNames(this.orderedDataFrame, this.dataFrame);

        Column selectedColumn = getSelectedColumn(field, this.dataFrame);
        if (selectedColumn == null) {
            return;
        }

        String maxValue = selectedColumn.getRowValue(0);
        for (int i = 1; i < selectedColumn.getSize(); i++) {
            maxValue = maxOperations(selectedColumn, maxValue, i, max);
        }
    }

    private String maxOperations(Column selectedColumn, String maxValue, int i, boolean max){
        if (selectedColumn.getRowValue(i).compareToIgnoreCase(maxValue) == 0) {
            copyIndexElements(this.orderedDataFrame, this.dataFrame, i);
        }

        if (selectedColumn.getRowValue(i).compareToIgnoreCase(maxValue) > 0 && max) {
            this.orderedDataFrame = new DataFrame();
            addMatchingColumnNames(this.orderedDataFrame, this.dataFrame);
            copyIndexElements(this.orderedDataFrame, this.dataFrame, i);
            maxValue = selectedColumn.getRowValue(i);
        }

        if (selectedColumn.getRowValue(i).compareToIgnoreCase(maxValue) < 0 && !max) {
            this.orderedDataFrame = new DataFrame();
            addMatchingColumnNames(this.orderedDataFrame, this.dataFrame);
            copyIndexElements(this.orderedDataFrame, this.dataFrame, i);
            maxValue = selectedColumn.getRowValue(i);
        }

        return maxValue;
    }

    private void addMatchingColumnNames(DataFrame dataFrame1, DataFrame dataFrame2) {
        for (Column column : dataFrame2.getColumns()) {
            dataFrame1.addColumn(new Column(column.getName()));
        }
    }

    private Column getSelectedColumn(String columnName, DataFrame dataFrame) {
        for (Column column : dataFrame.getColumns()) {
            if (column.getName().compareToIgnoreCase(columnName) == 0) {
                return column;
            }
        }
        return null;
    }

    private void copyIndexElements(DataFrame dataFrame1, DataFrame dataFrame2, int index) {
        for (int j = 0; j < dataFrame1.getColumns().size(); j++) {
            dataFrame1.addValue(dataFrame1.getColumnNames().get(j), dataFrame2.getValue(dataFrame2.getColumnNames().get(j), index));
        }
    }

    public void writeToJson(String fileName) {
        JSONWriter jsonWriter = new JSONWriter(this.dataFrame);
        jsonWriter.convertToJSON();
        jsonWriter.writeToFile(fileName);
    }
}
