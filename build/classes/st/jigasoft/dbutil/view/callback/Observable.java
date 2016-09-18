/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.view.callback;

/**
 *
 * @author xdata
 */
public interface Observable <T> {
    public void accept(T t);
}
