package ru.yandex.money.def;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by def on 13.07.14.
 */
public class Gui extends JFrame{
    private JMenuBar menuBar;
    private JMenu menuModel;
    private JMenuItem generateCases;
    private JPanel mainPanel;
    private JTextArea textArea;

    public Gui(ActionListener listener, String text) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultLookAndFeelDecorated(false);
        this.setPreferredSize(new Dimension(700, 500));
        this.setTitle("Test case generator");
        mainPanel = new JPanel();


        menuBar = new JMenuBar();
        menuModel = new JMenu("Model");
        menuModel.setMnemonic(KeyEvent.VK_M);
        menuModel.getAccessibleContext().setAccessibleDescription("Load teams");
        menuBar.add(menuModel);
        generateCases = new JMenuItem("Create cases", KeyEvent.VK_C);
        menuModel.add(generateCases);
        textArea = new JTextArea(text);
        mainPanel.add(textArea);
        generateCases.addActionListener(listener);
        this.setJMenuBar(menuBar);

        this.setContentPane(mainPanel);
        mainPanel.setLayout(new GridLayout(1, 2));

        this.pack();
        this.setVisible(true);

    }

    public String getModel() {
        return textArea.getText();
    }
}
