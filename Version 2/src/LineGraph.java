import java.awt.*;

public class LineGraph extends Graph {
    public LineGraph(Column column) {
        super(column);
        drawGraph("Line Graph", "plots");
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
        int x = LEFT_X_AXIS_OFFSET + width / 2;
        int prevX = -1, prevY = -1;

        for (String label : data.keySet()) {
            int value = data.get(label);
            height = (int) ((getHeight() - BOTTOM_Y_AXIS_OFFSET) * ((double) value / max));

            g.setColor(Color.ORANGE);
            g.fillOval(x, getHeight() - BOTTOM_Y_AXIS_OFFSET - height, 10, 10);

            if (prevX != -1 && prevY != -1) {
                g.setColor(Color.BLUE);
                g.drawLine(x + 5, getHeight() - BOTTOM_Y_AXIS_OFFSET - height + 5, prevX + 5, prevY + 5);
            }

            prevX = x;
            prevY = getHeight() - BOTTOM_Y_AXIS_OFFSET - height;

            x += (width + GAP_BETWEEN_BARS);
        }
    }
}