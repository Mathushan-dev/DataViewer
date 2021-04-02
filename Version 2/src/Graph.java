import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Graph extends JPanel {
    protected static final int LEFT_X_AXIS_OFFSET = 150, RIGHT_X_AXIS_OFFSET = 10, BOTTOM_Y_AXIS_OFFSET = 50, X_AXIS_TITLE_OFFSET = 40, Y_AXIS_TITLE_OFFSET = 140;
    protected static final float TOP_Y_AXIS_OFFSET_RATIO = 1.1f;
    protected static final int GAP_BETWEEN_BARS = 20, GAP_BETWEEN_X_LABELS = 20, GAP_BETWEEN_Y_LABELS = 50, NUM_OF_Y_LABELS = 10;
    protected static final int MAX_NUM_BARS = 10;
    protected JFrame frame;
    protected Column column;
    protected Map<String, Integer> data;

    public Graph(Column column) {
        this.column = column;
        this.data = new LinkedHashMap<>();
    }

    protected void constructData() {
        for (String label : column.getRows()) {
            if (this.data.containsKey(label)) {
                this.data.replace(label, this.data.get(label) + 1);
            } else {
                this.data.put(label, 1);
            }
        }
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(data.size() * 10 + 600, 500);
    }

    protected void initialiseAxes(Graphics g, int height, int width, int max) {
        g.setColor(Color.BLACK);
        g.drawLine(LEFT_X_AXIS_OFFSET, getHeight() - BOTTOM_Y_AXIS_OFFSET, getWidth() - RIGHT_X_AXIS_OFFSET, getHeight() - BOTTOM_Y_AXIS_OFFSET);
        g.drawLine(LEFT_X_AXIS_OFFSET, getHeight() - BOTTOM_Y_AXIS_OFFSET, LEFT_X_AXIS_OFFSET, getHeight() - BOTTOM_Y_AXIS_OFFSET - height);
        g.drawString(column.getName(), (LEFT_X_AXIS_OFFSET + getWidth() - RIGHT_X_AXIS_OFFSET) / 2, getHeight() - BOTTOM_Y_AXIS_OFFSET + X_AXIS_TITLE_OFFSET);
        g.drawString("Frequency", LEFT_X_AXIS_OFFSET - Y_AXIS_TITLE_OFFSET, (getHeight() - BOTTOM_Y_AXIS_OFFSET + getHeight() - BOTTOM_Y_AXIS_OFFSET - height) / 2);

        int i = 0;
        for (String key : data.keySet()) {
            if (key == "") {
                key = "Other";
            }
            g.drawString(key, LEFT_X_AXIS_OFFSET + Math.round(width / 3) + i * (GAP_BETWEEN_BARS + width), getHeight() - (BOTTOM_Y_AXIS_OFFSET - GAP_BETWEEN_X_LABELS));
            i++;
        }

        for (int j = 0; j <= NUM_OF_Y_LABELS; j++) {
            int round = Math.round(getHeight() - BOTTOM_Y_AXIS_OFFSET - j * (height / NUM_OF_Y_LABELS));
            if (j == NUM_OF_Y_LABELS) {
                round = getHeight() - BOTTOM_Y_AXIS_OFFSET - height;
            }
            g.drawLine(LEFT_X_AXIS_OFFSET, round, LEFT_X_AXIS_OFFSET - 5, round);
            g.drawString(String.valueOf(j * (max / NUM_OF_Y_LABELS)), LEFT_X_AXIS_OFFSET - GAP_BETWEEN_Y_LABELS, round);
        }
    }

    protected void drawGraph(String title, String auxillaryName) {
        this.constructData();
        if (this.data.size() > MAX_NUM_BARS) {
            JOptionPane.showMessageDialog(null, "This column cannot be drawn as there are too many " + auxillaryName + ".\nThis column has more than " + MAX_NUM_BARS + " different groups.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        frame = new JFrame(title);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }
}
