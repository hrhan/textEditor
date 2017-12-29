import com.sun.glass.events.KeyEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TextEditorGUI extends JFrame{
    protected String fileName = "Untitled";
    private JFileChooser fc = new JFileChooser();

    public TextEditorGUI(){
        setTitle(fileName);
        JTextPane textArea = new JTextPane();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(500, 500));
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
                FileNameExtensionFilter textFilter = new FileNameExtensionFilter(".txt", "txt", "text");
                fc.setFileFilter(textFilter);
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

    public static void main(String[] args) {
        new TextEditorGUI();
    }

    private class OpenL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            File file = null;
            if (chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
                file = chooser.getSelectedFile();
        }
    }
}
