/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.view;

import st.jigasoft.dbutil.util.DUString;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Servidor
 * @param <E>
 */
public class DUAutoComplity<E> extends EditText
{
    private final ArrayList<String> listCompletion;
    private int startComplityAt;
    private final JPopupMenu popCompletion;
    private ListView listShowValues;
    private DefaultListModel<String> listModel;
    private int autoComplitHeight;
    
    
    public DUAutoComplity()
    {
        super();
        this.listCompletion = new ArrayList<>();
        this.popCompletion = new JPopupMenu();
        this.listShowValues = new ListView();
        this.listModel = new DefaultListModel<>();
        this.listShowValues.listValues.setModel(this.listModel);
        this.startComplityAt = 1;
        this.autoComplitHeight = 120;
       
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                DUAutoComplity.this.autoCompit(evt);
            }
        });
        this.popCompletion.add(this.listShowValues);
        
        this.listShowValues.listValues.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                DUAutoComplity.this.onSelect(evt);
            }
        });
        
        this.listShowValues.listValues.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                DUAutoComplity.this.onSwitch(evt);
            }
        });
        
        
    }

    /**
     * Buscar qual valor a completacao ira iniciar
     * @return 
     */
    public int getStartComplityAt()
    {
        return startComplityAt;
    }

    /**
     * Identificar a aprtir de qual valor deve iniciar a completacao
     * @param startComplityAt O valor iniciar 
     */
    public void setStartComplityAt(int startComplityAt) 
    {
        this.startComplityAt = startComplityAt;
    }

    public int getAutoComplitHeight() {
        return autoComplitHeight;
    }

    public void setAutoComplitHeight(int autoComplitHeight) {
        this.autoComplitHeight = autoComplitHeight;
    }
    
    
    
    /**
     * Adicionar um novo valor na copletacao
     * @param newCompletion O novo valor a copletar
     */
    public void addCompletion(String newCompletion)
    {
        if(! this.listCompletion.contains(newCompletion))
        {
            this.listCompletion.add(newCompletion);
            Collections.sort(this.listCompletion);
        }
    }
    
    /**
     * Adicionar varios completion
     * @param completion Lista de copletacao
     */
    public void addCompletion(List<String> completion)
    {
        completion.stream().forEach((s) -> {
            this.addCompletion(s);
        });
    }
    
    
    /**
     * Remover um completacao em um dado poto da lista
     * @param index A posicao da completacoa removida
     * @return O valor da completacao removida | null caso nao remover nenhum valor
     */
    public String removeCompletion (int index)
    {
        if(index < this.listCompletion.size())
            return this.listCompletion.remove(index);
        return null;
    }
    
    /**
     * Remover uma dada completação
     * @param value A completacao que sera removida
     */
    public void removeCompletion(String value)
    {
        this.listCompletion.remove(value);
    }
    
    /**
     * Limpar a lista toda de completion
     */
    public void clearCompletion()
    {
        this.listCompletion.clear();
    }
    
    
    
    private void autoCompit(KeyEvent evt)
    {
        if(this.getText().length() >= this.startComplityAt)
        {
            ArrayList<String> likeds = DUString.likeStart(this.listCompletion, super.getText());
            if(likeds.size() > 0)
            {
                this.listModel.clear();
                for(String s: likeds)
                    listModel.addElement(s);
                this.popCompletion.show(this, 0, this.getHeight());
                this.popCompletion.setPopupSize(this.getWidth(), this.autoComplitHeight);
                this.popCompletion.updateUI();
                this.listShowValues.updateUI();
                this.requestFocusInWindow();

                switch(evt.getKeyCode())
                {
                    case 28: case 40:
                        this.listShowValues.listValues.requestFocusInWindow();
                        if(likeds.size() > 0)this.listShowValues.listValues.setSelectedIndex(0);
                        break;
                }
            }
            else this.popCompletion.setVisible(false);
        }else this.popCompletion.setVisible(false);
    }

    private void onSwitch(KeyEvent evt) 
    {
        switch(evt.getKeyCode())
        {
            case 10:
                if(this.listShowValues.listValues.getSelectedIndex() != -1)
                {
                    this.setText(this.listShowValues.listValues.getSelectedValue());
                    this.popCompletion.setVisible(false);
                    this.requestFocusInWindow();
                }
        }
    }

    /**
     * Au selecionar
     * @param evt 
     */
    private void onSelect(ListSelectionEvent evt)
    {
        
    }

    @Override
    public boolean isValidData()
    {
        return super.isValidData(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
