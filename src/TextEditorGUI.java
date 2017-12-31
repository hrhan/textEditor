import javax.swing.*;
import java.awt.*;

public class TextEditorGUI extends JFrame{
    protected String fileName = "Untitled";
    private JTextPane textPane;

    public TextEditorGUI(){
        setTitle(fileName);
        this.textPane = new JTextPane();
        textPane.setFont(new Font("Monospaced", Font.PLAIN, 20));
        JScrollPane scroll = new JScrollPane(textPane);
        scroll.setPreferredSize(new Dimension(500, 500));
        getContentPane().add(scroll);
        setJMenuBar(new MenuBar(this));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
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

    public static void main(String[] args) {
        new TextEditorGUI();
    }
}
