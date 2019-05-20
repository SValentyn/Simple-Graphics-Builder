package beans;

import java.io.Serializable;

/**
 * Representation of the node (point) data.
 */
public class DataNode implements Serializable {

    private static final long serialVersionUID = 1L;

    private double x, y;
    private String date;

    public DataNode() {
        this(0, 0, "");
    }

    public DataNode(double x, double y, String date) {
        this.x = x;
        this.y = y;
        this.date = date;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "date - " + date + "; x -" + x + "; y - " + y;
    }

}
