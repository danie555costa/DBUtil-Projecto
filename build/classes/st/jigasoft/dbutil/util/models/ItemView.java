/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.models;

import java.awt.Component;

/**
 *
 * @author xdata
 */
public interface ItemView 
{
    public Component creatComoponent(int position);

    public Object getValue();
}
