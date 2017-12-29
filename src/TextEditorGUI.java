import com.sun.glass.events.KeyEvent;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.UndoManager;
import java.awt.*;

public class TextEditorGUI extends JFrame{
    protected UndoManager undo = new UndoManager();
    protected String fileName = "Untitled";

    public TextEditorGUI(){
        setTitle(fileName);
        JTextPane textArea = new JTextPane();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(200, 200));
        getContentPane().add(scroll);
        setJMenuBar(createMenuBar());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private JMenuBar createMenuBar(){
        JMenuBar menu = new JMenuBar();
        menu.add(createFileMenu());
        menu.add(createEditMenu());
        return menu;
    }

    private JMenu createFileMenu(){
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_ALT);
        JMenuItem save = new JMenuItem("Save");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem quit = new JMenuItem("Quit");
        file.add(save);
        file.add(open);
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

    public static void main(String[] args) {
        new TextEditorGUI();
    }
}
