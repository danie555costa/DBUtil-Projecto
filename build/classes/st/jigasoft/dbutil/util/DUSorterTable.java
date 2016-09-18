/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util;

import javax.swing.DefaultRowSorter;

/**
 *
 * @author Servidor
 */
public class DUSorterTable extends DefaultRowSorter
{
    
    public DUSorterTable(FilterableTableModel model)
    {
        super.setModelWrapper(new LocalModelWarp(model));
        
    }
    
    public int getIndexInModel (int indexRow)
    {
        return super.convertRowIndexToModel(indexRow);
    }
    
    
    
    
    
    
    
    
    
    private class LocalModelWarp  extends  ModelWrapper<FilterableTableModel, String> 
    {
        private FilterableTableModel model;
        
        
        private LocalModelWarp(FilterableTableModel model)
        {
            this.model = model;
        }

        @Override
        public FilterableTableModel getModel() {
            return this.model;
        }


        @Override
        public int getColumnCount() {
            return this.model.getColumnCount();
        }

        @Override
        public int getRowCount() {
            return this.model.getRowCount();
        }

        @Override
        public Object getValueAt(int row, int column)
        {
            Object vaObject = this.model.getValueAt(row, column);
            if(vaObject != null)
            {
                //Validacao numerica
                boolean isNumber = DUStringNumber.castDouble(vaObject.toString()) != null;
                if(isNumber) return this.ajustLength(row, column, vaObject);
                
                //Validacao Monetaria
                DUMoney m =  DUMoney.createByParrent(vaObject.toString(), this.model.getMoneyReferences(), true);
                if(m != null) return this.ajustLength(row, column, vaObject);
                
                //Validacao de Data
                String data = DUDateTime.Formats.cast(vaObject.toString(), DUDateTime.Formats.YYYYMMDD_TRASO);
                if(data != null)  return data;
            }
            return vaObject;
        }

        @Override
        public String getIdentifier(int row) {
            
            return this.model.getColumnName(row);
        }

        /**
         * Ajustar o tanho  para igualara ao tamanho do maior valor na coluna
         * @param row a linha onde na presisa compara a linha ao valor em test
         * @param column a coluna base
         * @param vaObject o valor a igualar
         * @return 
         */
        private Object ajustLength(int row, int column, Object vaObject)
        {
            Object otherCompare;
            int maxLength = 0;
            
            // Verificar na coluna a String com maior tamanho
            for(int i=0; i< this.model.getRowCount(); i++)
            {
                if(i == row) continue;
                otherCompare = model.getValueAt(i, column);
                if(otherCompare == null) otherCompare = "";
                if(otherCompare.toString().length() > maxLength)
                        maxLength = otherCompare.toString().length();
            }
            return "9"+DUString.completIni(vaObject.toString(), '0', maxLength);
        }

    }
    
    
    
}
