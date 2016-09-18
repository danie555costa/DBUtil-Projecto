/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.view;

import st.jigasoft.dbutil.util.FilterableTableModel;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author Servidor
 */
public class DUTable extends JTable
{
    @Override
    @Deprecated
    public void setModel(TableModel dataModel)
    {
        super.setModel(dataModel);
    }
    
    public void setModelUtil(FilterableTableModel dataModel)
    {
        super.setModel(dataModel); //To change body of generated methods, choose Tools | Templates.
    }

    public FilterableTableModel getModelUtil()
    {
        if(super.dataModel instanceof FilterableTableModel)
            return (FilterableTableModel) super.getModel();
        return null;
    }

    @Override
    @Deprecated
    public TableModel getModel() {
        return super.getModel(); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<String> getListData(String string)
    {
        String line = "";
        ArrayList<String> list = new ArrayList<>();
        for(int i =0; i<this.getModelUtil().getColumnCount(); i++)
            line = line + this.getModelUtil().getColumnName(i) + ((i<this.getColumnCount()-1)? string: "");
        list.add(line);
        for(int i = 0; i< this.getRowCount(); i++)
        {
            line = "";
            for(int j =0; j<this.getColumnCount(); j++)
                line = line + this.getValueAt(i, j).toString() + ((j<this.getColumnCount()-1)? string: "");
            line = line.replace(" ", " ");
            list.add(line);
        }
        return list;
    }
   


   
    
    
    
}
