import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TextEditorGUI extends JFrame{
    protected String fileName = "Untitled";

    public TextEditorGUI(){
        setTitle(fileName);
        JTextPane textArea = new JTextPane();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(500, 500));
        getContentPane().add(scroll);
        setJMenuBar(new MenuBar());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
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
