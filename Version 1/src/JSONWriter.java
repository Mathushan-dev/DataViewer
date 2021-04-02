import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {
    private final DataFrame dataFrame;
    private final StringBuilder stringBuilder;

    public JSONWriter(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
        this.stringBuilder = new StringBuilder();
    }

    public void writeToFile(String fileName) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            if (stringBuilder.isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are no contents to write to the file.");
                return;
            }

            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
            JOptionPane.showMessageDialog(null, "File written successfully in JSON format.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File " + fileName + " could not be written to.\nThe contents will be written to output.txt instead.", "Error", JOptionPane.ERROR_MESSAGE);
            writeToFile("output.txt");
        }
    }

    public void convertToJSON() {
        this.stringBuilder.append("[\n");
        for (int i = 0; i < this.dataFrame.getColumns().get(0).getSize(); i++) {
            this.stringBuilder.append("\t{\n");

            addJSONItems(i);

            if (i != this.dataFrame.getColumns().get(0).getSize() - 1) {
                this.stringBuilder.append("\t},\n");
            } else {
                stringBuilder.append("\t}\n");
            }
        }
        this.stringBuilder.append("]\n");
    }

    private void addJSONItems(int itemNumber) {
        for (int j = 0; j < this.dataFrame.getColumns().size(); j++) {
            if (j == this.dataFrame.getColumns().size() - 1) {
                this.stringBuilder.append("\t\t\"" + this.dataFrame.getColumns().get(j).getName() + "\":\"" + this.dataFrame.getColumns().get(j).getRowValue(itemNumber) + "\"\n");
                break;
            }
            this.stringBuilder.append("\t\t\"" + this.dataFrame.getColumns().get(j).getName() + "\":\"" + this.dataFrame.getColumns().get(j).getRowValue(itemNumber) + "\",\n");
        }
    }
}

