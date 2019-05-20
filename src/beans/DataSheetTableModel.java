package beans;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Table structure and manipulation of various actions.
 */
public class DataSheetTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private int rowCount = 1;
    private int columnCount = 3;
    private DataSheet dataSheet = null;

    private DataChangeEvent changeEvent = new DataChangeEvent(this);
    private List<DataSheetChangeListener> listenerList = new ArrayList<>();

    private String[] columnNames = {"Date", "X", "Y"};

    public DataSheet getDataSheet() {
        return dataSheet;
    }

    public void setDataSheet(DataSheet dataSheet) {
        this.dataSheet = dataSheet;
        rowCount = this.dataSheet.getNodes().size();
        fireDataSheetChange();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        if (rowCount > 0) this.rowCount = rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex > 0);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            double value;
            if (dataSheet != null) {
                if (columnIndex == 0) {
                    dataSheet.getData(rowIndex).setDate((String) aValue);
                } else if (columnIndex == 1) {
                    value = Double.parseDouble((String) aValue);
                    dataSheet.getData(rowIndex).setX(value);
                } else if (columnIndex == 2) {
                    value = Double.parseDouble((String) aValue);
                    dataSheet.getData(rowIndex).setY(value);
                }
                fireDataSheetChange();
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (dataSheet != null) {
            if (columnIndex == 0) {
                return dataSheet.getData(rowIndex).getDate();
            } else if (columnIndex == 1) {
                return dataSheet.getData(rowIndex).getX();
            } else if (columnIndex == 2) {
                return dataSheet.getData(rowIndex).getY();
            }
        }
        return null;
    }

    public void addDataSheetChangeListener(DataSheetChangeListener listener) {
        listenerList.add(listener);
    }

    public void removeDataSheetChangeListener(DataSheetChangeListener listener) {
        listenerList.remove(listener);
    }

    public void fireDataSheetChange() {
        for (DataSheetChangeListener listener : listenerList) {
            listener.dataChanged(changeEvent);
        }
    }

}
