import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader {
    private ArrayList<Column> columns;
    private DataFrame dataFrame;
    private JFileChooser fileSelector;

    public DataLoader() {
        this.columns = new ArrayList<>();
        this.dataFrame = new DataFrame();
        initialiseFileSelector();
    }

    public DataFrame obtainDataFrame() {
        String fileName = this.determineFileName();
        String fileType = this.determineFileType();

        if (fileType.compareTo("1") == 0) {
            this.readFromFile(fileName);
        }
        if (fileType.compareTo("2") == 0) {
            this.readFromJSON(fileName);
        }

        for (Column column : this.columns) {
            this.dataFrame.addColumn(column);
        }

        return this.dataFrame;
    }

    private void initialiseFileSelector() {
        fileSelector = new JFileChooser();
        fileSelector.setFileFilter(new FileNameExtensionFilter("JSON and CSV files", "txt", "csv"));
        fileSelector.setAcceptAllFileFilterUsed(false);
    }

    private String determineFileName() {
        int option = fileSelector.showOpenDialog(new JFrame());

        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                return fileSelector.getSelectedFile().getAbsolutePath().strip();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(new JFrame(), "File is not accessible.\nPlease try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return determineFileName();
            }
        }

        JOptionPane.showMessageDialog(new JFrame(), "You have not selected a file.\nPlease try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
        return determineFileName();
    }

    private String determineFileType() {
        String option = JOptionPane.showInputDialog(new JFrame(), "How do you wish to parse the selected file?\n\t1. CSV\n\t2. JSON\n\nEnter 1 or 2 to choose.", "File Type", JOptionPane.QUESTION_MESSAGE);
        if (option == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You must provide a file type.\nPlease try again!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return determineFileType();
        }
        if (option.strip().compareTo("1") != 0 && option.strip().compareTo("2") != 0) {
            JOptionPane.showMessageDialog(new JFrame(), option.strip() + " is not a valid option.\nPlease try again!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return determineFileType();
        }
        return option.strip();
    }

    private void readFromFile(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            readLines(bufferedReader);
            return;
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(new JFrame(), "File " + fileName + " could not be found.\nPlease try another file.", "FNF", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(new JFrame(), "File " + fileName + " could not be read.\nPlease try another file.", "FNR", JOptionPane.ERROR_MESSAGE);
        }
        this.readFromFile(determineFileName());
    }

    private void readLines(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        while (line != null) {
            if (line.contains("[") || line.contains("{")) {
                this.columns = new ArrayList<>();
                JOptionPane.showMessageDialog(new JFrame(), "Cannot parse the selected file in CSV format.\nPlease select a correct CSV file.", "PARSE ERROR", JOptionPane.ERROR_MESSAGE);
                readFromFile(determineFileName());
                return;
            }
            fillColumns(line);
            line = bufferedReader.readLine();
        }

        while (this.columns.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Cannot parse the selected file in CSV format.\nPlease select a correct CSV file.", "PARSE ERROR", JOptionPane.ERROR_MESSAGE);
            readFromFile(determineFileName());
        }
    }

    private void readFromJSON(String fileName) {
        JSONReader jsonReader = new JSONReader();

        DataFrame dataFrame = jsonReader.obtainDataFrame(fileName);
        while (dataFrame.getColumns().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Cannot parse the selected file in JSON format.\nPlease select a correct JSON file.", "PARSE ERROR", JOptionPane.ERROR_MESSAGE);
            dataFrame = jsonReader.obtainDataFrame(determineFileName());
        }

        this.dataFrame = dataFrame;
    }

    private void initialiseColumns(String line) {
        if (line == null) {
            return;
        }

        for (String columnName : line.split(",")) {
            this.columns.add(new Column(columnName.strip()));
        }
    }

    private void fillColumns(String line) {
        if (line == null) {
            return;
        }

        if (this.columns.isEmpty()) {
            initialiseColumns(line);
            return;
        }

        int i = 0;
        for (String value : line.split(",")) {
            if (i > this.columns.size() - 1) {
                return;
            }
            this.columns.get(i).addRowValue(value.strip());
            i++;
        }

        while (i < this.columns.size()) {
            this.columns.get(i).addRowValue("");
            i++;
        }
    }
}
