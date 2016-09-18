/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util;

import st.jigasoft.dbutil.view.FiterableTable;
import st.jigasoft.dbutil.util.models.ItemTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import st.jigasoft.dbutil.view.callback.OnTableValueChange;

/**
 * Classe model é o modelo suportado em DUTableFilter
 * @author Servidor
 */
public final class FilterableTableModel extends AbstractTableModel implements Runnable
{
    private final ArrayList<ItemTableModel> listSourceData;
    private final ArrayList<ItemTableModel> filterList;
    private  String [] colunsName;
    private  String [] colunsShow;
    
    private final String keyName;
    private boolean editable;
    private DUMoney moneyReferences;
    private String filterValue;
    private String[] collunsHiden;
    private DUSorterTable sorter;
    private int[] colunsShowIndex;
    private OnTableValueChange onTableValueChange;
    private Thread filterBackground;
    
    public FilterableTableModel(String [] colunsName, String keyName, DUMoney moneyReferences, String ... collunsHiden)
    {
        this.listSourceData = new ArrayList<>();
        this.filterList = new ArrayList<>();
        this.editable = false;
        this.keyName = keyName;
        this.setColumn(colunsName, collunsHiden);
        prepareStructure(colunsName, collunsHiden);
    }

    private void prepareStructure(String[] colunsName1, String[] collunsHiden1)
    {
        int count = 0;
        int countIndex = 0;
        for (String s : colunsName1) {
            boolean add = true;
            for (String s1 : collunsHiden1) {
                if(s1.equals(s)) 
                {
                    add = false;
                    break;
                }
            }
            if(add)
            {
                colunsShow[count] = s;
                colunsShowIndex[count] = countIndex;
                count ++;
            }
            countIndex ++;
        }
    }
    
    public FilterableTableModel() {
        this.listSourceData = new ArrayList<>();
        this.filterList = new ArrayList<>();
        this.colunsName = new String[0];
        this.keyName = null;
        this.collunsHiden = new String[0];
        this.colunsShow = new String[0];
        this.colunsShowIndex = new int[0];
    }
    
    public FilterableTableModel (String ... column)
    {
        this(column, new String []{});
    }
    
    public FilterableTableModel(String columns[], String ... columnHidden)
    {
        this(columns, null, null, columnHidden);
    }
    
    public void setColumn(String [] columns, String ... columnsHidem)
    {
        this.colunsName= columns;
        this.collunsHiden = columnsHidem;
        int length = (collunsHiden != null)? colunsName.length - collunsHiden.length: colunsName.length;
        
        this.colunsShow = new String[length];
        this.colunsShowIndex = new int[length];
        this.prepareStructure(colunsName, collunsHiden);
        this.listSourceData.clear();
        this.filterList.clear();
        super.fireTableStructureChanged();
    }
    
    public void setColumn(String ... columns)
    {
        this.setColumn(columns, new String [] {});
    }

    

    public void setOnTableValueChange(OnTableValueChange onTableValueChange) {
        this.onTableValueChange = onTableValueChange;
    }
    
    
    
    /**
     * Setar os nomes para as colunas
     * @param split 
     */
    public void setColunsNames(String ... split) 
    {
        this.colunsName = split;
        this.colunsShow = new String[this.colunsName.length];
        int count = 0;
        for(String s: colunsName) 
        {
            boolean add = true;
            for(String s1: collunsHiden)
                if(s1.equals(s)) 
                {
                    add = false;
                    break;
                }
            if(add) colunsShow[count++] = s;
        }
    }    

    public DUMoney getMoneyReferences()
    {
        return moneyReferences;
    }
    
    

    public void setMoneyReferences(DUMoney moneyReferences) {
        this.moneyReferences = moneyReferences;
    }
    
    public String getKeyName()
    {
        return keyName;
    }
    
    public String [] getCamposHidem ()
    {
        return  this.collunsHiden;
    }
    
    
    public boolean add(ItemTableModel itemModel)
    {
        if(itemModel == null) return false;       
        this.listSourceData.add(itemModel);
       
        String value = this.filterValue;
        if(value==null || value.length()==0)
            this.filterList.add(itemModel);
        else this.filter();
        return  true;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    @Override
    public String getColumnName(int column)
    {
        return this.colunsShow[column];
    }
    
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return this.editable;
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        column = colunsShowIndex[column];
        Object [] mapar = filterList.get(row).values();
        Object value  = mapar[column];
        return value;
    }

    @Override
    public int getRowCount() 
    {
        return this.filterList.size();
    }

    @Override
    public int getColumnCount() 
    {
        return this.colunsShow.length;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex); 
        String column = this.colunsShow[columnIndex];
        ItemTableModel item = this.filterList.get(rowIndex);
        item.valueSetAt(aValue, column, columnIndex);
        
        if(onTableValueChange != null)
            onTableValueChange.onValueChange(aValue, rowIndex, column, columnIndex);
    }
    
    
    /**
     * Obter o valor da linha na tabela
     * @param index
     * @return 
     */
    public HashMap<String, Object> getMapAt(int index)
    {
        if(index<0 || index>=this.filterList.size()) return null;
        if(this.sorter != null) index = sorter.getIndexInModel(index);
        ItemTableModel m = filterList.get(index);
        HashMap<String, Object> map = new HashMap<>();
        int count =0;
        
        if(keyName != null)
            map.put(this.keyName, m.key());
        for(String s: this.colunsName)
            map.put(s, this.filterList.get(index).values()[count++]);
        return map;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) 
    {
        if(listSourceData.get(0).values()[columnIndex] == null) return String.class;
        if(this.filterList.isEmpty())
            return super.getColumnClass(columnIndex);
       
       if(moneyReferences != null) 
       {
           DUMoney defaultMoney = DUMoney.createByParrent(listSourceData.get(0).values()[columnIndex].toString(), moneyReferences, true);
           if(defaultMoney != null)
               return DUMoney.class;
       }
        ItemTableModel item = filterList.get(0);
        Object[] valls = item.values();
        Object vall = valls[columnIndex];
        Class<? extends Object> aClass = vall.getClass();
        return  aClass;
    }

    /**
     * Mater o antigo delecionado
     * @param mapOldSelection
     * @param tableSelection
     * @return 
     */
    public HashMap<String, Object> setOldSelectd(HashMap<String, Object> mapOldSelection, JTable tableSelection) 
    {
        if(this.getKeyName() != null && mapOldSelection != null)
        {
            for(int i =0; i<this.getRowCount(); i++)
            {
                HashMap<String, Object> m = this.getMapAt(i);
                if((m.get(this.getKeyName()) == null && m.equals(mapOldSelection))
                        ||(m.get(this.getKeyName()) != null && m.get(this.getKeyName()).equals(mapOldSelection.get(this.getKeyName()))))
                {
                    tableSelection.setRowSelectionInterval(i, i);
                    return m;
                }
            }
        }
        return  null;
    }
    
    public HashMap<String, Object> setOldSelectd(FiterableTable tableSelection) 
    {
        return setOldSelectd(tableSelection.getMapSelection(), tableSelection.getTable());
    }

    /**
     * Obter um HasMap da linha selecionada na tabeTable
     * @param jTable
     * @return 
     */
    public HashMap<String, Object> getRowMapSelect(JTable jTable)
    {
        HashMap<String, Object> map = null;
        if(jTable != null)
        {
            int select;
            if((select = jTable.getSelectedRow()) != -1)
                map = this.getMapAt(select);
        }
        return map;
    }
    
    
    public HashMap<String, Object> getRowMapSelect(FiterableTable tableFilter)
    {
        return getRowMapSelect(tableFilter.getTable());
    }
    
    public DUSorterTable getSorterTable()
    {
        this.sorter =  new DUSorterTable(this);
        return sorter;
    }
    

    public void filter() 
    {
        int tt = this.filterList.size();
        this.filterList.clear();
        super.fireTableDataChanged();
        
        if(this.filterBackground != null
                && this.filterBackground.isAlive())
        {
            this.filterBackground.interrupt();
            this.filterBackground = null;
        }
        
        if(this.filterValue==null || this.filterValue.length()==0)
        {
            this.filterList.addAll(this.listSourceData);
            super.fireTableRowsInserted(0, this.filterList.size()-1);
             System.out.println(" Total rows found "+this.filterList.size());
        }
        else
        {
            try {
                this.filterBackground = new Thread(this);
                this.filterBackground.start();
            } catch (Exception ex) 
            {
            }
        }
    }
    
    @Override
    public void run() {
        String value = this.filterValue;
        for(ItemTableModel item : listSourceData)
        {
            if(itemContais(item, value)){
                this.filterList.add(item);
                super.fireTableRowsInserted(this.filterList.size()-1, this.filterList.size()-1);
            }
        }
        this.filterBackground = null;
         System.out.println(" Total rows found "+this.filterList.size());
    }

    private boolean itemContais(ItemTableModel item, String value) {
        Object compareValue;
        for(int iCollumn =0; iCollumn<colunsShow.length; iCollumn ++)
        {
            compareValue = item.values()[iCollumn];
            if(compareValue.toString().contains(value.toUpperCase()))
                return true;
        }
        return false;
    }
    
    
    public void setFilter(String filterValue)
    {
        this.filterValue = filterValue;
        this.filter();
       
    }
    
    public void clear ()
    {
        int ttFilter = this.filterList.size();
        this.filterList.clear();
        this.listSourceData.clear();
        super.fireTableDataChanged();
    }

    /**
     * Remover uma linha da lista
     * @param index 
     * @return  
     */
    public ItemTableModel removeAt(int index)
    {
        if(index >=0 &&  index <this.filterList.size())
        {
            ItemTableModel d = this.filterList.remove(index);
            for (int i =0; i<this.listSourceData.size(); i++)
            {
                if(listSourceData.get(i).key().equals(d.key()))
                {
                    listSourceData.remove(i);
                    break;
                }
            }
            return d;
        }
        return null;
    }
    
    /**
     * Remover o dado que tiver o codigo fornecido
     * @param key
     * @return 
     */
    public ItemTableModel remove  (String key)
    {
        ItemTableModel item = null;
        for(int i =0; i<this.listSourceData.size(); i++)
        {
            if(this.listSourceData.get(i).key().equals(key))
            {
                item = this.listSourceData.remove(i);
                break;
            }
        }

        if(item != null)
        {
            for(int i =0; i<this.filterList.size(); i++)
            {
                if(this.filterList.get(i).key().equals(key))
                {
                    this.filterList.remove(i);
                    break;
                }
            }
        }
        return item;
    }

    public ArrayList<ItemTableModel> getModelValues()
    {
        return  this.listSourceData;
    }

    public boolean hasItem()
    {
        return !this.listSourceData.isEmpty();
    }

    public ItemTableModel getItemModel(int index) 
    {
        if(index <0) return  null;
        return this.filterList.get(index);
    }

    /**
     * Buscar pelo index de um item no modelo
     * @param item o item em que se pretende obter o index
     * @return 
     */
    public int getIndexOf(ItemTableModel item)
    {
        for(int i =0; i<this.filterList.size(); i++)
            if(filterList.get(i).equals(item)) return i;
        for(int i = 0; i<this.filterList.size(); i++)
            if(filterList.get(i).key().equals(item.key())) return i;
        return -1;
    }

    public void removeAll()
    {
        this.listSourceData.clear();
        this.filterList.clear();
    }

    
    public static class DefaultItemTableModel implements ItemTableModel
    {
        Object key;
        Object [] values;
        private boolean showing;

        public DefaultItemTableModel(Object keyValue, Object[] values)
        {
            this.key = keyValue;
            this.values = values;
        }

        @Override
        public Object[] values()
        {
            return values;
        }

        public void setValues(Object[] values) {
            
            this.values = values;
        }
        
        @Override
        public Object key()
        {
            return key;
        }

        @Override
        public boolean isVisible() 
        {
            return this.showing;
        }

        @Override
        public void setVisible(boolean visiblie)
        {
            this.showing =  true;
        }

        @Override
        public String toString() 
        {
            return this.key+" = "+(Arrays.toString(this.values));
        }

        
        @Override
        public int hashCode()
        {
            int hash = 7;
            hash = 47 * hash + Objects.hashCode(this.key);
            hash = 47 * hash + Arrays.deepHashCode(this.values);
            hash = 47 * hash + (this.showing ? 1 : 0);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final DefaultItemTableModel other = (DefaultItemTableModel) obj;
            if (!Objects.equals(this.key, other.key)) {
                return false;
            }
            return Arrays.deepEquals(this.values, other.values);
        }
    }
}
