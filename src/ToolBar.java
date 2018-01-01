import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ToolBar extends JToolBar {
    private TextEditorGUI editor;

    public ToolBar(TextEditorGUI editor){
        this.editor = editor;
        createFontsMenu();
        addSeparator();
        createFontSizeMenu();
        addSeparator();
        createFontStyleMenu();
    }

    // somehow fontList.setSelectedItem() is not working.
    private void createFontsMenu(){
        Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        JComboBox<Font> fontList = new JComboBox<>(fonts);
        fontList.setRenderer(new FontListRenderer());
        fontList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Font currentFont = editor.getFont();
                Font selected;
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selected = (Font) e.getItem();
                    editor.getTextPane().setFont(new Font(selected.getName(), currentFont.getStyle(), currentFont.getSize()));
                }
            }
        });
        add(fontList);
    }

    // Find out why editor.getTextPane().setFont(currentFont.deriveFont()) is not working as it should
    private void createFontSizeMenu(){
        SpinnerModel fontSize = new SpinnerNumberModel(15, 0, 50, 1);
        JSpinner fontSizeSpinner = new JSpinner(fontSize);
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Font currentFont = editor.getFont();
                editor.getTextPane().setFont(new Font(currentFont.getName(), currentFont.getStyle(),
                        (int)fontSizeSpinner.getValue()));
            }
        });
        add(fontSizeSpinner);
    }

    private void createFontStyleMenu(){
        Action bold = new StyledEditorKit.BoldAction();
        bold.putValue(Action.NAME, "Bold");
        add(bold);

        Action italic = new StyledEditorKit.ItalicAction();
        italic.putValue(Action.NAME, "Italic");
        add(italic);

        Action underline = new StyledEditorKit.UnderlineAction();
        underline.putValue(Action.NAME, "Underline");
        add(underline);
    }

    private class FontListRenderer extends DefaultListCellRenderer{
        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus){
            String fontName = "";
            if (value instanceof Font){
                fontName = ((Font)value).getName();
                value = fontName;
            }
            Font labelFont = new Font(fontName, Font.PLAIN, 20);
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setFont(labelFont);
            return label;
        }
    }

}
