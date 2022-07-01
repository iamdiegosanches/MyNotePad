package src;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TextEditor extends JFrame implements ActionListener {

    protected JTextArea textArea;
    JScrollPane scrollPane;
    protected UndoManager undoManager;

    JMenuBar menuBar;
    JMenu file;
    JMenu edit;
    JMenuItem newWindow;
    JMenuItem save;
    JMenuItem open;
    JMenuItem undo;

    ImageIcon logo = new ImageIcon("src/nota.png");

    public TextEditor() {
        createTextArea();
        createScrollPane();
        undoManager = new UndoManager();
        createMenuBar();
        createFrame();
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        file = new JMenu("File");

        edit = new JMenu("Edit");
        edit.setMnemonic(KeyEvent.VK_E);

        newWindow = new JMenuItem("New window");
        newWindow.addActionListener(this);

        save = new JMenuItem("Save...");
        save.addActionListener(this);

        open = new JMenuItem("Open...");

        undo = new JMenuItem("Undo      ");
        undo.addActionListener(this);
        undo.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        file.add(newWindow);
        file.add(save);
        file.add(open);

        edit.add(undo);

        menuBar.add(file);
        menuBar.add(edit);
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
        createTextAreaBorder();
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textArea.getDocument().addUndoableEditListener(
                e -> undoManager.addEdit(e.getEdit())
        );
    }

    private void createTextAreaBorder() {
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        textArea.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(3, 8, 10, 10)));
    }

    private void createFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("MyNotePad");
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setIconImage(logo.getImage());
        addToFrame();
        this.setVisible(true);
    }

    private void addToFrame() {
        this.setJMenuBar(menuBar);
        this.add(textArea);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newWindow) {
            this.dispose();
            new TextEditor();
        }

        if(e.getSource() == undo) {
            try {
                undoManager.undo();
            } catch (CannotRedoException cre) {
                cre.printStackTrace();
            }
        }

        saveFile(e);
    }

    private void saveFile(ActionEvent e) {
        if(e.getSource() == save) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showSaveDialog(null);

            if(response == JFileChooser.APPROVE_OPTION) {
                File file;

                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try (PrintWriter fileOut = new PrintWriter(file)) {
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}


