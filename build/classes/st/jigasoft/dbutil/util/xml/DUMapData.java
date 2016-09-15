/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.io.File;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.HashMap;
import javafx.scene.input.DataFormat;


public class DUMapData
{
    public static DUMapData loadDatas(String txt)
    {
        XStream xStream = defaultXStream();
        return (DUMapData) xStream.fromXML(txt);
    }
    
    public static DUMapData loadDatas(File file)
    {
        XStream xStream = defaultXStream();
        return (DUMapData) xStream.fromXML(file);
    }
    
    public static XStream defaultXStream()
    {
        XStream xStream = new XStream();
        ConverteReader c = new ConverteReader();
        xStream.alias("groups-data", DUMapData.class);
        xStream.registerConverter(c);
        return xStream;
    }
    
    public HashMap<String, Group> groupsMap = new HashMap<>();

    public Group getGroup(String groupName) 
    {
        return this.groupsMap.get(groupName);
    }
    
    public Object getItem(String groupName, String itemName) 
    {
        return this.groupsMap.get(groupName).getItem(itemName);
    }
    
    public Class getType(String groupName) 
    {
        return this.groupsMap.get(groupName).types;
    }
    
    public int countGroups()
    {
        return this.groupsMap.size();
    }
    
    public String [] getGroupsNames()
    {
        String [] names = new String[groupsMap.keySet().toArray().length];
        int count =0;
        for(Object o: groupsMap.keySet().toArray())
            names[count ++] = o.toString();
        return names;
    }
    
    public static class Group
    {
        Class types;
        private HashMap<String, Object> itemsMap;

        public Group(Class types)
        {
            this.types = types;
            itemsMap = new HashMap<>();
        }
        
        
        
        public Object getItem(String itemName)
        {
            return itemsMap.get(itemName);
        }
        
        public Class getType()
        {
            return types;
        }
        
        public int countItem()
        {
            return itemsMap.size();
        }
        
        public String [] getItemsName()
        {
            String [] names = new String[itemsMap.keySet().toArray().length];
            int count =0;
            for(Object o: itemsMap.keySet().toArray())
                names[count ++] = o.toString();
            return names;
        }
    }
    
    
    
    
    private static class ConverteReader implements Converter
    {

        @Override
        public void marshal(Object inObject, HierarchicalStreamWriter writer, MarshallingContext mc)
        {
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
        {
            DUMapData mapData = new  DUMapData();
            int groupCounts = 0;
            while(reader.hasMoreChildren())
            {
                reader.moveDown();
                
                String id = reader.getAttribute("id");
                String classType = reader.getAttribute("class");
                String nodName = reader.getNodeName();
                Class jClass = null;
                
                
                if(!nodName.equals("group"))  throw new RuntimeException("\nunknown  tag in XML -> TAG: "+nodName +" at "+groupCounts);
                if(id == null) throw new RuntimeException("\nid not found in group: "+groupCounts);
                if (mapData.groupsMap.containsKey(id)) throw new RuntimeException("\nGroup duplicated in XML -> group id=\""+id+"\" index="+groupCounts);
                if(classType == null || classType.isEmpty()) throw new RuntimeException("\ntype not found in group -> group id=\""+id+"\" index="+groupCounts);
                
                if(classType.equalsIgnoreCase("Byte")) jClass = Byte.class;
                else if(classType.equalsIgnoreCase("Boolean")) jClass = Boolean.class;
                else if(classType.equalsIgnoreCase("Integer")) jClass = Integer.class;
                else if(classType.equalsIgnoreCase("Double")) jClass = Double.class;
                else if(classType.equalsIgnoreCase("Long")) jClass = Long.class;
                else if(classType.equalsIgnoreCase("String")) jClass = String.class;
                else if(classType.equalsIgnoreCase("Float")) jClass = Float.class;
                else throw new RuntimeException("\nUnsuported class -> class=\""+classType+"\" group id=\""+id+"\" index="+groupCounts);
                
                Group group = new Group(jClass);
                
                mapData.groupsMap.put(id, group);
                
                int item = 0;
                while (reader.hasMoreChildren())
                {
                    reader.moveDown();
                    String nameItem = reader.getAttribute("name");
                    String sValue = reader.getValue();
                    nodName = reader.getNodeName();
                    
                    if(nameItem == null) throw new RuntimeException("\nitem name not found in group: "+id + " at "+item);
                    else if(sValue == null ||sValue.isEmpty()) throw new RuntimeException("\nitem value not found in group -> group-id=\""+id+"\" index="+item + " | item name=\""+nameItem+"\"");
                    else if(group.itemsMap.containsKey(nameItem))throw new RuntimeException("\nitem Dupicated in group -> group-id=\""+id+"\" index="+item + " | item name=\""+nameItem+"\"");
                    if(reader.hasMoreChildren()) throw new RuntimeException("\nitem cannot accept children group -> group-id=\""+id+"\" index="+item + " | item name=\""+nameItem+"\"");
                    if(!nodName.equals("item"))  throw new RuntimeException("\nunknown  tag in XML -> TAG: "+nodName + " at group-id=\""+id+"\" index="+item + " | item name=\""+nameItem+"\"");
                    
                    Object value = (String.class.equals(jClass))? sValue
                            :(Boolean.class.equals(jClass))? Boolean.valueOf(sValue)
                            :(Byte.class.equals(jClass))? Byte.valueOf(sValue)
                            :(Integer.class.equals(jClass))? Integer.valueOf(sValue)
                            :(Long.class.equals(jClass))? Long.valueOf(sValue)
                            :(Double.class.equals(jClass))? Double.valueOf(sValue)
                            :sValue;
                   
                    if((jClass.equals(Boolean.class))
                        && !(sValue.equalsIgnoreCase("true") || sValue.equalsIgnoreCase("false")))
                        throw new RuntimeException("Inalid Boolean value -> value = "+sValue+" group-id=\""+id+"\" index="+item + " | item name=\""+nameItem+"\"");
                    
                    group.itemsMap.put(nameItem, value);
                    reader.moveUp();
                    item ++;
                }
                reader.moveUp();
                groupCounts ++;
            }
            return mapData;
        }

        @Override
        public boolean canConvert(Class type)
        {
            return type.isAssignableFrom(DUMapData.class);
        }
    }
}
