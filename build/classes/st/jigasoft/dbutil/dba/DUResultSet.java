package st.jigasoft.dbutil.dba;

import st.jigasoft.dbutil.util.FilterableTableModel;
import st.jigasoft.dbutil.util.DUMoney;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 * Daniel Costa
 * @author Servidor
 */
public  class DUResultSet 
{
    /**
     * Executar uma for echa em uma ResultSet
     * @param result
     * @param action 
     */
    public static void forEachResultSet (ResultSet result, Consumer<HashMap<String, Object>> action)
    {
        try 
        {
            HashMap<String, Object> map;
            String s;
            while (result.next())
            {
                map = new HashMap<>();
                for (int i =1 ; i<=result.getMetaData().getColumnCount(); i++)
                {
                    s = result.getMetaData().getColumnName(i);
                    map.put(s, result.getObject(s));
                }
                action.accept(map);
            }
            result.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Por implementar
     * @param result
     * @param action 
     */
    public static void forEachResultSet(ResultSet result, BiConsumer<DBHeaderTable, HashMap<String, Object>> action)
    {
        
    }
    
    /**
     * Por implementar
     * @param result
     * @param action 
     */
    public static void findResultSet(ResultSet result, Function<HashMap<String, Object>, Boolean> action)
    {
        //if(action.apply(null)) return finded;
    }
    
    /**
     * Converter uma ResultSet em um ArrayList de HasMap
     * @param result
     * @return 
     */
    public static ArrayList<HashMap<String, Object>> toArrayMap (ResultSet result)
    {
        try
        {
            ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
            String column [] = new String [result.getMetaData().getColumnCount()];
            for (int i=0; i<result.getMetaData().getColumnCount(); i++)
            {
                column[i] = result.getMetaData().getColumnName(i+1);
            }
            while (result.next())
            {
                HashMap<String, Object> map = new HashMap<>();
                for(String s: column)
                {
                    map.put(s, result.getObject(s));
                }
                listMap.add(map);
            }
            result.close();
            return  listMap;
        } catch (SQLException ex) {
            Logger.getLogger(DUResultSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static class DBHeaderTable
    {
        private HashMap<String, DBColumn> mapColumns;
        
        private DBHeaderTable(ResultSetMetaData metaData)
        {
            try 
            {
                this.mapColumns = new HashMap<>();
                for (int i = 1; i<= metaData.getColumnCount(); i++)
                {
                    DBColumn c = new DBColumn();
                    try
                    {
                        c.presicion = (metaData.getPrecision(i));
                        c.type = (metaData.getColumnType(i));
                        c.typeName = (metaData.getColumnName(i));
                        c.scale = metaData.getScale(i);
                        c.tableName = metaData.getTableName(i);
                        c.schemaName = metaData.getSchemaName(i);
                        c.autoIncrement = metaData.isAutoIncrement(i);
                        c.caseSensitive = metaData.isCaseSensitive(i);
                        c.columnDisplaySize = metaData.getColumnDisplaySize(i);
                        c.columnLabel = metaData.getColumnLabel(i);
                        c.currency = metaData.isCurrency(i);
                        c.nullable = metaData.isNullable(i);
                        c.signed = metaData.isSigned(i);
                        c.definitelyWritable= metaData.isDefinitelyWritable(i);
                        c.searchable = metaData.isSearchable(i);
                        c.writable = metaData.isWritable(i);
                    }catch(Exception ex)
                    {
                        
                    }
                    this.mapColumns.put(metaData.getColumnName(i), c);
                }
            } catch (SQLException ex) 
            {
                Logger.getLogger(DUResultSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public HashMap<String, DBColumn> getMapColumns() {
            return mapColumns;
        }
    }
    
    public static DefaultTableModel creatDataModelByResultSet (ResultSet rs)
    {
        
        try 
        {
            String colunas[] = new String[rs.getMetaData().getColumnCount()];
            for(int i = 0;i<colunas.length; i++)
                colunas[i]= rs.getMetaData().getColumnName(i+1);
            
            ArrayList<HashMap<String, Object>> listData = toArrayMap(rs);
            Object data[][] = new Object[listData.size()] [colunas.length];
            int row = 0;
            for(HashMap<String, Object> map: listData)
            {
                int column = 0;
                for(String col: colunas)
                    data[row][column++] = map.get(col);
                row++;
            }
            DefaultTableModel defualt = new DefaultTableModel(data, colunas)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            return defualt;
        } catch (SQLException ex) {
            Logger.getLogger(DUResultSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Criar um model a partir de uma ResultSet
     * @param rs
     * @param key
     * @param moneyReferences
     * @param columnsHidem
     * @return Um Mode suportado pela TableFilter
     */
    public static FilterableTableModel creatTableModelKey (ResultSet rs, String key, DUMoney moneyReferences, String ... columnsHidem)
    {
        try 
        {
            String colunas [] = new String[rs.getMetaData().getColumnCount()];
            for(int i = 0;i<colunas.length; i++)
                colunas[i]= rs.getMetaData().getColumnName(i+1);
            String realColumn [] = (key == null || key.isEmpty())? colunas
                    :new String[colunas.length -1];
            int count =0;
            
           
            // nas coluna so vai aparecer o index dos que sao para adicionar
            for(String s: colunas)
            {
                if(key != null && !s.equals(key)) realColumn[count++] = s;
                else if(key == null) realColumn[count++] = s;
            }
            
            
            ArrayList<HashMap<String, Object>> listData = toArrayMap(rs);
            FilterableTableModel model = new FilterableTableModel(realColumn, key, moneyReferences, columnsHidem);
            
            
            Object keyRow;
            for(HashMap<String, Object> map: listData)
            {
                Object data[] = new Object[realColumn.length];
                int column = 0;
                for(String col: realColumn)
                {
                    
                    data[column] = map.get(col);
                    column++;
                }
                keyRow = (key != null)? map.get(key): null;
                FilterableTableModel.DefaultItemTableModel itemModel = new FilterableTableModel.DefaultItemTableModel(keyRow, data);
                model.add(itemModel);
            }
            
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(DUResultSet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static class DBColumn
    {
        private String typeName;
        private String tableName;
        private String schemaName;
        private int type;
        private int presicion;
        private int scale;
        private boolean autoIncrement;
        private boolean caseSensitive;
        private int columnDisplaySize;
        private String columnLabel;
        private boolean currency;
        private int nullable;
        private boolean definitelyWritable;
        private boolean searchable;
        private boolean writable;
        private boolean signed;

        public String getTypeName() {
            return typeName;
        }

        public String getTableName() {
            return tableName;
        }

        public String getSchemaName() {
            return schemaName;
        }

        public int getType() {
            return type;
        }

        public int getPresicion() {
            return presicion;
        }

        public int getScale() {
            return scale;
        }

        public boolean isAutoIncrement() {
            return autoIncrement;
        }

        public boolean isCaseSensitive() {
            return caseSensitive;
        }

        public int getColumnDisplaySize() {
            return columnDisplaySize;
        }

        public String getColumnLabel() {
            return columnLabel;
        }

        public boolean isCurrency() {
            return currency;
        }

        public int getNullable() {
            return nullable;
        }

        public boolean isDefinitelyWritable() {
            return definitelyWritable;
        }

        public boolean isSearchable() {
            return searchable;
        }

        public boolean isWritable() {
            return writable;
        }

        public boolean isSigned() {
            return signed;
        }
    }
}
