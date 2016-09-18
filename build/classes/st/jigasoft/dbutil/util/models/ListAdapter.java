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
public interface ListAdapter 
{
    public  Component getComponet(int position);
    
    public int getCountComponets();
    
    public Object getValue(int position);
}
