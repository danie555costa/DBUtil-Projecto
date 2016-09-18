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
import st.jigasoft.dbutil.util.xml.References.ClassClass;

/**
 *
 * @author Servidor
 */
public class RefConverter  implements Converter
{
    private static final String TAG_REFERENCES = "References";

    private final String TAG_CLASS = "Class";
    private final String ATB_CLASS_NAME = "name";
    private final String TAG_FIELD = "Field";
    private final String ATB_FIELD_NAME = "name";
    private final String ATB_COUNT_CLASS = "class";
    private final String ATB_COUNT_FIELD = "fields";
    private final String ATB_FIELD_STATUS = "state";
    private final String STATUS_INVALID = "INVALID";
    private final String STATUS_VALID = "VALID";
    private final String ATB_CLASS_STATUS = "status";
    

    @Override
    public void marshal(Object o, HierarchicalStreamWriter xml, MarshallingContext mc)
    {
        References re = (References) o;
        //xml.addAttribute(ATB_COUNT_CLASS, re.countClass()+"");
        re.forEachRow((cla)->
        {
             xml.startNode(TAG_CLASS);
            xml.addAttribute(ATB_CLASS_NAME, cla.getName());
            xml.addAttribute(ATB_COUNT_FIELD, cla.countFields()+"");
            
            if(!cla.isValid()) xml.addAttribute(ATB_CLASS_STATUS, STATUS_INVALID);
            
            cla.forEachRow((f)->
            {
                xml.startNode(TAG_FIELD);
                xml.addAttribute(ATB_FIELD_NAME, f.getName());
                if(! f.isValid())
                    xml.addAttribute(ATB_FIELD_STATUS, STATUS_INVALID);
                xml.setValue(f.getValue());
                xml.endNode();
            });
           
            xml.endNode();
        });
                
        
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader xml, UnmarshallingContext uc) 
    {
        References r = new References();
        while(xml.hasMoreChildren())
        {
            
            xml.moveDown();
            ClassClass cla = r.addClass(xml.getAttribute(ATB_FIELD_NAME));
            while(xml.hasMoreChildren())
            {
                xml.moveDown();
                String nameField= xml.getAttribute(ATB_FIELD_NAME);
                String value = xml.getValue();
                cla.addFiel(nameField, value);
                xml.moveUp();
            }
            xml.moveUp();
        }
        r.ignoreUpgrads();
        return r;
    }

    @Override
    public boolean canConvert(Class type)
    {
        return type.equals(References.class);
    }
    
    /**
     * Criar o Xtrean do ficheiro XML
     * @return 
     */
    public static XStream getStream()
    {
        XStream stream = new XStream();
        stream.alias(TAG_REFERENCES, References.class);
        stream.registerConverter(new RefConverter());
        return stream;
    }
}
