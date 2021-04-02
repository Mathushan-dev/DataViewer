import java.awt.*;

public class BarChart extends Graph {
    public BarChart(Column column) {
        super(column);
        drawGraph("Bar Chart", "bars");
    }

    @Override
    protected void paintComponent(Graphics g) {
        int max = 0;
        for (Integer value : data.values()) {
            max = Math.max(max, value);
        }

        int width = ((getWidth() - LEFT_X_AXIS_OFFSET - RIGHT_X_AXIS_OFFSET - GAP_BETWEEN_BARS * data.size()) / data.size());
        int height = (int) ((getHeight() - BOTTOM_Y_AXIS_OFFSET) * ((double) max / (max * TOP_Y_AXIS_OFFSET_RATIO)));

        initialiseAxes(g, height, width, max);

        max *= TOP_Y_AXIS_OFFSET_RATIO;
        int x = LEFT_X_AXIS_OFFSET;

        for (String label : data.keySet()) {
            int value = data.get(label);
            height = (int) ((getHeight() - BOTTOM_Y_AXIS_OFFSET) * ((double) value / max));

            g.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
            g.fillRect(x, getHeight() - BOTTOM_Y_AXIS_OFFSET - height, width, height);
            g.setColor(Color.black);
            g.drawRect(x, getHeight() - BOTTOM_Y_AXIS_OFFSET - height, width, height);

            x += (width + GAP_BETWEEN_BARS);
        }
    }
}