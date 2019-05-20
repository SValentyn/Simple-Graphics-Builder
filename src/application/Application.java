package application;

import analysisXML.SAXService;
import analysisXML.XMLWriter;
import beans.DataNode;
import beans.DataSheet;
import beans.DataSheetGraph;
import beans.DataSheetTable;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.File;

/**
 * A program application is a program in which a tabular presentation of data is displayed on a graph.
 * The program has the ability to process XML files, that is, open and save data from a table to a file.
 * The graphical interface of the application is developed using the Swing library.
 *
 * @author Syniuk Valentyn
 */
public class Application extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel panelContent;
    private JPanel panelControlButtons;
    private DataSheet dataSheet;           // data
    private DataSheetTable dataSheetTable; // table
    private DataSheetGraph dataSheetGraph; // graph

    private final JFileChooser fileChooser = new JFileChooser();

    public static void main(String[] args) {
        Application application = new Application();
        application.setVisible(true);
        application.setResizable(false);
    }

    private Application() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setBounds(100, 100, 600, 400);
        setTitle("GraphBuilder");

        createPanelContent();
        createDataSheet();
        createDataSheetGraph();
        createDataSheetTable();
        createPanelControlButtons();
        createControlButtons();
    }

    private void createPanelContent() {
        panelContent = new JPanel();
        panelContent.setLayout(new BorderLayout());
        setContentPane(panelContent);
    }

    private void createDataSheet() {
        dataSheet = new DataSheet();
        dataSheet.addData(new DataNode());
    }

    private void createDataSheetGraph() {
        dataSheetGraph = new DataSheetGraph();
        dataSheetGraph.setLayout(new FlowLayout());
        dataSheetGraph.setPreferredSize(new Dimension(300, 300));
        dataSheetGraph.setDataSheet(dataSheet);
        panelContent.add(dataSheetGraph, BorderLayout.EAST);
    }

    /* Creating a table with an initial value (0; 0) */
    private void createDataSheetTable() {
        dataSheetTable = new DataSheetTable();
        dataSheetTable.getTableModel().setDataSheet(dataSheet);
        dataSheetTable.setPreferredSize(new Dimension(200, 300));
        dataSheetTable.getTableModel().addDataSheetChangeListener(event -> {
            dataSheetGraph.revalidate();
            dataSheetGraph.repaint();
        });
        panelContent.add(dataSheetTable, BorderLayout.CENTER);
    }

    private void createPanelControlButtons() {
        panelControlButtons = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panelControlButtons.getLayout();
        flowLayout.setHgap(20);
        panelContent.add(panelControlButtons, BorderLayout.SOUTH);
        fileChooser.setCurrentDirectory(new File("src/XML/"));
    }

    private void createControlButtons() {
        createConnectCheckBox();
        createOpenButton();
        createSaveButton();
        createClearButton();
        createExitButton();
    }

    private void createConnectCheckBox() {
        JCheckBox connectCheckBox = new JCheckBox("Connect");
        connectCheckBox.addActionListener(event -> dataSheetGraph.setConnected(connectCheckBox.isSelected()));
        panelControlButtons.add(connectCheckBox);
    }

    private void createOpenButton() {
        JButton openButton = new JButton("Open");
        openButton.addActionListener(event -> {
            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
                String fileName = fileChooser.getSelectedFile().getPath();
                dataSheet = SAXService.parseWithDTD(fileName);
                dataSheetTable.getTableModel().setDataSheet(dataSheet);
                dataSheetTable.revalidate();
                dataSheetGraph.setDataSheet(dataSheet);
            }
        });
        panelControlButtons.add(openButton);
    }

    private void createSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(event -> {
            if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
                String fileName = fileChooser.getSelectedFile().getPath();
                try {
                    XMLWriter.recordDataToFile(dataSheet, fileName);
                } catch (TransformerException | ParserConfigurationException ignored) {
                }
                JOptionPane.showMessageDialog(null, "File " + fileName.trim() + " saved!", "Result saved", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panelControlButtons.add(saveButton);
    }

    private void createClearButton() {
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(event -> {
            dataSheet = new DataSheet();
            dataSheet.addData(new DataNode());
            dataSheetTable.getTableModel().setDataSheet(dataSheet);
            dataSheetTable.revalidate();
            dataSheetGraph.setDataSheet(dataSheet);
        });
        panelControlButtons.add(clearButton);
    }

    private void createExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(event -> dispose());
        panelControlButtons.add(exitButton);
    }

}
