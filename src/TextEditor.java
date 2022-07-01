package src;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener {

    protected JTextArea textArea;
    protected JScrollPane scrollPane;
    protected UndoManager undoManager;

    private JMenuBar menuBar;
    JMenu file;
    JMenu edit;
    private JMenuItem newWindow;
    private JMenuItem save;
    JMenuItem open;
    private JMenuItem undo;

    ImageIcon logo = new ImageIcon("src/nota.png");

    public TextEditor() {
        createFrame();
        createTextArea();
        createScrollPane();
        undoManager = new UndoManager();
        createMenuBar();
        addToFrame();
        this.setVisible(true);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        edit = new JMenu("Edit");
        edit.setMnemonic(KeyEvent.VK_E);

        newWindow = new JMenuItem("New window");
        newWindow.addActionListener(this);
        newWindow.setAccelerator(KeyStroke.getKeyStroke('N',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        save = new JMenuItem("Save...");
        save.addActionListener(this);
        save.setAccelerator(KeyStroke.getKeyStroke('S',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        open = new JMenuItem("Open...");
        open.addActionListener(this);
        open.setAccelerator(KeyStroke.getKeyStroke('O',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        undo = new JMenuItem("Undo      ");
        undo.addActionListener(this);
        undo.setAccelerator(KeyStroke.getKeyStroke('Z',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        file.add(newWindow);
        file.add(save);
        file.add(open);

        edit.add(undo);

        menuBar.add(file);
        menuBar.add(edit);
    }

    private void createScrollPane() {
        scrollPane = new JScrollPane(this.textArea);
        scrollPane.setPreferredSize(new Dimension(450, 450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void createTextArea() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setTabSize(5);
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
    }

    private void addToFrame() {
        this.setJMenuBar(menuBar);
        this.add(scrollPane);
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

        if(e.getSource() == save) {
            saveFile();
        }

        if(e.getSource() == open) {
            openFile();
        }
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("D:/"));

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        int response = fileChooser.showOpenDialog(null);

        if(response == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

            try (Scanner fileIn = new Scanner(file)) {
                if (file.isFile()) {
                    while (fileIn.hasNextLine()) {
                        String line = fileIn.nextLine() + "\n";
                        textArea.append(line);
                    }
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }

    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("D:/"));

        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt", "text");
        fileChooser.setFileFilter(filter);

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
