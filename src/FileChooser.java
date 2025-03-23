import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FileChooser {
    private JPanel pnMain;
    private JButton openFileButton;
    private JTextArea taVypis;
    final JScrollPane scrollPane;
    private File currentFile;

    public FileChooser() {
        pnMain = new JPanel();
        taVypis = new JTextArea();
        scrollPane = new JScrollPane(taVypis);
        pnMain.setLayout(new BorderLayout());
        pnMain.add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem menuItemOpen = new JMenuItem("Open");
        JMenuItem menuItemSave = new JMenuItem("Save");
        JMenuItem menuItemSaveAs = new JMenuItem("Save as...");

        menu.add(menuItemOpen);
        menu.add(menuItemSave);
        menu.add(menuItemSaveAs);
        menuBar.add(menu);

        JFrame frame = new JFrame("FileChooser");
        frame.setJMenuBar(menuBar);
        frame.setContentPane(pnMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);

        menuItemOpen.addActionListener(e -> openFile());
        menuItemSave.addActionListener(e -> saveFile(currentFile));
        menuItemSaveAs.addActionListener(e -> saveFileAs());
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(pnMain);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            System.out.println("Vybrán soubor: " + currentFile.getAbsolutePath());
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                taVypis.read(reader, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Soubor nebyl vybrán");
        }
    }

    private void saveFile(File file) {
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                taVypis.write(writer);
                System.out.println("Uložen soubor: " + file.getAbsolutePath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void saveFileAs() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(pnMain);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            saveFile(currentFile);
        } else {
            System.out.println("Soubor neuložen");
        }
    }

    public static void main(String[] args) {
        new FileChooser();
    }
}