import javax.swing.*;

public class TextEditorGUI extends JFrame{
    private JTextArea textArea;

    public TextEditorGUI(){
        textArea = new JTextArea(50, 50);
        setJMenuBar(createMenuBar());
        getContentPane().add(textArea);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private JMenuBar createMenuBar(){
        JMenuBar menu = new JMenuBar();
        menu.add(createFileMenu());
        menu.add(createEditMenu());
        return menu;
    }

    private JMenu createFileMenu(){
        JMenu file = new JMenu("File");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem open = new JMenuItem("Open");
        file.add(save);
        file.add(open);
        return file;
    }

    private JMenu createEditMenu(){
        JMenu edit = new JMenu("Edit");
        return edit;
    }

    public static void createGUI(){
        TextEditorGUI frame = new TextEditorGUI();
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createGUI();
            }
        });
    }
}
