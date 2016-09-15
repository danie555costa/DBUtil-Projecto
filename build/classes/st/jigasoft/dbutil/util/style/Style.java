/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.style;

import java.util.HashMap;

/**
 *
 * @author Daniel
 */
class Style
{
    private String name;
    private Style parrent;
    private String parrentName;
    
    private HashMap<String, StylePropriete> methods;

    public Style(String name, String parrentName)
    {
        this.name = name;
        this.parrentName = parrentName;
        this.methods = new HashMap<>();
    }
    
    /**
     * Setar um novo style
     * @param name
     * @param value 
     */
    public StylePropriete addPropriete (String name)
    {
        if(name == null) return null;
        StylePropriete propriete;
        if(this.methods.containsKey(name))
            propriete = this.methods.get(name);
        else this.methods.put(name, (propriete = new StylePropriete(name)));
        return propriete;
    }

    public Style getParrent() 
    {
        return parrent;
    }

    public String getName() 
    {
        return name;
    }

    public String getParrentName() 
    {
        return parrentName;
    }

    public void setParrent(Style parrent)
    {
        if((this.parrentName != null)
                &&(parrent == null
                && !parrent.getName().equals(this.parrentName)))
            return;
        this.parrent = parrent;
        treatLooper(this, "");
    }
    
    private void treatLooper(Style style, String loop)
    {
        String nameParent = (parrent != null)? parrent.name : "NULL";
        //System.err.println("THIS: "+name + ", STYLE: "+style.name +", PARENT: "+ nameParent);
        loop = loop +((loop.length()>0)? " -> ":"")+this.getName();
        
        if(parrent != null)
        {
            if(parrent.equals(style))
                throw new RuntimeException("Loop parrent references encontred" + loop);
            else 
                parrent.treatLooper(style, loop);
        }
        
              
//        if(style != null && style.equals(parrent))
//            
//        else if(this.parrent != null)
//            this.parrent.treatLooper(style, loop);
    }
    
    public HashMap<String, StylePropriete> getAllValues()
    {
        HashMap<String, StylePropriete> containerValues = new HashMap<>();
        this.loadParrentValues(containerValues);
        return containerValues;
    }
    
    private void loadParrentValues(HashMap<String, StylePropriete> container)
    {
        if(this.parrent != null)
            this.parrent.loadParrentValues(container);
        
        this.methods.entrySet().stream().forEach((i) -> 
        {
            if(container.containsKey(i.getKey()))
                container.replace(i.getKey(), i.getValue());
            else container.put(i.getKey(), i.getValue());
        });   
    }
    
     
    
    
    /**
     * Obter o estulo do xml
     * @param name
     * @return 
     */
    public  Object getStyle(String name)
    {
        Object style = this.methods.get(name);
        if(style == null 
                && parrent != null)
            style = this.parrent.getStyle(name);
        return style;
    }
    
    
    class StylePropriete
    {
        String name;
        HashMap<String, String> parans;
        boolean hasDefaultValue;
        private boolean hasParam;
        private String defaultValue;

        public StylePropriete(String name)
        {
            this.name = name;
            this.parans = new HashMap<>();
        }
        
        public String getValue(String key)
        {
            return this.parans.get(key);
        }

        public String getName() {
            return name;
        }

        public boolean isHasDefaultValue() {
            return hasDefaultValue;
        }

        public boolean isHasParam() {
            return hasParam;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        void addValue(String key, String value) 
        {
            if(hasDefaultValue) throw new RuntimeException("O VALOR PADRA JA FOI DEFINIDO NA PROPRIENDADE");
            this.parans.put(key, value);
            this.hasParam = true;
        }

        void setDefaultValue(String value)
        {
            if(hasParam)throw new RuntimeException("JA EXISTE PARAMETROS DEFINIDO NAO PODE DEFINIR TAMEM O VALOR PADRAO");
            this.hasDefaultValue = true;
            this.defaultValue = value;
        }
        
        public int countParams()
        {
            return hasDefaultValue? 1
                    :(hasParam)? this.parans.size()
                    :0;
        }
    }
    
    
    //class ContinerValues
    
   

    @Override
    public String toString() {
        return "Style{" + "name=" + name + ", parrent=" + parrent + ", parrentName=" + parrentName + '}';
    }
}
