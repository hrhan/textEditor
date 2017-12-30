import javax.swing.*;
import java.awt.*;

public class TextEditorGUI extends JFrame{
    protected String fileName = "Untitled";
    private JTextPane textArea;

    public TextEditorGUI(){
        setTitle(fileName);
        textArea = new JTextPane();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(500, 500));
        getContentPane().add(scroll);
        setJMenuBar(new MenuBar(this));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public JTextPane getTextArea(){
        return this.textArea;
    }

    public static void main(String[] args) {
        new TextEditorGUI();
    }
}
