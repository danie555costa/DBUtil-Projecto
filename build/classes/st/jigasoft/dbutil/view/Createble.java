/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.view;

/**
 *
 * @author xdata
 */
public interface Createble {
    
    default void onPreCreate() {}
    
    void onCreate();
    
    default void onPosCreated(){}
}
