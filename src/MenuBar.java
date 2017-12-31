import com.sun.glass.events.KeyEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyledEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MenuBar extends JMenuBar {
    private TextEditorGUI teGUI;
    private JFileChooser fc;

    private JMenu fileMenu;
    private JMenu editMenu;

    public MenuBar(TextEditorGUI teGUI){
        this.teGUI = teGUI;
        this.fc = new JFileChooser();
        this.add(createFileMenu());
        this.add(createEditMenu());
    }

    private JMenu createFileMenu(){
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_ALT);
        FileNameExtensionFilter textFilter = new FileNameExtensionFilter(".txt", "txt");
        fc.setFileFilter(textFilter);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fc.showSaveDialog(fileMenu) == JFileChooser.APPROVE_OPTION) {
                    saveFile(fc.getSelectedFile());
                }
            }
        });
        save.setMnemonic(KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                File selectedFile = null;
                if (fc.showOpenDialog(fileMenu)==JFileChooser.APPROVE_OPTION) {
                    selectedFile = fc.getSelectedFile();
                    readFile(selectedFile);
                }
            }
        });
        open.setMnemonic(KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String[] options = {"Yes", "No", "Cancel"};
                if (teGUI.getChanged()){
                    int response = JOptionPane.showOptionDialog(fileMenu, "Would you like to save the current file?",
                            "Save?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                            options, options[2]);
                    if(response == 0){
                        if(fc.showSaveDialog(fileMenu)==JFileChooser.APPROVE_OPTION)
                            saveFile(fc.getSelectedFile());
                    }
                    if(response==2)
                        return;
                }
                teGUI.dispose();
            }
        });

        fileMenu.add(save);
        fileMenu.add(open);
        fileMenu.add(quit);
        return fileMenu;
    }

    // Somehow get the save? message when quit menu is selected right after opening a file. Needs to be fixed.
    private void readFile(File file){
        try(BufferedReader input = new BufferedReader(new FileReader(file))){
            teGUI.getTextPane().read(input, null);
            teGUI.addNewDocumentListener();
            teGUI.setFileName(file.getName());
            teGUI.setChanged(false);
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(this.fileMenu, "Unable to find " + file.getName());
        }
    }

    private void saveFile(File thisFile){
        String fileName = thisFile.getAbsolutePath();
        if (!fileName.endsWith(".txt")){
            fileName += ".txt";
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            teGUI.getTextPane().write(writer);
            teGUI.setFileName(thisFile.getName());
            teGUI.setChanged(false);
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(this.fileMenu, "Unable to save the file");
        }
    }

    private JMenu createEditMenu(){
        JMenu edit = new JMenu("Edit");
        Action cut = new DefaultEditorKit.CutAction();
        cut.putValue(Action.NAME, "Cut");
        edit.add(cut);
        Action copy = new DefaultEditorKit.CopyAction();
        copy.putValue(Action.NAME, "Copy");
        edit.add(copy);
        Action paste = new DefaultEditorKit.PasteAction();
        paste.putValue(Action.NAME, "Paste");
        edit.add(paste);
        JMenu font = new JMenu("Font");
        Action bold = new StyledEditorKit.BoldAction();
        bold.putValue(Action.NAME, "Bold");
        font.add(bold);
        edit.add(font);
        return edit;
    }
}
