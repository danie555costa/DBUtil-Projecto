/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.view;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.ListModel;

/**
 *
 * @author xdata
 */
public final class VerticalListView  extends JList<ItemDataSet> implements Createble{
    
    private List<Component> recicledViewList;
    private ListViewManager listViewManager;
    
    public VerticalListView(ListModel<ItemDataSet> dataModel) {
        super(dataModel);
        this.onCreate();
    }

    public VerticalListView(ItemDataSet[] listData) {
        super(listData);
        this.onCreate();
    }

    public VerticalListView(Vector<? extends ItemDataSet> listData) {
        super(listData);
        this.onCreate();
    }

    public VerticalListView() {
        onCreate();
    }

    @Override
    public void onCreate() {
        this.recicledViewList = new  ArrayList<>();
        this.listViewManager = new ListViewManager();
        
        super.setModel(listViewManager);
        super.setCellRenderer(this.listViewManager);
    }

    public ListViewManager getListViewManager() {
        return listViewManager;
    }

    @Override
    public void setModel(ListModel<ItemDataSet> model) {
        super.setModel(model);
    }

    public void addItem(ItemViewList itemViewList, ItemDataSet dataSet) {
        listViewManager.add(itemViewList, dataSet);
    }
    
    public void clearItem()
    {
        this.listViewManager.clear();
    }
}
