import javax.swing.*;

public class GraphController {
    private final int BAR_TYPE = 0, LINE_TYPE = 1;
    private final int graphType;
    private final Model model;

    public GraphController(Model model, int graphType) {
        this.model = model;
        this.graphType = graphType;
        obtainColumn();
    }

    private void obtainColumn() {
        String chosenColumn = JOptionPane.showInputDialog(null, "Enter the column name: ");
        if (chosenColumn == null) {
            return;
        }

        for (Column column : this.model.getDataFrame().getColumns()) {
            if (column.getName().compareToIgnoreCase(chosenColumn.strip()) == 0) {
                createGraph(column);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "The column " + chosenColumn.strip() + " was not found!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void createGraph(Column column) {
        if (this.graphType == BAR_TYPE) {
            new BarChart(column);
        } else if (this.graphType == LINE_TYPE) {
            new LineGraph(column);
        }
    }
}
