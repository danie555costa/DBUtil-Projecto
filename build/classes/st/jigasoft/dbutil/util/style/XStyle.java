/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.style;

import com.thoughtworks.xstream.XStream;
import st.jigasoft.dbutil.util.style.Style.StylePropriete;
import st.jigasoft.dbutil.util.xml.XReference;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class XStyle 
{
    /**
     * Contentor dos estilos
     */
    private static final  StyleContainer styles = new StyleContainer();
    
    public static void loadStyle(String xmlStyle, boolean breakIfInvalid)
    {
        XStream strean = StyleConverter.prepareStrean();
        ArrayList<Style> listStyle = (ArrayList<Style>) strean.fromXML(new File(xmlStyle));
        
        listStyle.stream().forEach((Style style) -> {
            styles.addStyle(style);
        });
        styles.reValidParrent(breakIfInvalid);
    }
    
    public static void apply(String style, Object conponet, boolean require)
    {
        Style s = styles.get(style);
        if(s == null && require) throw new RuntimeException("Style "+style+" NOT FOUND");
        else if(s == null) return;
        else
        {
            //STYLING...
            Class<? extends Object> cla = conponet.getClass();
            Field.setAccessible(cla.getFields(), true);
            HashMap<String, Style.StylePropriete> filedsValues = s.getAllValues();
            
            
            for(Map.Entry<String, Style.StylePropriete> i:filedsValues.entrySet())
            {
                try 
                {
                    Method method = findField(cla, i.getValue());
                    if(method == null) continue;
                    callMethnd(method, i.getValue(), conponet);
                    
                } catch (Exception ex) {
                    Logger.getLogger(XStyle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static void callMethnd(Method method, Style.StylePropriete value, Object compnet) 
    {
        Object [] treatedValues;
        try
        {
            treatedValues = treatedParans(value, method);
            method.invoke(compnet, treatedValues);
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) 
        {
        }
       
    }

    /**
     *
     * @param cla
     * @param propriete
     * @return
     */
    public static Method findField(Class<? extends Object> cla, StylePropriete propriete)
    {
        String methodFilend;
        String key = propriete.getName();
        
        methodFilend = key.substring(0, 1).toUpperCase();
        methodFilend = methodFilend+key.substring(1, key.length());
        methodFilend = "set"+methodFilend;
        
        for(Method m:cla.getDeclaredMethods())
            if(m.getName().equals(methodFilend) 
                    && m.getParameterCount() == propriete.countParams()) return m;
        
        for(Method m:cla.getMethods())
            if(m.getName().equals(methodFilend)
                    && m.getParameterCount() == propriete.countParams()) return m;
        
        for(Method m:cla.getDeclaredMethods())
            if(m.getName().equalsIgnoreCase(methodFilend)
                    && m.getParameterCount() == propriete.countParams()) return m;
        
        for(Method m:cla.getMethods())
            if(m.getName().equalsIgnoreCase(methodFilend)
                    && m.getParameterCount() == propriete.countParams()) return m;
        return null;
    }

    private static Object[] treatedParans(StylePropriete proriete, Method method) 
    {
        Parameter p;
        if((proriete.isHasDefaultValue() 
                && method.getParameterCount()>1)) throw new RuntimeException("FALTA VALOR PARA A PROPRIEDADE "+proriete.getName());
        
        Object value, paransTreate [] = new Object[method.getParameterCount()];
        
        for(int i =0; i<method.getParameterCount(); i++)
        {
            p = method.getParameters()[i];
            
            
            if(proriete.isHasParam())
                value = proriete.getValue(p.getName());
            else value = proriete.getDefaultValue();
            value  = XReference.treatXMLValues(p.getType(), (String) value);
            paransTreate[i] = value;
            
        }
        
        return paransTreate;
    }
}
