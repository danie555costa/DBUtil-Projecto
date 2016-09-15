/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.style;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Daniel
 */
class StyleContainer 
{
    private HashMap<String, Style> styles;
    private ArrayList<StyleReference> listReferences;
    
    public StyleContainer() 
    {
        this.styles = new HashMap<>();
        this.listReferences = new ArrayList<>();
    }
    
    /**
     * Adicionar um novo estilo
     * @param name
     * @param parrent
     * @param style 
     */
    public void addStyle(Style style)
    {
        if(style == null || style.getName() == null) return;
        Style styleParrent = this.styles.get(style.getParrentName());
        if(this.styles.containsKey(style.getName()))
            throw  new RuntimeException("Style alered defined "+style.getName()+" -> Style has duplicated");
        else styles.put(style.getName(), style);
        if(style.getParrentName() != null)
            this.listReferences.add(new StyleReference(style.getName(), style.getParrentName(), styleParrent != null));
    }
    
    /**
     * Definir o parente
     * @param styleName
     * @param styleReference 
     */
    private boolean setReferences (String styleName, String styleReference)
    {
        Style parrent = this.styles.get(styleReference);
        Style style = this.styles.get(styleName);
        if(parrent != null && style != null)
        {
            style.setParrent(parrent);
            return true;
        }
        return false;
    }
    
    /**
     * Revalidar as definicoes de refeencia do estilos 
     */
    public void reValidParrent(boolean breakIfInvalid)
    {
        this.listReferences.stream().forEach((StyleReference i) -> 
        {
            i.apllyed = this.setReferences(i.name, i.reference);
        });
        if(breakIfInvalid)
        {
            for (StyleReference i:this.listReferences)
                if(!i.apllyed) throw new RuntimeException("Not found all style referenced");
                
        }
    }

    Style get(String style) 
    {
        return this.styles.get(style);
    }
    
    private class StyleReference
    {

        String name;
        String reference;
        boolean apllyed;
        
        public StyleReference(String name, String reference, boolean apllyed) {
            this.name = name;
            this.reference = reference;
            this.apllyed = apllyed;
        }

        @Override
        public String toString() {
            return "StyleReference{" + "name=" + name + ", reference=" + reference + ", apllyed=" + apllyed + '}';
        }
    }
}
