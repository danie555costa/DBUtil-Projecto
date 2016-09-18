/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.models;

/**
 * Quando o estado do item for alterado
 * <br/> Sera passado trez parametros sendo
 * <li>O item alterado</li>
 * <li>O antigo valor</li>
 * <li>O O novo valor</li>
 * @author Daniel
 * @param <I> AS type item
 * @param <V> AS Type Value
 */
public interface ItemStateChange <I, V>
{
    /**
     * Ao alterar o estao do item
     * @param item O item alterado 
     * @param oldState O antigo valor
     * @param newState O novo valor
     */
    public void stateChange(I item, V oldState, V newState);
}
