package beans;

import javax.swing.*;
import java.awt.*;

/**
 * Class for presenting data in a table with a button control panel.
 */
public class DataSheetTable extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable table;
    private DataSheetTableModel tableModel;
    private JPanel panelButtons;

    public DataSheetTable() {
        table = new JTable();
        tableModel = new DataSheetTableModel();
        setLayout(new BorderLayout());

        createPanelButtons();
        createAddButton();
        createDeleteButton();
        createScrollPane();
    }

    private void createPanelButtons() {
        panelButtons = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panelButtons.getLayout();
        flowLayout.setHgap(25);
        add(panelButtons, BorderLayout.SOUTH);
    }

    private void createAddButton() {
        JButton addButton = new JButton("Add (+)");
        addButton.addActionListener(event -> {
            tableModel.setRowCount(tableModel.getRowCount() + 1);
            tableModel.getDataSheet().addData(new DataNode());
            table.revalidate();
            tableModel.fireDataSheetChange();
        });
        panelButtons.add(addButton);
    }

    private void createDeleteButton() {
        JButton deleteButton = new JButton("Del (-)");
        deleteButton.addActionListener(event -> {
            int[] rows = table.getSelectedRows();
            if (rows.length == 0) {
                isRowsNotSelected();
            } else {
                isRowsSelected(rows);
            }
        });
        panelButtons.add(deleteButton);
    }

    private void isRowsNotSelected() {
        if (tableModel.getRowCount() > 1) {
            deleteLastRow();
        } else {
            createStartRow();
            table.repaint();
        }
        table.revalidate();
        tableModel.fireDataSheetChange();
    }

    private void createStartRow() {
        tableModel.getDataSheet().getData(0).setDate("");
        tableModel.getDataSheet().getData(0).setX(0);
        tableModel.getDataSheet().getData(0).setY(0);
    }

    private void deleteLastRow() {
        tableModel.setRowCount(tableModel.getRowCount() - 1);
        tableModel.getDataSheet().getNodes().remove(tableModel.getDataSheet().getNodes().size() - 1);
    }

    private void isRowsSelected(int[] rows) {
        int countRowsToDelete = 0;
        for (int row : rows) {
            tableModel.getDataSheet().getNodes().remove(row - countRowsToDelete);
            countRowsToDelete++;
        }

        if (tableModel.getDataSheet().getNodes().size() == 0) {
            tableModel.getDataSheet().getNodes().add(new DataNode());
        }

        table.getSelectionModel().clearSelection();
        tableModel.setRowCount(tableModel.getDataSheet().getNodes().size());
        table.revalidate();
        table.repaint();
        tableModel.fireDataSheetChange();
    }

    private void createScrollPane() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(table);
        table.setModel(tableModel);
    }

    public DataSheetTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DataSheetTableModel tableModel) {
        this.tableModel = tableModel;
        table.revalidate();
    }

    @Override
    public void revalidate() {
        if (table != null) table.revalidate();
    }

}
