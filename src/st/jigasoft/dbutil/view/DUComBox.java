/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.view;

import st.jigasoft.dbutil.util.DUComBoxItem;
import st.jigasoft.dbutil.util.models.DUComponetValidate;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Servidor
 */
public class DUComBox extends JComboBox<DUComBoxItem> implements DUComponetValidate
{
    private String label;

    public DUComBox(ComboBoxModel<DUComBoxItem> aModel)
    {
        super(aModel);
        this.init();
    }

    public DUComBox(DUComBoxItem[] items) 
    {
        super(items);
        this.init();
    }

    public DUComBox(ArrayList<DUComBoxItem> items)
    {
        super((DUComBoxItem [])items.toArray());
        this.init();
    }

    public DUComBox()
    {
        this.init();
    }

    private void init() 
    {
        this.label = "";
    }

    @Override
    public boolean isValidData() 
    {
        return true;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    
    
}
