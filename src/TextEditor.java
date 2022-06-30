package src;

import javax.swing.*;
import java.awt.*;

public class TextEditor extends JFrame {

    JTextArea textArea;
    ImageIcon logo = new ImageIcon("src/images/nota.png");

    public TextEditor() {
        createTextArea();
        createFrame();
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
        this.add(textArea);
    }
}
