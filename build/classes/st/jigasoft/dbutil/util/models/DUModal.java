/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.models;

import java.util.function.Consumer;

/**
 *
 * @author Servidor
 */
public interface DUModal<T>
{
    public void close();
    
    public void open();
    
    public void setPreOpen(Consumer<T> onPreOpen);
    
    public void setPosOpen(Consumer<T> onPosOpen);
    
    public void setPreClose(Consumer<T> onPreClose);
    
    public void setPosClose(Consumer<T> onPosClose);    
}
