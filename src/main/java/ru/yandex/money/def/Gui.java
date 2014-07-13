package ru.yandex.money.def;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Created by def on 13.07.14.
 */
public class Gui extends JFrame{
    private JMenuBar menuBar;
    private JMenu menuModel;
    private JMenuItem generateCases;
    private JPanel mainPanel;
    private JTextArea textArea;
    private JFileChooser dialog;

    public Gui(ActionListener listener, String text) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultLookAndFeelDecorated(false);
        this.setPreferredSize(new Dimension(700, 500));
        this.setTitle("Test case generator");
        mainPanel = new JPanel();


        menuBar = new JMenuBar();
        menuModel = new JMenu("Model");
        menuModel.setMnemonic(KeyEvent.VK_M);
        menuModel.getAccessibleContext().setAccessibleDescription("Model");
        menuBar.add(menuModel);
        generateCases = new JMenuItem("Export to freeMind", KeyEvent.VK_E);
        menuModel.add(generateCases);
        textArea = new JTextArea(text);
        mainPanel.add(textArea);
        generateCases.addActionListener(listener);
        this.setJMenuBar(menuBar);

        this.setContentPane(mainPanel);
        mainPanel.setLayout(new GridLayout(1, 1));

        this.pack();
        this.setVisible(true);




    }

    public String getModel() {
        return textArea.getText();
    }

    public File getSrcFile(String filename){
        dialog = new JFileChooser();
        dialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        dialog.setApproveButtonText("Сохранить");
        dialog.setDialogTitle("Куда сохранить");
        dialog.setSelectedFile(new File(filename));
        dialog.setDialogType(JFileChooser.SAVE_DIALOG);
        dialog.setMultiSelectionEnabled(false);
        int res = dialog.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            return dialog.getSelectedFile();
        }
        return null;
    }
}
