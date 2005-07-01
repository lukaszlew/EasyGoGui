//----------------------------------------------------------------------------
// $Id$
// $Source$
//----------------------------------------------------------------------------

package net.sf.gogui.gui;

import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

//----------------------------------------------------------------------------

/** Implementation of GuiTextPane using a JTextPane. */
class GuiJTextPane
    extends JTextPane
    implements GuiTextPane
{
    public GuiJTextPane()
    {
    }

    public void addStyle(String name, Color foreground, Color background)
    {
        StyledDocument doc = getStyledDocument();
        StyleContext context = StyleContext.getDefaultStyleContext();
        m_defaultStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
        Style style = doc.addStyle(name, m_defaultStyle);
        StyleConstants.setForeground(style, foreground);
        StyleConstants.setBackground(style, background);
    }

    public JComponent getComponent()
    {
        return this;
    }

    public String getSelection()
    {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        StyledDocument doc = getStyledDocument();
        try
        {
            return doc.getText(start, end - start);
        }
        catch (BadLocationException e)
        {
            assert(false);
            return "";
        }   
    }

    public void markAll(Pattern pattern)
    {
        StyledDocument doc = getStyledDocument();
        try
        {
            CharSequence text = doc.getText(0, doc.getLength());
            Matcher matcher = pattern.matcher(text);
            boolean firstMatch = true;
            while (matcher.find())
            {
                int start = matcher.start();
                int end = matcher.end();
                Style style = doc.getStyle("marked");
                if (firstMatch)
                {
                    doc.setCharacterAttributes(0, doc.getLength(),
                                               m_defaultStyle, true);
                    setCaretPosition(start);
                    firstMatch = false;
                }
                doc.setCharacterAttributes(start, end - start, style, true);
            }
        }
        catch (BadLocationException e)
        {
            assert(false);
        }
    }

    public void setTabFocusKeys()
    {
        Set forwardSet  = new HashSet();
        forwardSet.add(KeyStroke.getKeyStroke("TAB"));
        int forwardId = KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS;
        setFocusTraversalKeys(forwardId, forwardSet);
        Set backwardSet  = new HashSet();
        backwardSet.add(KeyStroke.getKeyStroke("shift TAB"));
        int backwardId = KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS;
        setFocusTraversalKeys(backwardId, backwardSet);
    }

    public void setText(String text)
    {
        StyledDocument doc = getStyledDocument();
        doc.setCharacterAttributes(0, doc.getLength(), m_defaultStyle, true);
        super.setText(text);
        setCaretPosition(0);
    }

    /** Serial version to suppress compiler warning.
        Contains a marker comment for serialver.sourceforge.net
    */
    private static final long serialVersionUID = 0L; // SUID

    private Style m_defaultStyle;
}

//----------------------------------------------------------------------------
