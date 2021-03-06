/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.view;

import st.jigasoft.dbutil.util.FilterableTableModel;
import st.jigasoft.dbutil.util.models.Abas.IntentType;
import st.jigasoft.dbutil.util.models.GeralAbas;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.plaf.metal.MetalIconFactory;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author Daniel
 */
public abstract class DUReport extends GeralAbas
{
    private static String GROUP = "GROUP";

    private Date newDateInicio;
    private Date oldDateInico;
    private Date newDateFim;
    private Date oldDateFim;
    private double dividerTableGraficosParcente;
    private double dividerTableGraficos;
    private double dividerTableFutter;
    
    
    private DUReport.StateReport state;
            
    /**
     * Creates new form PReportGeral
     */
    public DUReport() 
    {
        initComponents();
        this.panelGraficoTop.setLayout(new GridLayout(1, 1));
        this.panelGraficoButton.setLayout(new GridLayout(1, 1));
        
        this.choseGroupData.addOption("DAY", "Diario");
        this.choseGroupData.addOption("MONTH", "Mensal");
        this.choseGroupData.addOption("YEAR", "Anual");
        
        this.choseExport.addOption("PDF", "PDF");
        this.choseExport.addOption("XLS", "XLS");
        this.choseExport.addOption("XML", "XML");
        this.choseExport.addOption("CSV", "CSV");
        
        
        this.choseGrafico.addOption("BARA", "Bara");
        this.choseGrafico.addOption("CIRCULO", "Circulo");
        
        
        choseGroupData.getIten("DAY").setValue("FORMAT", "dd-MM-yyyy");
        choseGroupData.getIten("MONTH").setValue("FORMAT", "MMMMM yyyy");
        choseGroupData.getIten("YEAR").setValue("FORMAT", "YYYY");
        
        choseGroupData.getIten("DAY").setObject(DUReport.GROUP, ModoAgrupamento.DAILY);
        choseGroupData.getIten("MONTH").setObject(DUReport.GROUP, ModoAgrupamento.MONTHLY);
        choseGroupData.getIten("YEAR").setObject(DUReport.GROUP, ModoAgrupamento.YEARLY);
        
       
        this.choseGroupData.setOnValueChange((oldVall, newVall)-> 
        {
            this.onChangeGroupDate((oldVall != null)? (ModoAgrupamento) oldVall.getObject("GROUP"): null, 
                    (newVall != null)? (ModoAgrupamento) newVall.getObject("GROUP"): null);
        });
        
        this.choseGroupData.setOnItemStateChange((item, oldState, newState)->
        {
            if(newState) this.setFormatDate(item);
        });
        
        this.choseGrafico.setOnValueChange((oldVall, newVall)-> 
        {
            this.onChangeGrafico((oldVall != null)? oldVall.getKey(): null, 
                    (newVall != null)?newVall.getKey(): null);
        });
        
        this.choseTopOptions.setOnValueChange((oldVall, newVall)->
        {
             this.onChangeModoDate(
                    oldVall,
                    newVall);
        });
        
        this.dividerTableGraficosParcente = 75;
        this.dividerTableGraficos = 50;
        this.dividerTableFutter = 90;
        
        this.setFormatDate(choseGroupData.getIten("DAY"));
        this.verItem(false);
        this.verTipoGrafico(false);
        this.verTopOptions(false);
        this.verFutter(false);
        this.verGraficos(false);
        this.verDateEnd(true);
        this.verDateStart(true);
        this.verGrupoDate(true);
        this.onCreate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        separatorTabelaGrafico = new javax.swing.JSplitPane();
        separatorGrafico = new javax.swing.JSplitPane();
        jPanel9 = new javax.swing.JPanel();
        panelGraficoTop = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        choseGrafico = new st.jigasoft.dbutil.view.DUOptions<String>();
        panelGraficoButton = new javax.swing.JPanel();
        separatorTable = new javax.swing.JSplitPane();
        panelTable = new javax.swing.JPanel();
        table = new st.jigasoft.dbutil.view.FiterableTable();
        jPanel1 = new javax.swing.JPanel();
        btWindowsBtRigth = new javax.swing.JButton();
        btWindowsLeft = new javax.swing.JButton();
        panelCommands = new javax.swing.JPanel();
        dtEnd = new com.toedter.calendar.JDateChooser();
        dtStart = new com.toedter.calendar.JDateChooser();
        choseGroupData = new st.jigasoft.dbutil.view.DUOptions<String>();
        btFindItem = new javax.swing.JButton();
        txtItemDescrision = new st.jigasoft.dbutil.view.EditText();
        choseTopOptions = new st.jigasoft.dbutil.view.DUOptions<String>();
        panelFuter = new javax.swing.JPanel();
        choseExport = new st.jigasoft.dbutil.view.DUOptions();
        panelCuston = new javax.swing.JPanel();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        separatorTabelaGrafico.setDividerLocation(900);

        separatorGrafico.setDividerLocation(250);
        separatorGrafico.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout panelGraficoTopLayout = new javax.swing.GroupLayout(panelGraficoTop);
        panelGraficoTop.setLayout(panelGraficoTopLayout);
        panelGraficoTopLayout.setHorizontalGroup(
            panelGraficoTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 213, Short.MAX_VALUE)
        );
        panelGraficoTopLayout.setVerticalGroup(
            panelGraficoTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 383, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGraficoTop, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGraficoTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        separatorGrafico.setTopComponent(jPanel9);

        javax.swing.GroupLayout panelGraficoButtonLayout = new javax.swing.GroupLayout(panelGraficoButton);
        panelGraficoButton.setLayout(panelGraficoButtonLayout);
        panelGraficoButtonLayout.setHorizontalGroup(
            panelGraficoButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelGraficoButtonLayout.setVerticalGroup(
            panelGraficoButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(choseGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelGraficoButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(panelGraficoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(choseGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        separatorGrafico.setRightComponent(jPanel10);

        separatorTabelaGrafico.setRightComponent(separatorGrafico);

        separatorTable.setDividerLocation(500);
        separatorTable.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        btWindowsBtRigth.setFont(new java.awt.Font("DejaVu Sans", 1, 11)); // NOI18N
        btWindowsBtRigth.setForeground(new java.awt.Color(204, 0, 0));
        btWindowsBtRigth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btWindowsBtRigthActionPerformed(evt);
            }
        });

        btWindowsLeft.setFont(new java.awt.Font("DejaVu Sans", 1, 11)); // NOI18N
        btWindowsLeft.setForeground(new java.awt.Color(204, 0, 0));
        btWindowsLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btWindowsLeftActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btWindowsLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btWindowsBtRigth, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btWindowsBtRigth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btWindowsLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        dtEnd.setDate(new Date());
        dtEnd.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtEndPropertyChange(evt);
            }
        });

        dtStart.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtStartPropertyChange(evt);
            }
        });

        btFindItem.setText("...");
        btFindItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFindItemActionPerformed(evt);
            }
        });

        txtItemDescrision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtItemDescrisionKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelCommandsLayout = new javax.swing.GroupLayout(panelCommands);
        panelCommands.setLayout(panelCommandsLayout);
        panelCommandsLayout.setHorizontalGroup(
            panelCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCommandsLayout.createSequentialGroup()
                .addComponent(choseGroupData, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dtStart, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(choseTopOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtItemDescrision, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btFindItem, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelCommandsLayout.setVerticalGroup(
            panelCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCommandsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dtStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelCommandsLayout.createSequentialGroup()
                        .addGroup(panelCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btFindItem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(choseTopOptions, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dtEnd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtItemDescrision, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(choseGroupData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelTableLayout = new javax.swing.GroupLayout(panelTable);
        panelTable.setLayout(panelTableLayout);
        panelTableLayout.setHorizontalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCommands, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelTableLayout.setVerticalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTableLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCommands, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(table, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
        );

        separatorTable.setTopComponent(panelTable);

        choseExport.setModoSelection(st.jigasoft.dbutil.view.DUOptions.ModoSelection.SAMPLES);

        javax.swing.GroupLayout panelCustonLayout = new javax.swing.GroupLayout(panelCuston);
        panelCuston.setLayout(panelCustonLayout);
        panelCustonLayout.setHorizontalGroup(
            panelCustonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 621, Short.MAX_VALUE)
        );
        panelCustonLayout.setVerticalGroup(
            panelCustonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFuterLayout = new javax.swing.GroupLayout(panelFuter);
        panelFuter.setLayout(panelFuterLayout);
        panelFuterLayout.setHorizontalGroup(
            panelFuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFuterLayout.createSequentialGroup()
                .addComponent(panelCuston, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(choseExport, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelFuterLayout.setVerticalGroup(
            panelFuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCuston, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFuterLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(choseExport, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        separatorTable.setRightComponent(panelFuter);

        separatorTabelaGrafico.setLeftComponent(separatorTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(separatorTabelaGrafico, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(separatorTabelaGrafico)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void dtStartPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtStartPropertyChange
        this.newDateInicio = this.dtStart.getDate();
        if(hasAltered(this.newDateInicio, this.oldDateInico))
        {
            this.onChageDateInicio(oldDateInico, newDateInicio);
            this.oldDateInico = this.newDateInicio;
        }
        
    }//GEN-LAST:event_dtStartPropertyChange

    private void dtEndPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtEndPropertyChange
        this.newDateFim = this.dtEnd.getDate();
        if(hasAltered(oldDateFim, newDateFim))
        {
            this.onChageDateFim(oldDateFim, newDateFim);
            this.oldDateFim = newDateFim;
        }
    }//GEN-LAST:event_dtEndPropertyChange

    private void btFindItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFindItemActionPerformed
        this.onLoadItems(this.txtItemDescrision);
    }//GEN-LAST:event_btFindItemActionPerformed

    private void txtItemDescrisionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemDescrisionKeyReleased
        this.onItemKeyRelesed(evt);
    }//GEN-LAST:event_txtItemDescrisionKeyReleased

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        this.restoreDivider();
    }//GEN-LAST:event_formComponentResized

    private void btWindowsBtRigthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btWindowsBtRigthActionPerformed
         this.closeAreaCommand((JButton) evt.getSource());
    }//GEN-LAST:event_btWindowsBtRigthActionPerformed

    private void btWindowsLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btWindowsLeftActionPerformed
        this.closeAreaCommand(btWindowsLeft);
    }//GEN-LAST:event_btWindowsLeftActionPerformed

    private boolean hasAltered(Date oldDate, Date newDate)
    {
        return oldDate == null && newDate != null
                || oldDate != null && newDate == null
                || (oldDate != null && newDate != null && !oldDate.equals(newDate));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btFindItem;
    private javax.swing.JButton btWindowsBtRigth;
    private javax.swing.JButton btWindowsLeft;
    private st.jigasoft.dbutil.view.DUOptions choseExport;
    private st.jigasoft.dbutil.view.DUOptions<String> choseGrafico;
    private st.jigasoft.dbutil.view.DUOptions<String> choseGroupData;
    private st.jigasoft.dbutil.view.DUOptions<String> choseTopOptions;
    private com.toedter.calendar.JDateChooser dtEnd;
    private com.toedter.calendar.JDateChooser dtStart;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel panelCommands;
    private javax.swing.JPanel panelCuston;
    private javax.swing.JPanel panelFuter;
    private javax.swing.JPanel panelGraficoButton;
    private javax.swing.JPanel panelGraficoTop;
    private javax.swing.JPanel panelTable;
    private javax.swing.JSplitPane separatorGrafico;
    private javax.swing.JSplitPane separatorTabelaGrafico;
    private javax.swing.JSplitPane separatorTable;
    private st.jigasoft.dbutil.view.FiterableTable table;
    private st.jigasoft.dbutil.view.EditText txtItemDescrision;
    // End of variables declaration//GEN-END:variables

    private void onProprietChage(PropertyChangeEvent evt) 
    {
    }

    @Override
    public void goTo(int page) 
    {
    }

    @Override
    public void goMy()
    {
    }

    @Override
    public void reciveIntent(IntentType intent, String commandCod, Consumer<Object> action, HashMap<String, Object> mapDataIntention)
    {
    }

    @Override
    public void loadData()
    {
        this.onLoadData();
        this.stateReport(2);
        restoreDivider();
    }

    /**
     * Resetar a distancia dos divideres
     */
    public void restoreDivider() 
    {
       defineDivider('W', this.separatorTabelaGrafico, this.dividerTableGraficosParcente);
       defineDivider('H', this.separatorGrafico, this.dividerTableGraficos);
       defineDivider('H', this.separatorTable, this.dividerTableFutter);
    }

    /**
     * Definer o tamanho padrao do divider
     * W as width
     * H as Heigth
     * @param key
     * @param sepTableGarfico
     * @param parcent 
     */
    private void defineDivider(char key, JSplitPane sepTableGarfico, double parcent)
    {
        double cParcent = parcent/100;
        int size = (key=='H')?sepTableGarfico.getHeight() :sepTableGarfico.getWidth();
        int location = (int) (size*cParcent);
        sepTableGarfico.setDividerLocation(location);
    }
    
    
    @Override
    public void free() 
    {
    }

    
    
     /**
      * Definir o medelo na tabela
      * @param modelTable 
      */
    protected void setModelReport (FilterableTableModel modelTable)
    {
        this.table.setModel(modelTable);
    }
    
  

    /**
     * Definir o tipo do laiout do grafico de cima
     * @param layout 
     */
    protected void setLayoutPanelGraficoTop(LayoutManager layout) 
    {
        this.panelGraficoTop.setLayout(layout);
    }
    
    /**
     * Definir o tipo do layout do grafico de baixo
     * @param layout 
     */
    protected void setLayoutPanelGraficoButton(LayoutManager layout) 
    {
         this.panelGraficoButton.setLayout(layout);
    }
    
    /**
     * Definir o tipo do layout da area de costumizaccao
     * @param layout 
     */
    protected void setLayoutPanelCostumn(LayoutManager layout) 
    {
         this.panelCuston.setLayout(layout);
    }
    
      /**
     * Adicionar os graficao de cima
     * @param chartPanels 
     */    
    protected void addGrafiquisTop(ChartPanel ... chartPanels)
    {
        this.addComponestesAt(this.panelGraficoTop, chartPanels);
    }
    
    /**
     * Adicionar os graficos de baixo
     * @param chartPanels 
     */
    protected void addGrafiquisButton(ChartPanel ... chartPanels)
    {
        this.addComponestesAt(this.panelGraficoButton, chartPanels);
    }
    
    /**
     * Adiciora componete na area costumizada
     * @param costoums 
     */
    protected void addElementeCostoum(JComponent ... costoums)
    {
        this.addComponestesAt(this.panelCuston, costoums);
    }
    
    
    /**
     * Adicionar coponetes a um dado panel
     * @param panel
     * @param chartPanels 
     */
    private void addComponestesAt (JPanel panel, JComponent ... chartPanels)
    {
        panel.removeAll();
        if(chartPanels == null) return;
        for(JComponent component:chartPanels)
            if(component!= null)
            {
                panel.add(component);
                panel.updateUI();
            }
    }

    /**
     * Ao altera a data do inico 
     * @param oldDate
     * @param newDate
     */
    protected abstract void onChageDateInicio(Date oldDate, Date newDate);

    /**
     * Ao alterar a data do fim
     * @param oldDateFim
     * @param newDateFim 
     */
    protected abstract void onChageDateFim(Date oldDateFim, Date newDateFim);

    /**
     * Ao carregar os item
     * @param itemDescricion 
     */
    protected abstract void onLoadItems(EditText itemDescricion);
    
    
    /**
     * Ao crear a estrutura do relatorio
     */
    protected void onCreate()
    {
        
    }
    
    /**
     * Quando o tipo de agrupameto for alterado
     * @param oldGroup
     * @param newGroup 
     */
    protected abstract void onChangeGroupDate (ModoAgrupamento oldGroup, ModoAgrupamento newGroup);
    
    /**
     * Quando o tipo de grafico for alterado
     * @param oldGrafico
     * @param newGrafico 
     */
    protected abstract void onChangeGrafico (String oldGrafico, String newGrafico);
    
  
    /**
     * Ao escrever no Item
     * @param evt
     */
    protected  abstract  void onItemKeyRelesed(KeyEvent evt);
    
    
    protected abstract void onLoadData();

    protected void verGrupoDate(boolean  ver)
    {
        this.choseGroupData.setVisible(ver);
    }
    
    protected void verDateStart(boolean  ver)
    {
        this.dtStart.setVisible(ver);
    }
    
    protected void verDateEnd(boolean  ver)
    {
        this.dtEnd.setVisible(ver);
    }
    
    protected void verItem(boolean  ver)
    {
        this.txtItemDescrision.setVisible(ver);
        this.btFindItem.setVisible(ver);
    }
    
    protected void verTopOptions(boolean ver)
    {
        this.choseTopOptions.setVisible(ver);
    }
    
    protected void verBottonItem(boolean ver)
    {
        if(this.txtItemDescrision.isVisible())
            this.btFindItem.setVisible(ver);
    }

    protected void verTipoGrafico(boolean ver) 
    {
        this.choseGrafico.setVisible(ver);
    }
    
    protected  void setItemText(String text)
    {
        this.txtItemDescrision.setText(text);
    }
    
    protected  void setItemHintText(String text)
    {
        this.txtItemDescrision.setTextHint(text);
    }
    
    protected void setItemTextEnable(boolean enable)
    {
        this.txtItemDescrision.setEditable(enable);
    }

    private void setFormatDate(ItemOption<String> item) 
    {
        String format = item.getValue("FORMAT");
        this.dtEnd.setDateFormatString(format);
        this.dtStart.setDateFormatString(format);
    }

    public ModoAgrupamento getAgrupamento() 
    {
        ModoAgrupamento group = null;
        if(this.choseGroupData.isVisible()
                && this.choseGroupData.hasSelectedItem())
            group =  (ModoAgrupamento) this.choseGroupData.getSelectedItem().getObject(DUReport.GROUP);
        
        return group;
    }

    public Date getStartDate() 
    {
        return this.dtStart.getDate();
    }

    public Date getEndDate() 
    {
        return this.dtEnd.getDate();
    }

    protected void verFutter(boolean ver) 
    {
    }

    protected void verButoonGrafico(boolean ver)
    {
    }

    protected void verTopGrafico(boolean ver)
    {
    }

    protected void verGraficos(boolean ver) 
    {
        this.verTopGrafico(ver);
        this.verButoonGrafico(ver);
    }
    
    protected DUOptions getModoReport()
    {
        return this.choseTopOptions;
    }

    public ItemOption<String> getSelectedTopOption() 
    {
        if(this.choseTopOptions.getSelectedItem() == null)return null;
        return this.choseTopOptions.getSelectedItem();
    }

    /**
     * Quando for altera os modo da data
     * @param oldValue
     * @param newValue 
     */
    protected void onChangeModoDate(ItemOption<String> oldValue, ItemOption<String> newValue) {}

    public DUOptions<String> getTypeGrafico() 
    {
        return this.choseGrafico;
    }

    protected ItemOption<String> getSelectedTypeGrafico() 
    {
        return this.choseGrafico.getSelectedItem();
    }

    public void setDeviderTableGraficos(double  parcente) 
    {
        this.dividerTableGraficosParcente = parcente;
    }

    public void setDividerTableGraficos(double dividerTableGraficos) {
        this.dividerTableGraficos = dividerTableGraficos;
    }

    public void setDividerTableFutter(double dividerTableFutter) {
        this.dividerTableFutter = dividerTableFutter;
    }

    public DUOptions<String> getTopOpions() 
    {
        return this.choseTopOptions;
    }

    private void closeAreaCommand(JButton butao) 
    {
        if(butao.equals(this.btWindowsBtRigth))
        {
            int newState = (this.state == StateReport.NORMAL)? 1
                    :(this.state == StateReport.CLOSED)? 2
                    :2;
            this.stateReport(newState);
        }
        else if(butao.equals(this.btWindowsLeft))
        {
            this.stateReport(3);
        }
    }

    /**
     * 
     * @param i 1- fechar | 2 - normalizr | 3 fechar
     */
    protected void stateReport(int i) 
    {
        if(i == 1)
        {
            this.btWindowsLeft.setVisible(false);
            this.btWindowsBtRigth.setIcon(MetalIconFactory.getInternalFrameDefaultMenuIcon());
            this.panelCommands.setVisible(false);
            this.panelFuter.setVisible(false);
            this.separatorTabelaGrafico.setDividerLocation(60);
            this.table.setVisible(false);
            this.panelCuston.setVisible(false);
            this.state = StateReport.CLOSED;
            this.btWindowsBtRigth.setToolTipText("Restores table");
        }
        else if(i == 2)
        {
            this.separatorTabelaGrafico.setOneTouchExpandable(true);
            this.btWindowsLeft.setVisible(true);
            this.btWindowsLeft.setIcon(MetalIconFactory.getInternalFrameMaximizeIcon(5));
            this.btWindowsBtRigth.setIcon(MetalIconFactory.getInternalFrameCloseIcon(5));
            this.separatorTabelaGrafico.getRightComponent().setVisible(true);
            this.panelCommands.setVisible(true);
            this.table.setVisible(true);
            this.panelFuter.setVisible(true);
            this.separatorTable.setVisible(true);
            this.panelCuston.setVisible(true);
            this.restoreDivider();
            this.state = StateReport.NORMAL;
            this.btWindowsBtRigth.setToolTipText("Close table");
            this.btWindowsLeft.setToolTipText("Maximize table");
        }
        else if(i == 3)
        {
            this.btWindowsLeft.setVisible(false);
            this.btWindowsBtRigth.setIcon(MetalIconFactory.getInternalFrameDefaultMenuIcon());
            this.separatorTabelaGrafico.getRightComponent().setVisible(false);
            this.defineDivider('W', separatorTabelaGrafico, 100);
            this.state = StateReport.MAXIMIZED;
            this.btWindowsBtRigth.setToolTipText("Restores table");
        }
        this.updateUI();
    }
    
    
    /**
     * Tipos do agrupamento
     */
    public static enum ModoAgrupamento
    {
        /**
         * Agrupamento diarioo
         */
        DAILY(1),
        
        /**
         * Agrupamento mensal
         */
        MONTHLY(2),
        
        /**
         * Agrupamento anual
         */
        YEARLY(3);
        
        private final int key;

        private ModoAgrupamento(int key) 
        {
            this.key = key;
        }

        public int getKey() {
            return key;
        }
    }
    
    
    private enum StateReport
    {
        MAXIMIZED,
        NORMAL,
        CLOSED
    }
}
