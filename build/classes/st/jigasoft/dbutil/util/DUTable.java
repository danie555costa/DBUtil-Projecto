/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import st.jigasoft.dbutil.view.FiterableTable;

/**
 *
 * @author Servidor
 */
public class DUTable
{
    public static void clearTable(JTable tabela)
    {
        if(tabela != null)
        {
            while(tabela.getColumnCount()>0)
                tabela.removeColumn(tabela.getColumn(tabela.getColumnName(0)));
        }
    }
    
    public static void clearTable(FiterableTable tabela)
    {
        if(tabela != null)
        {
            clearTable(tabela.getTable());
            tabela.clear();
        }
    }
    
    
    public static void alignmentTable(FiterableTable table,  Alignment alinha, int ... indexColumns)
    {
        alignmentTable(table.getTable(), alinha, indexColumns);
    }
    
    public static void alignmentTable(JTable table,  Alignment alinha, int ... indexColumns)
    {
        if(table != null
                && indexColumns != null
                && indexColumns.length >0
                && table.getColumnCount()>0)
        {
             ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        
             DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
             direita.setHorizontalAlignment(SwingConstants.RIGHT);

             DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
             centro.setHorizontalAlignment(SwingConstants.CENTER);

             DefaultTableCellRenderer esqu = new DefaultTableCellRenderer();
             esqu.setHorizontalAlignment(SwingConstants.LEFT);
            
            
                for(int index: indexColumns)
                {
                    if(index>=0 && index<table.getModel().getColumnCount())
                    {
                        if(alinha == Alignment.CENTER)
                        {
                            table.getColumnModel().getColumn(index).setCellRenderer(centro);
                        }
                        else if(alinha == Alignment.RIGHT)
                            table.getColumnModel().getColumn(index).setCellRenderer(direita);
                        else table.getColumnModel().getColumn(index).setCellRenderer(esqu);
                    }
                }
        }
    }
    
    public static Alignment getAlignmentOrder(String valeus)
    {
        Number n = DUStringNumber.castDouble(valeus);
        if(n != null) return  Alignment.RIGHT;
        DUMoney parentMoney = new DUMoney();
        DUMoney m = DUMoney.createByParrent(valeus, DUMoney.getReferenceMoney(), true);
        if(m != null) return Alignment.RIGHT;
        ArrayList<DUDateTime.Formats> dt = DUDateTime.Formats.findByValue(valeus);
        if(dt != null && !dt.isEmpty()) return Alignment.CENTER;
        return Alignment.LEFT;
    }
    
    
    public static enum Alignment
    {
        /**
         * alinhar para direita
         */
        RIGHT,
        
        /**
         * Alinhar para esquerda
         */
        LEFT,
        
        /**
         * Alinhar tudo acentro
         */
        CENTER
        
    }
}
