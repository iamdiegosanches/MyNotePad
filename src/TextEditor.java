package src;

import javax.swing.*;
import java.awt.*;

public class TextEditor extends JFrame {

    private JTextArea textArea;
    JScrollPane scrollPane;

    JMenuBar menuBar;
    JMenu file;
    JMenuItem newWindow;
    JMenuItem save;

    ImageIcon logo = new ImageIcon("src/nota.png");

    public TextEditor() {
        createTextArea();
        createScrollPane();
        createMenuBar();
        createFrame();
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        file = new JMenu("File");

        newWindow = new JMenuItem("New window");
        save = new JMenuItem("Save File");

        file.add(newWindow);
        file.add(save);
        menuBar.add(file);
    }

    private void createScrollPane() {
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void createTextArea() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    private void createFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("MyNotePad");
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setIconImage(logo.getImage());
        addFrame();
        this.setVisible(true);
    }

    private void addFrame() {
        this.setJMenuBar(menuBar);
        this.add(textArea);
    }
}
