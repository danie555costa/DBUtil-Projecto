/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util;

import java.util.ArrayList;

/**
 *
 * @author Servidor
 */
public class DUString
{
    /**
     * Criar uma lista com os valores que paracerem o valor desejado a partir de uma lista grande
     * Os valores que serao encomtrado pode ser no inicio ou no fim da frase ou no meio da frase
     * @param list A lista com os valores todos
     * @param like O valor que se deseja encotrar na lista
     * @return A lista com os valores desejados
     */
    public static ArrayList<String> likeBetween(ArrayList<String> list, String like)
    {
        if(like == null || list == null) return null;
        ArrayList<String> likeList = new ArrayList<>();
        if(like.length() == 0)
        {
            likeList.addAll(list);
            return likeList;
        }
        
        for(String s: list)
        {
            if(s.toUpperCase().contains(like.toUpperCase()))
            {
                likeList.add(s);
            }
        }
        
        return likeList;
    }
    
     /**
     * Criar uma lista com os valores que paracerem o valor desejado a partir de uma lista grande
     * Os valores que serao encomtrado sera somente os do inicio da frase
     * @param list A lista com os valores todos
     * @param like O valor que se deseja encotrar na lista
     * @return A lista com os valores desejados
     */
    public static ArrayList<String> likeStart(ArrayList<String> list, String like)
    {
        if(like == null || list == null) return null;
        ArrayList<String> likeList = new ArrayList<>();
        if(like.length() == 0)
        {
            likeList.addAll(list);
            return likeList;
        }
        String aux;
        for(String s: list)
        {
            aux = s;
            if(s.length() >= like.length())
            {
                s=s.substring(0, like.length());
                if(s.toUpperCase().contains(like.toUpperCase()))
                {
                    likeList.add(aux);
                }
            }
            
        }
        
        return likeList;
    }
    
     /**
     * Criar uma lista com os valores que paracerem o valor desejado a partir de uma lista grande
     * Os valores que serao encomtrado sera somente os do fim da frase
     * @param list A lista com os valores todos
     * @param like O valor que se deseja encotrar na lista
     * @return A lista com os valores desejados
     */
    public static ArrayList<String> likeEnd(ArrayList<String> list, String like)
    {
        if(like == null || list == null) return null;
        ArrayList<String> likeList = new ArrayList<>();
        if(like.length() == 0)
        {
            likeList.addAll(list);
            return likeList;
        }
        String aux;
        for(String s: list)
        {
            aux = s;
            if(s.length() >= like.length())
            {
                s=s.substring(s.length()-like.length(), s.length());
                if(s.toUpperCase().contains(like.toUpperCase()))
                {
                    likeList.add(aux);
                }
            }
            
        }
        return likeList;
    }
    
    /**
     * Completar o caravter no inicio da string ate obtero o tamanho final
     * @param argment
     * @param completion
     * @param finalLength
     * @return 
     */
    public static String completIni (String argment, char completion , int finalLength)
    {
        if(argment == null) return null;
        for(int i = argment.length(); i<finalLength; i++)
            argment = completion + argment;
        return argment;
    }
    
    /**
     * Completar o caraver no fianl da String ate obter o tamnho final
     * @param argment
     * @param completion
     * @param finalLength
     * @return 
     */
    public static String completFin (String argment, char completion , int finalLength)
    {
        if(argment == null) return null;
        for(int i = argment.length(); i<finalLength; i++)
            argment = completion + argment;
        return argment;
    }
  
}
