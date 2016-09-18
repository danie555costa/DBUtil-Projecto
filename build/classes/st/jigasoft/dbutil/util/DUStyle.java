/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.dnd.DropTarget;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.border.Border;

/**
 *
 * @author Servidor
 */
public class DUStyle
{
    public static void applyComponet (JComponent fontStyle, JComponent reciviStyle)
    {
        reciviStyle.setAlignmentX(fontStyle.getAlignmentX());
        reciviStyle.setAlignmentY(fontStyle.getAlignmentY());
        reciviStyle.setBackground(fontStyle.getBackground());
        reciviStyle.setBorder(fontStyle.getBorder());
        reciviStyle.setBounds(fontStyle.getBounds());
        reciviStyle.setComponentOrientation(fontStyle.getComponentOrientation());
        // component.setComponentPopupMenu(style.getComponentPopupMenu());
        //component.setComponentZOrder(style., WIDTH);
        reciviStyle.setCursor(fontStyle.getCursor());
        reciviStyle.setDebugGraphicsOptions(fontStyle.getDebugGraphicsOptions());
        reciviStyle.setDoubleBuffered(fontStyle.isDoubleBuffered());
        reciviStyle.setDropTarget(fontStyle.getDropTarget());
        reciviStyle.setEnabled(fontStyle.isEnabled());
        reciviStyle.setFocusCycleRoot(fontStyle.isFocusCycleRoot());
        //component.setFocusTraversalKeys(style.getF, null);
        reciviStyle.setFocusTraversalKeysEnabled(fontStyle.getFocusTraversalKeysEnabled());
        reciviStyle.setFocusTraversalPolicy(fontStyle.getFocusTraversalPolicy());
        reciviStyle.setFocusTraversalPolicyProvider(fontStyle.isFocusTraversalPolicyProvider());
        reciviStyle.setFocusable(fontStyle.isFocusable());
        reciviStyle.setFont(fontStyle.getFont());
        reciviStyle.setForeground(fontStyle.getForeground());
        reciviStyle.setLayout(fontStyle.getLayout());
        reciviStyle.setLocale(fontStyle.getLocale());
        //component.setLocation(style.getLocation());
        reciviStyle.setMaximumSize(fontStyle.getMaximumSize());
        reciviStyle.setMinimumSize(fontStyle.getMinimumSize());
        reciviStyle.setOpaque(fontStyle.isOpaque());
        reciviStyle.setPreferredSize(fontStyle.getPreferredSize());
        reciviStyle.setRequestFocusEnabled(fontStyle.isRequestFocusEnabled());
        reciviStyle.setSize(fontStyle.getSize());
        reciviStyle.setToolTipText(fontStyle.getToolTipText());
        reciviStyle.setTransferHandler(fontStyle.getTransferHandler());
        reciviStyle.setVisible(fontStyle.isVisible());
    }
    
    public static void applyComponet (DUStyle dbStyle, JComponent reciviStyle)
    {
        if(dbStyle.alterAlignmentX) reciviStyle.setAlignmentX(dbStyle.alignmentX);
        reciviStyle.setAlignmentY(dbStyle.alignmentY);
        reciviStyle.setBackground(dbStyle.background);
        reciviStyle.setBorder(dbStyle.border);
        reciviStyle.setBounds(dbStyle.bounds);
        reciviStyle.setComponentOrientation(dbStyle.componentOrientation);
        // component.setComponentPopupMenu(style.getComponentPopupMenu);
        //component.setComponentZOrder(style., WIDTH);
        reciviStyle.setCursor(dbStyle.cursor);
        reciviStyle.setDebugGraphicsOptions(dbStyle.debugGraphicsOptions);
        reciviStyle.setDoubleBuffered(dbStyle.doubleBuffered);
        reciviStyle.setDropTarget(dbStyle.dropTarget);
        reciviStyle.setEnabled(dbStyle.enabled);
        reciviStyle.setFocusCycleRoot(dbStyle.focusCycleRoot);
        //component.setFocusTraversalKeys(style.getF, null);
        reciviStyle.setFocusTraversalKeysEnabled(dbStyle.focusTraversalKeysEnabled);
        reciviStyle.setFocusTraversalPolicy(dbStyle.focusTraversalPolicy);
        reciviStyle.setFocusTraversalPolicyProvider(dbStyle.focusTraversalPolicyProvider);
        reciviStyle.setFocusable(dbStyle.focusable);
        reciviStyle.setFont(dbStyle.font);
        reciviStyle.setForeground(dbStyle.foreground);
        reciviStyle.setLayout(dbStyle.layout);
        reciviStyle.setLocale(dbStyle.locale);
        //component.setLocation(style.getLocation);
        reciviStyle.setMaximumSize(dbStyle.maximumSize);
        reciviStyle.setMinimumSize(dbStyle.minimumSize);
        reciviStyle.setOpaque(dbStyle.opaque);
        reciviStyle.setPreferredSize(dbStyle.preferredSize);
        reciviStyle.setRequestFocusEnabled(dbStyle.requestFocusEnabled);
        reciviStyle.setSize(dbStyle.size);
        reciviStyle.setToolTipText(dbStyle.toolTipText);
        reciviStyle.setTransferHandler(dbStyle.transferHandler);
        reciviStyle.setVisible(dbStyle.visible);
    }
    
    public float alignmentY;
    public float alignmentX;
    public Color background;
    public Border border;
    public Rectangle bounds;
    public ComponentOrientation componentOrientation;
    public Cursor cursor;
    public int debugGraphicsOptions;
    public boolean doubleBuffered;
    public DropTarget dropTarget;
    public boolean enabled;
    public boolean focusCycleRoot;
    public boolean focusTraversalKeysEnabled;
    public FocusTraversalPolicy focusTraversalPolicy;
    public boolean focusTraversalPolicyProvider;
    public boolean focusable;
    public Font font;
    public Color foreground;
    public Locale locale;
    public LayoutManager layout;
    public Dimension minimumSize;
    public Dimension maximumSize;
    public boolean opaque;
    public Dimension preferredSize;
    public boolean requestFocusEnabled;
    public Dimension size;
    public String toolTipText;
    public TransferHandler transferHandler;
    public boolean visible;
    
    
    private boolean alterAlignmentY;
    private boolean alterAlignmentX;
    private boolean alterBackground;
    private boolean alterBorder;
    private boolean alterBounds;
    private boolean alterComponentOrientation;
    private boolean alterCursor;
    private boolean alteDebugGraphicsOptions;
    private boolean alterDoubleBuffered;
    private boolean alterDropTarget;
    private boolean alterEnabled;
    private boolean alterFocusCycleRoot;
    private boolean alterFocusTraversalKeysEnabled;
    private boolean alterFocusTraversalPolicy;
    private boolean alterFocusTraversalPolicyProvider;
    private boolean alterFocusable;
    private boolean alterFont;
    private boolean alterForeground;
    private boolean alterLocale;
    private boolean alterLayout;
    private boolean alterMinimumSize;
    private boolean alterMaximumSize;
    private boolean alterOpaque;
    private boolean alterPreferredSize;
    private boolean alterRequestFocusEnabled;
    private boolean alterSize;
    private boolean alterToolTipText;
    private boolean alterTransferHandler;
    private boolean alterVisible;
}
