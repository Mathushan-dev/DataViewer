import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame {
    private String[][] data;
    private String[] columns;
    private final Model model;

    private Container main;
    private JPanel bottomPanel, checkBoxPanel;
    private JButton loadButton, operationButton, jsonWriteButton, barChartButton, lineGraphButton, maxButton, minButton;
    private JLabel countLabel, instructionLabel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane tablePane, checkBoxPane, bottomScrollPane;

    public GUI(Model model) {
        this.model = model;
        displayConfigurations();
        setMainContainer();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void displayConfigurations() {
        this.setSize(1000, 600);
        this.setTitle("Data viewer");
        this.setResizable(true);
    }

    private void setMainContainer() {
        main = this.getContentPane();
        initialiseTable(main);
        addLabelButtonPanel(main);
        addQueryingBoxes(main);
    }

    private void addQueryingBoxes(Container main) {
        checkBoxPanel = new JPanel();

        for (String queryName : this.model.getQueryNames()) {
            JCheckBox checkBox = new JCheckBox(queryName.strip());
            checkBoxPanel.add(checkBox, BorderLayout.EAST);
        }
        checkBoxPane = new JScrollPane(checkBoxPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        main.add(checkBoxPane, BorderLayout.NORTH);
    }

    private void addLabelButtonPanel(Container main) {
        bottomPanel = new JPanel();

        addInstructionLabel(bottomPanel);
        addLoadButton(bottomPanel);
        addSearchButton(bottomPanel);
        addMaxButton(bottomPanel);
        addMinButton(bottomPanel);
        addJsonWriteButton(bottomPanel);
        addBarChartButton(bottomPanel);
        addLineGraphButton(bottomPanel);
        addCountLabel(bottomPanel);

        bottomScrollPane = new JScrollPane(bottomPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        bottomScrollPane.createHorizontalScrollBar();
        main.add(bottomScrollPane, BorderLayout.SOUTH);
    }

    private void addInstructionLabel(JPanel jPanel) {
        instructionLabel = new JLabel();
        jPanel.add(instructionLabel);
    }

    private void addLoadButton(JPanel jPanel) {
        loadButton = new JButton("Load");
        loadButton.addActionListener(e -> updateTable());
        jPanel.add(loadButton);
    }

    private void addSearchButton(JPanel jPanel) {
        operationButton = new JButton("Search");
        operationButton.addActionListener(e -> performSearchEntry());
        jPanel.add(operationButton);
    }

    private void addMaxButton(JPanel jPanel) {
        maxButton = new JButton("Max");
        maxButton.addActionListener(e -> performMaxEntry(true));
        jPanel.add(maxButton);
    }

    private void addMinButton(JPanel jPanel) {
        minButton = new JButton("Min");
        minButton.addActionListener(e -> performMaxEntry(false));
        jPanel.add(minButton);
    }

    private void addJsonWriteButton(JPanel jPanel) {
        jsonWriteButton = new JButton("Write JSON to file");
        jsonWriteButton.addActionListener(e -> writeFile());
        jPanel.add(jsonWriteButton);
    }

    private void addCountLabel(JPanel jPanel) {
        countLabel = new JLabel();
        jPanel.add(countLabel);
    }

    private void addBarChartButton(JPanel jPanel) {
        barChartButton = new JButton("Bar Chart");
        barChartButton.addActionListener(e -> new GraphController(model, 0));
        jPanel.add(barChartButton);
    }

    private void addLineGraphButton(JPanel jPanel) {
        lineGraphButton = new JButton("Line Graph");
        lineGraphButton.addActionListener(e -> new GraphController(model, 1));
        jPanel.add(lineGraphButton);
    }

    private void initialiseTable(Container main) {
        this.data = new String[][]{};
        this.columns = new String[]{};
        tableModel = new DefaultTableModel(data, columns);
        table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null);
        table.setAutoCreateRowSorter(true);
        tablePane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        main.add(tablePane, BorderLayout.CENTER);
    }

    private void updateTable() {
        ArrayList<String> queries = this.getQueries();
        if (queries.isEmpty()) {
            cleanTable();
            return;
        }
        instructionLabel.setText("Click on columns to sort data.");
        changeTable(queries);
    }

    private void cleanTable() {
        this.data = new String[][]{};
        this.columns = new String[]{};
        countLabel.setText("No entries!");
        tableModel = new DefaultTableModel(data, columns);
        table.setModel(tableModel);
    }

    private void changeTable(ArrayList<String> queries) {
        model.queryTable(queries);
        this.columns = this.model.getQueriedColumns();
        this.data = this.model.getQueriedData();
        countLabel.setText(String.valueOf(this.data.length));

        tableModel = new DefaultTableModel(data, columns);
        table.setModel(tableModel);
    }

    private ArrayList<String> getQueries() {
        ArrayList<String> queries = new ArrayList<>();

        for (Component checkBox : checkBoxPanel.getComponents()) {
            if (checkBox instanceof JCheckBox) {
                if (((JCheckBox) checkBox).isSelected()) {
                    queries.add(((JCheckBox) checkBox).getText().strip());
                }
            }
        }
        return queries;
    }

    private void performSearchEntry() {
        String field = JOptionPane.showInputDialog("Enter the field you wish to search an entry for: ");
        if (field == null) {
            return;
        }

        String search = JOptionPane.showInputDialog("Enter the entry you wish to search in the field " + field.strip() + " for: ");
        if (search == null) {
            return;
        }

        model.searchEntry(field.strip(), search.strip());
        this.columns = this.model.getSearchedColumns();
        this.data = this.model.getSearchedData();
        countLabel.setText(String.valueOf(this.data.length));

        tableModel = new DefaultTableModel(data, columns);
        table.setModel(tableModel);
    }

    private void performMaxEntry(boolean max) {
        String choice = "maximum";
        if (!max) {
            choice = "minimum";
        }

        String field = JOptionPane.showInputDialog("Enter the field you wish to search the " + choice + " entry for: ");
        if (field == null) {
            return;
        }

        model.searchMax(field.strip(), max);
        this.columns = this.model.getOrderedColumns();
        this.data = this.model.getOrderedData();
        countLabel.setText(String.valueOf(this.data.length));

        tableModel = new DefaultTableModel(data, columns);
        table.setModel(tableModel);
    }

    private void writeFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                model.writeToJson(fileChooser.getSelectedFile().getAbsoluteFile().getAbsolutePath());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "File is not accessible.\nThe contents will be written to output.txt instead.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}