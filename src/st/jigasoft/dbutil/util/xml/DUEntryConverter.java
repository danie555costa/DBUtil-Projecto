/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.xml;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Servidor
 */
public class DUEntryConverter  implements Converter
{
    private String nameItem;
    private String nameKey;
    private String nameCount;
    
    private boolean validateCount;
    private boolean requireCount;
    
    public DUEntryConverter(String nameItem, String nameKey, String nameCount)
    {
        this.nameItem = nameItem;
        this.nameKey = nameKey;
        this.nameCount = nameCount;
        requireCount = true;
    }
    
    public void setValidat(boolean validateCount)
    {
        this.validateCount = validateCount;
        if(validateCount) requireCount = true;
    }
    
    public void setRequireCount(boolean requireCount)
    {
        this.requireCount = requireCount;
        if(!requireCount)  this.validateCount = false;
    }
    
    @Override
    public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext mc) 
    {
        HashMap<String, Object> map  =  (HashMap<String, Object>) object;
        if(requireCount)writer.addAttribute(this.nameCount, String.valueOf(map.size()));
        for(Map.Entry<String, Object> e: map.entrySet())
        {
            writer.startNode(this.nameItem);
            writer.addAttribute(this.nameKey, e.getKey());
            writer.setValue(e.getValue().toString());
            writer.endNode();
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc)
    {
        String size = null;
        HashMap<String, String> map = new HashMap<>();
        if(requireCount)
        {
             size = reader.getAttribute(this.nameCount);
            if(size == null) throw  new RuntimeException("Size map undefined!");
        }
        
        while(reader.hasMoreChildren())
        {
            reader.moveDown();
            String key = reader.getAttribute(this.nameKey);
            String value = reader.getValue();
            map.put(key, value);
            reader.moveUp();
        }
        if(requireCount && !size.equals(String.valueOf(map.size()))) 
            throw new RuntimeException("invalide size : XML size = "+size+ " MAP size = "+map.size());
        
        return  map;
    }

    @Override
    public boolean canConvert(Class type) 
    {
        return type.isAssignableFrom(Map.Entry.class);
    }
}
