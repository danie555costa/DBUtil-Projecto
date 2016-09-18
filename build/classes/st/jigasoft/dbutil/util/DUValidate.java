/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util;

/**
 *
 * @author Servidor
 */
public class DUValidate 
{
    public static boolean isText (String arg) 
    {
        if(arg == null) return false;
        boolean valid;
        for(char c: arg.toCharArray())
        {
            valid = (c >= 'a' && c <= 'z')
                        ||(c >= 'A' && c <= 'Z');
            if(!valid) return false;
        }
        return true;
    }
    
    
    public static final boolean isNumber(String arg)
    {
        if(arg == null) return false;
        arg = arg.toUpperCase();
        boolean validChar;
        int count =0;
        for(char c: arg.toCharArray())
        {
            validChar = (c>='0' && c <= '9')
                    || c == '.'
                    || (c == '-' && count ==0)
                    || (count == arg.length()-1 
                        && ((arg.length()>1 && arg.charAt(0) != '-')
                               ||(arg.length()>2) )
                        && (c == 'D' 
                            || c == 'F'
                            || c == 'L'))
                    ;
            count ++;
            if (!validChar) return  false;
        }
        return true;
        
    }
    
    
}
