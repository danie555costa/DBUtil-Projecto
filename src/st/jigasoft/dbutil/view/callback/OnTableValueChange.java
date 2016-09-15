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
public interface OnTableValueChange {
    void onValueChange(Object newValue, int row, String columnName, int columnIndex);
}
