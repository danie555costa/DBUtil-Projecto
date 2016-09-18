/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.models;

/**
 *
 * @author Daniel
 * @param <V> O typo do valor que sera alterado
 */
public interface ProprietValueChange  <V>
{
    /**
     * Quando o valor for alterado
     * @param oldState
     * @param newState 
     */
    public void valueChange(V oldState, V newState);
}
