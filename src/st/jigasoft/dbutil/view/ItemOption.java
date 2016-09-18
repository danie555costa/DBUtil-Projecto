/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.view;

/**
 *
 * @author Servidor
 */
public interface ItemOption <T>
{
    boolean isSelected();
    
    public void setValue(String name, T value);
    
    public void setObject(String name,Object value);
    
    public String getKey();
    
    public String getValue();
    
    public T getValue(String name);
    
    public Object getObject(String name);
}
