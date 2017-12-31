import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class TextEditorGUI extends JFrame{
    protected String fileName = "Untitled";
    private JTextPane textPane = new JTextPane();
    private boolean changed = false;

    public TextEditorGUI(){
        setTitle(this.fileName);
        textPane.setFont(new Font("Monospaced", Font.PLAIN, 20));
        JScrollPane scroll = new JScrollPane(textPane);
        scroll.setPreferredSize(new Dimension(500, 500));
        getContentPane().add(scroll);
        setJMenuBar(new MenuBar(this));
        addNewDocumentListener();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void addNewDocumentListener(){
        this.textPane.getStyledDocument().addDocumentListener(new MyDocumentListener());
    }

    public JTextPane getTextPane(){
        return this.textPane;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
        setTitle(this.fileName);
    }

    public boolean getChanged(){
        return this.changed;
    }

    public void setChanged(boolean changed){
        this.changed = changed;
    }

    public static void main(String[] args) {
        new TextEditorGUI();
    }

    private class MyDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateStatus();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateStatus();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateStatus();
        }

        public void updateStatus(){
            changed = true;
        }
    }
}
