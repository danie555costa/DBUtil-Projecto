/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import st.jigasoft.dbutil.dba.DUExecute;
import st.jigasoft.dbutil.dba.DUResultSet;

/**
 *
 * @author Servidor
 */
public class DUComBoxItem
{

    public static void setSeleted(JComboBox<DUComBoxItem> boxMetreageBase, String cod)
    {
        for(int i = 0; i< boxMetreageBase.getItemCount(); i++)
        {
            DUComBoxItem item = boxMetreageBase.getItemAt(i);
            if(item.getCod() != null && item.getCod().equals(cod))
            {
                boxMetreageBase.setSelectedIndex(i);
                return;
            }
            
        }
        if(boxMetreageBase.getItemCount()>0)
            boxMetreageBase.setSelectedIndex(0);
    }
    
    private String cod;
    private Object value;
    private HashMap<String, Object> oldValues;
    
    public  DUComBoxItem()
    {
        this.oldValues = new HashMap<>();
    }
    
    public DUComBoxItem(String cod, Object value)
    {
        this.cod = cod;
        this.value = value;
    }

    public String getCod()
    {
        return this.cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Object getValue() {
        return this.value;
    }
    
    @Override
    public String toString()
    {
        return this.value.toString();
    }
    
    public String getValueString()
    {
        return this.toString();
    }
    
    public Object getValue (String keyName)
    {
        return this.oldValues.get(keyName);
    }
    
    public void putSetValue(String codName, Object value)
    {
        if(this.oldValues.containsKey(codName)) this.oldValues.replace(codName, value);
        else this.oldValues.put(codName, value);
    }
    
    public int countOldsValues()
    {
        return this.oldValues.size();
    }
    
    public Set<Map.Entry<String, Object>> getEntrysSetsValues ()
    {
        return this.oldValues.entrySet();
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    public static ArrayList<DUComBoxItem> loadCombox(JComboBox<DUComBoxItem> combox, DUExecute execute, String tableName, String columnKey, String columnValue, String label, Object ... inParam)
    {
        if(label==null || label.length() == 0) label = "Select";
        label = "("+label+")";
        ArrayList<DUComBoxItem> list = new ArrayList<>();
        list.add(new DUComBoxItem());
        list.get(0).setValue(label);
        try
        {
            ResultSet rs = execute.select(tableName, "*", inParam);
            DUResultSet.forEachResultSet(rs, (map)->
            {
                Object val;
                DUComBoxItem item = new  DUComBoxItem();
                val  = map.get(columnKey);
                item.setCod(val != null? val.toString(): null);
                val  = map.get(columnValue);
                item.setValue(val != null? val.toString(): null);
                if(item.getCod() != null)
                    list.add(item);
            });
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        if (combox != null)
        {
            combox.removeAllItems();
            list.stream().forEach((s) -> combox.addItem(s));
        }
        return list;
    }
    
    public static  DefaultComboBoxModel creatModel (DUExecute execute, String tableName, String columnKey, String columnValue, String label, Object ... inParam)
    {
        ArrayList<DUComBoxItem> model = loadCombox(null, execute, tableName, columnKey, columnValue, label, inParam);
        return new DefaultComboBoxModel(model.toArray());
    }
    
    

    

    public int codInt() 
    {
        return Integer.parseInt(this.cod);
    }

}
