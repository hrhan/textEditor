import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.*;

public class ToolBar extends JToolBar {
    private TextEditorGUI editor;

    public ToolBar(TextEditorGUI editor){
        this.editor = editor;
        createFontsMenu();
        createFontSizeMenu();
        createFontStyleMenu();
        createAlignmentMenu();
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
        JButton boldButton = new JButton(bold);

        Action italic = new StyledEditorKit.ItalicAction();
        italic.putValue(Action.NAME, "Italic");
        JButton italicButton = new JButton(italic);

        Action underline = new StyledEditorKit.UnderlineAction();
        underline.putValue(Action.NAME, "Underline");
        JButton underlineButton = new JButton(underline);

        JPanel stylePanel = new JPanel();
        stylePanel.add(boldButton);
        stylePanel.add(italicButton);
        stylePanel.add(underlineButton);
        stylePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Font Style"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
        add(stylePanel);

    }

    private void createAlignmentMenu(){
        Action left = new StyledEditorKit.AlignmentAction("Left", StyleConstants.ALIGN_LEFT);
        JButton leftButton = new JButton(left);

        Action center = new StyledEditorKit.AlignmentAction("Center", StyleConstants.ALIGN_CENTER);
        JButton centerButton = new JButton(center);

        Action right = new StyledEditorKit.AlignmentAction("Right", StyleConstants.ALIGN_RIGHT);
        JButton rightButton = new JButton(right);


        JPanel alignPanel = new JPanel();
        alignPanel.add(leftButton);
        alignPanel.add(centerButton);
        alignPanel.add(rightButton);
        alignPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Alignment"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
        add(alignPanel);
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
