/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.view;

import java.awt.Component;
import java.awt.Label;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author xdata
 */
public class ListViewManager extends AbstractListModel<ItemDataSet> implements ListCellRenderer<ItemDataSet>{
    
    private final List< ItemDataSet > listItemDataSets;
    private final List< ItemViewList > listComponents;

    public ListViewManager() {
        this.listItemDataSets = new ArrayList<>();
        this.listComponents = new  ArrayList<>();
    }
    

    @Override
    public int getSize() {
        return this.listItemDataSets.size();
    }

    @Override
    public ItemDataSet getElementAt(int index) {
        return this.listItemDataSets.get(index);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ItemDataSet> list, ItemDataSet value, int index, boolean isSelected, boolean cellHasFocus) 
    {
        System.out.println();
        ItemViewList itemView = this.listComponents.get(index);
        itemView.setSelectd(isSelected);
        
        itemView.bind(value);
        
        return itemView.getComponent();
    }
    
    public void add(ItemViewList component, ItemDataSet dataSet)
    {
        this.listComponents.add(component);
        this.listItemDataSets.add(dataSet);
    }
    
    public void remove(int index)
    {
        this.listComponents.remove(index);
        this.listItemDataSets.remove(index);
    }
    
    public void clear()
    {
        this.listComponents.clear();
        this.listItemDataSets.clear();
    }
}
