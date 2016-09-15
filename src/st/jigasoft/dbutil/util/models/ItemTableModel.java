/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.models;

/**
 *
 * @author Servidor
 */
public interface ItemTableModel
{
    /**
     * Deveolver uma lista dos valores que irao preencher uma linha de tabela
     * @return 
     */
    public Object [] values ();

    /**
     * O codigo da linha
     * @return 
     */
    public Object key();
    
    /**
     * Se o model esta a ser apresentado na tabela
     * @return 
     */
    public boolean isVisible();
    
    /**
     *
     * @param visiblie
     */
    void setVisible (boolean visiblie);
    
    
    default public void valueSetAt(Object value, String column, int columnIndex){};
    
}
