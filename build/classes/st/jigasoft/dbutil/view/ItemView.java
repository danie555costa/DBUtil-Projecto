/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.view;

import java.awt.Component;

/**
 *
 * @author xdata
 */
public interface ItemView {
    
   
    void bind(ItemDataSet dataSet);
    
    Component getComponent();
    
}
