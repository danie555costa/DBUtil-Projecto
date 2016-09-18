/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.beans;

import st.jigasoft.dbutil.util.models.ItemView;
import st.jigasoft.dbutil.util.models.ListAdapter;
import java.awt.Component;
import java.util.ArrayList;

/**
 *
 * @author xdata
 */
public class BaseListAdapter implements ListAdapter
{
    ArrayList<ItemView> listItem;

    public BaseListAdapter() 
    {
        this.listItem = new ArrayList<>();
    }
    
    
    public void  addItem(ItemView item)
    {
        this.listItem.add(item);
    }
    
    
    @Override
    public Component getComponet(int position) 
    {
        return this.listItem.get(position).creatComoponent(position);
    }

    @Override
    public int getCountComponets()
    {
        return this.listItem.size();
    }

    @Override
    public Object getValue(int position) 
    {
        return this.listItem.get(position).getValue();
    }

    public void clear()
    {
        this.listItem.clear();
    }
    
    
    public void remove(ItemView itemView)
    {
        this.listItem.remove(itemView);
    }
    
    public ItemView getItemView(int index)
    {
        return this.listItem.get(index);
    }
}
