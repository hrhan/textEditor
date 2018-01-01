import com.sun.glass.events.KeyEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MenuBar extends JMenuBar {
    private TextEditorGUI editor;
    private JFileChooser fc;

    private JMenu fileMenu;
    private JMenu editMenu;

    public MenuBar(TextEditorGUI editor){
        this.editor = editor;
        this.fc = new JFileChooser();
        this.add(createFileMenu());
        this.add(createEditMenu());
    }

    private JMenu createFileMenu(){
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_ALT);
        FileNameExtensionFilter textFilter = new FileNameExtensionFilter(".txt", "txt");
        fc.setFileFilter(textFilter);

        fileMenu.add(newMenu());
        fileMenu.add(saveMenu());
        fileMenu.add(saveAsMenu());
        fileMenu.add(openMenu());
        fileMenu.add(quitMenu());

        return fileMenu;
    }

    private JMenuItem newMenu(){
        JMenuItem newWindow = new JMenuItem("New");
        newWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TextEditorGUI();
            }
        });
        newWindow.setMnemonic(KeyEvent.VK_N);
        newWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        return newWindow;
    }

    private JMenuItem saveMenu(){
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editor.getFileName().equals("Untitled"))
                    saveFileAs();
                else
                    saveFile(fc.getSelectedFile());
            }
        });
        save.setMnemonic(KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        return save;
    }

    private JMenuItem saveAsMenu(){
        JMenuItem saveAs = new JMenuItem("Save As");
        saveAs.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                saveFileAs();
            }
        });
        return saveAs;
    }

    private JMenuItem openMenu(){
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                File selectedFile = null;
                if (fc.showOpenDialog(editor)==JFileChooser.APPROVE_OPTION) {
                    selectedFile = fc.getSelectedFile();
                    readFile(selectedFile);
                    editor.setChanged(false);
                }
            }
        });
        open.setMnemonic(KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        return open;
    }

    private JMenuItem quitMenu(){
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String[] options = {"Yes", "No", "Cancel"};
                if (editor.getChanged()){
                    int response = JOptionPane.showOptionDialog(editor, "Would you like to save the current file?",
                            "Save?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                            options, options[2]);
                    if(response == 0){
                        saveFileAs();
                    }
                    if(response==2)
                        return;
                }
                editor.dispose();
            }
        });
        quit.setMnemonic(KeyEvent.VK_Q);
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        return quit;
    }

    // Somehow get the save? message when quit menu is selected right after opening a file. Needs to be fixed.
    private void readFile(File file){
        try(BufferedReader input = new BufferedReader(new FileReader(file))){
            editor.getTextPane().read(input, null);
            editor.addNewDocumentListener();
            editor.setFileName(file.getName());
            editor.setChanged(false);
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(this.editor, "Unable to find " + file.getName());
        }
    }

    private void saveFile(File thisFile){
        String fileName = thisFile.getAbsolutePath();
        if (!fileName.endsWith(".txt")){
            fileName += ".txt";
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            editor.getTextPane().write(writer);
            editor.setFileName(thisFile.getName());
            editor.setChanged(false);
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(this.editor, "Unable to save the file");
        }
    }

    private void saveFileAs(){
        if(fc.showSaveDialog(this.editor) == JFileChooser.APPROVE_OPTION)
            saveFile(fc.getSelectedFile());
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
        return edit;
    }
}
