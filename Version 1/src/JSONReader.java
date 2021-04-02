import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JSONReader {
    private final ArrayList<Column> columns;
    private final DataFrame dataFrame;

    public JSONReader() {
        this.columns = new ArrayList<>();
        this.dataFrame = new DataFrame();
    }

    public DataFrame obtainDataFrame(String fileName) {
        this.readFromFile(fileName);

        for (Column column : this.columns) {
            this.dataFrame.addColumn(column);
        }

        return this.dataFrame;
    }

    private void readFromFile(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine();

            while (line != null) {
                if (line.contains(":")) {
                    fillColumn(line);
                }
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(new JFrame(), "File " + fileName + " could not be found.\nPlease try another file.", "FNF", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(new JFrame(), "File " + fileName + " could not be read.\nPlease try another file.", "FNR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initialiseColumn(String key, String value) {
        this.columns.add(new Column(key));
        this.columns.get(this.columns.size() - 1).addRowValue(value);
    }

    private void fillColumn(String line) {
        line = line.replaceAll("[\",]", "");

        String key = "", value = "";
        if (line.split(":").length == 1) {
            key = line.split(":")[0].strip();
        }

        if (line.split(":").length == 2) {
            key = line.split(":")[0].strip();
            value = line.split(":")[1].strip();
        }

        for (Column column : this.columns) {
            if (column.getName().compareToIgnoreCase(key) == 0) {
                column.addRowValue(value);
                return;
            }
        }

        if (key != "") {
            initialiseColumn(key, value);
        }
    }
}

