/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author Servidor
 */
public class DUShow 
{
    public static void warning(JComponent component, String message, String title)
    {
        JOptionPane.showMessageDialog(component, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    public static void information(JComponent component, String message, String title)
    {
        JOptionPane.showMessageDialog(component, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void error(JComponent component, String message, String title)
    {
        JOptionPane.showMessageDialog(component, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
