/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.style;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Daniel
 */
public class StyleConverter implements Converter
{

    private static String CONTAINER_STYLE = "Styles";
    private static String STYLE = "Style";
    private String ATB_NAME = "name";
    private String ATB_APRRENT = "parrent";

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext mc) 
    {
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc)
    {
        Style style;
        String name;
        String parrent;
        String value;
        String valueName;
        String paranName;
        String paranValue;
        boolean hasParns;
        Style.StylePropriete p;
        
        /**<Styles>*/
        ArrayList<Style> styles = new ArrayList<>();
        HashMap<String, String> parans = new  HashMap<>();
        while (reader.hasMoreChildren()) 
        {
            reader.moveDown();
             /**<Style>*/
             name = reader.getAttribute(ATB_NAME);
             parrent = reader.getAttribute(ATB_APRRENT);
             style = new Style(name, parrent);
                while(reader.hasMoreChildren())
                {
                    hasParns = false;
                    parans.clear();
                    value = null;
                    reader.moveDown();
                    /**<Value>*/
                    valueName = reader.getNodeName();
                    if(valueName == null) throw  new RuntimeException("PROPRIEDADE DESCONHECIDA");
                   
                    for(int i =0; i <reader.getAttributeCount(); i++)
                    {
                        paranName = reader.getAttributeName(i);
                        paranValue = reader.getAttribute(paranName);
                        hasParns = true;
                        
                        if(parans.containsKey(paranName))
                            throw  new RuntimeException("DUBLICATEDE PARAMITEHR INTO PROPRIETE "+ valueName);
                        else parans.put(paranName, paranValue);
                    }
                    value = reader.getValue();
                    if(value != null && value.length() == 0)
                        value= null;
                                        
                    if(hasParns && value != null) 
                        throw new  RuntimeException("NAO PODE DEFINIR O PARAMETRO E OS VALORES AMBOS AO MESMO TEMPO EM UMA SO PROPRIENDADE");
                    
                   if(value == null && !hasParns) 
                       throw new RuntimeException("NEHNUM VALOR ENCONTRADO NA PROPRIEDADE");
                   
                    reader.moveUp();
                /**</Value>*/
                p = style.addPropriete(valueName);
                if(hasParns)
                    for(Map.Entry<String, String> i:parans.entrySet())
                        p.addValue(i.getKey(), i.getValue());
                else p.setDefaultValue(value);
                            
                    
                }
            reader.moveUp();
             /**</Style>*/
            styles.add(style);
        }
         /**</Styles*/
        return styles;
    }

    @Override
    public boolean canConvert(Class type)
    {
        return type.isAssignableFrom(ArrayList.class);
    }
    
    public static XStream prepareStrean()
    {
        XStream stream = new XStream();
        stream.registerConverter(new StyleConverter());
        stream.alias(CONTAINER_STYLE, ArrayList.class);
        return stream;
    }
    
}
