import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TextEditorGUI extends JFrame{
    protected String fileName = "Untitled";
    private JTextPane textPane = new JTextPane();
    private boolean changed = false;
    private JMenuBar menuBar = new MenuBar(this);
    private JToolBar toolBar = new ToolBar(this);

    public TextEditorGUI(){
        setTitle(this.fileName);
        textPane.setFont(new Font("Arial", Font.PLAIN, 15));
        JScrollPane scroll = new JScrollPane(textPane);
        scroll.setPreferredSize(new Dimension(500, 500));
        getContentPane().add(scroll);
        setJMenuBar(menuBar);
        getContentPane().add(toolBar, BorderLayout.NORTH);
        addNewDocumentListener();

        WindowListener exitListner = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuBar.getMenu(0).getItem(4).doClick();
            }
        };
        addWindowListener(exitListner);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        pack();
        setVisible(true);
    }

    public void addNewDocumentListener(){
        this.textPane.getStyledDocument().addDocumentListener(new MyDocumentListener());
    }

    public JTextPane getTextPane(){
        return this.textPane;
    }

    public String getFileName(){
        return this.fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
        setTitle(this.fileName);
    }

    public Font getFont(){
        return this.textPane.getFont();
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
