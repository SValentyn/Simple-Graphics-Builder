package beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract representation of the node (point) data.
 */
public class DataSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private List<DataNode> data;

    public DataSheet() {
        title = "New DataSheet";
        data = new LinkedList<>();
    }

    private String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DataNode> getNodes() {
        return data;
    }

    public void setNodes(List<DataNode> data) {
        this.data = data;
    }

    public DataNode getData(int index) {
        return data.get(index);
    }

    public void addData(DataNode node) {
        data.add(node);
    }

    @Override
    public String toString() {
        return "DataSheet - " + getTitle() + getNodes().toString();
    }
}
