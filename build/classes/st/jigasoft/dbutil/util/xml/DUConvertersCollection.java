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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Servidor
 */
public class DUConvertersCollection
{
    private abstract static class AbstractConverterCollection implements Converter
    {
        String itemTagName;
        String sizeName;
        
        public  void setItemTagName(String nameItem)
        {
            this.itemTagName = nameItem;
        }
        
        public  void setSizeName(String sizeName)
        {
            this.sizeName = sizeName;
        }
    }
            
    public static class SampleList extends AbstractConverterCollection
    {
       
        @Override
        public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context)
        {
            ArrayList<String> list = (ArrayList<String>) o;
            if(this.itemTagName == null) itemTagName = "item";
            if(this.sizeName != null) writer.addAttribute(sizeName, String.valueOf(list.size()));
            
            for(String s: list)
            {
                writer.startNode(itemTagName);
                writer.setValue(s);
                writer.endNode();
            }
        }

        @Override
        public ArrayList<String> unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc)
        {
            ArrayList<String> list = new ArrayList<>();
            String nodName;
            String values;
            while(reader.hasMoreChildren())
            {
                reader.moveDown();
                values = reader.getValue();
                nodName = reader.getNodeName();
                list.add(values);
                reader.moveUp();
            }
            return list;
        }

        @Override
        public boolean canConvert(Class type)
        {
            return type.isAssignableFrom(List.class)
                    ||type.isAssignableFrom(ArrayList.class);
        }
    }
    
    
    
    public static class SampleMap extends AbstractConverterCollection
    {
        private String nameKey;
        
        public void setNameKey(String nameKey)
        {
            this.nameKey = nameKey;
        }
        
        @Override
        public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext mc)
        {
            HashMap<String, String> list = (HashMap<String, String>) o;
            
            autoRename();
            if(this.sizeName != null) writer.addAttribute(sizeName, String.valueOf(list.size()));
            
            
            for(Map.Entry<String, String> k : list.entrySet())
            {
                writer.startNode(this.itemTagName);
                writer.addAttribute(this.nameKey, k.getKey());
                writer.setValue(k.getValue());
                writer.endNode();
            }
        }

        private void autoRename()
        {
            if(this.itemTagName == null) itemTagName = "item";
            if(this.nameKey == null) nameKey = "key";
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
        {
            HashMap<String, String> map = new HashMap<>();
            String size = null;
            String key = null;
            String value = null;
            int count = 0;
            
            autoRename();
            if(this.sizeName != null) size = reader.getAttribute(sizeName);
            
            
            while (reader.hasMoreChildren()) 
            {
                reader.moveDown();
                key = reader.getAttribute(this.nameKey);
                value = reader.getValue();
                if(key == null) throw new RuntimeException("\nCannot find the key at item "+count);
                if(value == null) throw new RuntimeException("\nCannot find the value at item "+count);
                if(reader.hasMoreChildren()) throw new RuntimeException("\nThe item can not accept sub-items");
                
                map.put(key, value);
                reader.moveUp();
                count ++;
            }
            if(this.sizeName != null && size == null) throw new RuntimeException("\nCannot find the size");
            else if(size != null)
            {
                int iSize =Integer.parseInt(size);
                if(iSize != count) throw new RuntimeException("\nThe sizes are not equal");
            }
            return map;
        }

        @Override
        public boolean canConvert(Class type)
        {
            return type.isAssignableFrom(HashMap.class)
                    ||type.isAssignableFrom(Map.class);
        }
    }
}
