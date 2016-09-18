/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.models;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Servidor
 */
public abstract class GeralAbas extends JPanel implements Abas
{
    public static boolean LOAD;
    private AbaState state;
    public  GeralAbas ()
    {
        
    }
    @Override
    public void setVisible(boolean vist)
    {
        if(LOAD && vist)
        {
            this.loadData();
        }
        
        super.setVisible(vist);
        
        if(LOAD && !vist)
        {
            this.free();
        }
    }

    /**
     * Verificar se esta carregado
     * @return 
     */
    public boolean isLoad() 
    {
        return super.isVisible() && LOAD;
    }

    public AbaState getState()
    {
        return state;
    }
    
    
    public static int freeSubAbas(JTabbedPane subAbas) 
    {
        int index  = subAbas.getSelectedIndex();
//        subAbas.getSelectedComponent().setVisible(false);
        return index;
    }
    
    public static void loadSubAbas(JTabbedPane subAbas, int lastIndex) 
    {
        if(lastIndex == 0)
        {
            subAbas.setSelectedIndex(lastIndex);
            subAbas.getSelectedComponent().setVisible(false);
        }
    }
    
    
    
    
    public static enum AbaState
    {
        CREATED,
        ACTIVE,
        DESATIVE
    }
}
