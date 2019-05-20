package beans;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class DataSheetGraph extends JPanel {

    private static final long serialVersionUID = 1L;

    private DataSheet dataSheet = null;
    private boolean isConnected;
    private int deltaX;
    private int deltaY;
    private transient Color color;

    public DataSheetGraph() {
        super();
        initialize();
    }

    private void initialize() {
        isConnected = false;
        deltaX = 5;
        deltaY = 5;
        color = Color.RED;
        this.setSize(300, 400);
    }

    public DataSheet getDataSheet() {
        return dataSheet;
    }

    public void setDataSheet(DataSheet dataSheet) {
        this.dataSheet = dataSheet;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
        repaint();
    }

    public int getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
        repaint();
    }

    public int getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
        repaint();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    private double minX() {
        double result = 0;

        if (dataSheet != null) {
            result = dataSheet.getData(0).getX();
            for (DataNode d : dataSheet.getNodes()) {
                if (d.getX() < result) {
                    result = d.getX();
                }
            }
        }

        return result;
    }

    private double minY() {
        double result = 0;

        if (dataSheet != null) {
            result = dataSheet.getData(0).getY();
            for (DataNode d : dataSheet.getNodes()) {
                if (d.getY() < result) {
                    result = d.getY();
                }
            }
        }

        return result;
    }

    private double maxX() {
        double result = 0;

        if (dataSheet != null) {
            result = dataSheet.getData(0).getX();
            for (DataNode d : dataSheet.getNodes()) {
                if (d.getX() > result) {
                    result = d.getX();
                }
            }
        }

        return result;
    }

    private double maxY() {
        double result = 0;

        if (dataSheet != null) {
            result = dataSheet.getData(0).getY();
            for (DataNode d : dataSheet.getNodes()) {
                if (d.getY() > result) {
                    result = d.getY();
                }
            }
        }

        return result;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        showGraph(graphics2D);
    }

    private void showGraph(Graphics2D graphics2D) {
        double xMin, xMax, yMin, yMax;
        double width = getWidth();
        double height = getHeight();

        xMin = minX() - deltaX;
        xMax = maxX() + deltaX;
        yMin = minY() - deltaY;
        yMax = maxY() + deltaY;

        double xScale = width / (xMax - xMin);
        double yScale = height / (yMax - yMin);
        double x0 = -xMin * xScale;
        double y0 = yMax * yScale;

        Paint oldColor = graphics2D.getPaint();
        graphics2D.setPaint(Color.WHITE);
        graphics2D.fill(new Rectangle2D.Double(0.0, 0.0, width, height));

        Stroke oldStroke = graphics2D.getStroke();
        Font oldFont = graphics2D.getFont();

        float[] pattern = {5, 5};
        graphics2D.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, pattern, 0));
        graphics2D.setFont(new Font("Serif", Font.BOLD, 14));

        double xStep = getStep(width, xScale);
        double xScaleStep = xStep * xScale;
        for (double x = x0 - xScaleStep; x > 0; x -= xScaleStep) {
            graphics2D.setPaint(Color.LIGHT_GRAY);
            graphics2D.draw(new Line2D.Double(x, 0, x, height));
            graphics2D.setPaint(Color.BLACK);
            graphics2D.drawString(Math.round((x - x0) / xScale) + "", (int) x + 1, (int) y0 + 15);
        }

        for (double x = x0 + xScaleStep; x < width; x += xScaleStep) {
            graphics2D.setPaint(Color.LIGHT_GRAY);
            graphics2D.draw(new Line2D.Double(x, 0, x, height));
            graphics2D.setPaint(Color.BLACK);
            graphics2D.drawString(Math.round((x - x0) / xScale) + "", (int) x + 1, (int) y0 + 15);
        }

        double yStep = getStep(height, yScale);
        double yScaleStep = yStep * yScale;
        for (double y = y0 - yScaleStep; y > 0; y -= yScaleStep) {
            graphics2D.setPaint(Color.LIGHT_GRAY);
            graphics2D.draw(new Line2D.Double(0, y, width, y));
            graphics2D.setPaint(Color.BLACK);
            graphics2D.drawString(Math.round((y - y0) / -yScale) + "", (int) x0 - 15, (int) y - 1);
        }

        for (double y = y0 + yScaleStep; y < height; y += yScaleStep) {
            graphics2D.setPaint(Color.LIGHT_GRAY);
            graphics2D.draw(new Line2D.Double(0, y, width, y));
            graphics2D.setPaint(Color.BLACK);
            graphics2D.drawString(Math.round((y - y0) / -yScale) + "", (int) x0 - 20, (int) y - 1);
        }

        graphics2D.setPaint(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(3.0f));
        graphics2D.draw(new Line2D.Double(x0, 0, x0, height));
        graphics2D.draw(new Line2D.Double(0, y0, width, y0));
        graphics2D.drawString("X", (int) width - 10, (int) y0 - 2);
        graphics2D.drawString("Y", (int) x0 + 2, 10);

        if (dataSheet != null) {
            if (!isConnected || dataSheet.getNodes().size() == 1) {
                for (DataNode d : dataSheet.getNodes()) {
                    double x = x0 + d.getX() * xScale;
                    double y = y0 - d.getY() * yScale;
                    graphics2D.setColor(color);
                    graphics2D.drawOval((int) (x - 5 / 2), (int) (y - 5 / 2), 5, 5);
                }
            } else {
                graphics2D.setPaint(color);
                graphics2D.setStroke(new BasicStroke(2.0f));
                double xOld = x0 + dataSheet.getData(0).getX() * xScale;
                double yOld = y0 - dataSheet.getData(0).getY() * yScale;
                for (int i = 1; i < dataSheet.getNodes().size(); i++) {
                    double x = x0 + dataSheet.getData(i).getX() * xScale;
                    double y = y0 - dataSheet.getData(i).getY() * yScale;
                    graphics2D.draw(new Line2D.Double(xOld, yOld, x, y));
                    xOld = x;
                    yOld = y;
                }

            }

        }

        graphics2D.setPaint(oldColor);
        graphics2D.setStroke(oldStroke);
        graphics2D.setFont(oldFont);
    }

    private double getStep(double size, double scale) {
        return Math.round(size / 4 / scale);
    }

}
