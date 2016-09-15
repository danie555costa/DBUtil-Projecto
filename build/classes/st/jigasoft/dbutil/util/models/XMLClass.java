/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.models;

/**
 * AS Vairiaveis declarado nessa interface não deve ser USADA pada receber outros valores 
 * SE titer que altera os seus valores deve manter os modificador e o tipo do armazenameto da que esta declarada nessa interface
 * @author Servidor
 */
public interface XMLClass
{
    /**
     * CORRESPONDE AO ESTADO DE LIPEZA AUTOMATICA DO FICHIRO XML
     * true - A lipeza sera feita automaticamente no fichiro
     * false - A sera iguinorada
     */
    public final static boolean REF_AUTO_CLEAR = true;
}
