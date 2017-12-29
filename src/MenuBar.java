import com.sun.glass.events.KeyEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyledEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuBar extends JMenuBar {
    private JFileChooser fc = new JFileChooser();

    public MenuBar(){
        add(createFileMenu());
        add(createEditMenu());
    }

    private JMenu createFileMenu(){
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_ALT);
        FileNameExtensionFilter textFilter = new FileNameExtensionFilter(".txt", "txt");
        fc.setFileFilter(textFilter);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fc.showSaveDialog(file) == JFileChooser.APPROVE_OPTION) {


                }
            }
        });
        save.setMnemonic(KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                File selectedfile = null;
                if (fc.showOpenDialog(file)==JFileChooser.APPROVE_OPTION) {
                    selectedfile = fc.getSelectedFile();

                }

            }
        });
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        JMenuItem quit = new JMenuItem("Quit");
        file.add(save);
        file.add(open);
        file.add(quit);
        return file;
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
