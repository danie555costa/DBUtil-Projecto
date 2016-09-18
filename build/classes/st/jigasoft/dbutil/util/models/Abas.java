/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package st.jigasoft.dbutil.util.models;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 *
 * @author Servidor
 */
public interface Abas  extends View
{    
    public void goTo(int page);
    
    public void goMy();
    
    /**
     * Enviar uma intencao para uma aba do sistema
     * @param intent O tipo de intencao a ser enviado
     * @param commandCod O codigo da intent
     * @param action A acao que davera ser excutada
     * @param mapDataIntention Os valores do envio da intencao
     */
    public void reciveIntent(IntentType intent, String commandCod, Consumer<Object> action, HashMap<String, Object> mapDataIntention);
    
    
    public static enum  IntentType
    {
        ACCESS,
        GET,
        SEND,
        EXECUT
    }
}
