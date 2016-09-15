/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util;

import java.text.NumberFormat;

/**
 *
 * @author Servidor
 */
public class DUStringNumber
{
    public static Number castNumber(String args)
    {
        try
        {
            Number number = NumberFormat.getInstance().parse(args);
            return number;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
    
    
    public static Integer castInteger(String text)
    {
        try
        {
            return Integer.parseInt(text);
        }catch(Exception ex)
        {
            return null;
        }
    }
    
    public static Double castDouble(String text)
    {
        try
        {
            return Double.parseDouble(text);
        }catch(Exception ex)
        {
            return null;
        }
    }
    public static Float castFloat(String text)
    {
        try
        {
            return Float.valueOf(text);
        }catch(Exception ex)
        {
            return null;
        }
    }
    
    public static Short castShort(String text)
    {
        
        try
        {
            return Short.parseShort(text);
        }catch(Exception ex)
        {
            return null;
        }
    }
    public static Long castLong(String text)
    {
        
        try
        {
            return Long.parseLong(text);
        }catch(Exception ex)
        {
            return null;
        }
    }
    public static Byte castByte(String text)
    {
        
        try
        {
            return Byte.parseByte(text);
        }catch(Exception ex)
        {
            return null;
        }
    }
}
