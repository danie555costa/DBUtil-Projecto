/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.dba;


/**
 *
 * @author Servidor
 */
public interface Call
{    
    int DEFAULT_MYSQL_PORT = 3306;
    int DEFAULT_ORACLE_PORT = 1521;
    int DEFAULT_POSTGRES_PORT = 5432;
    int DEFAULT_SQLSERVER_PORT = -1;
    
    String  FUNC_CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP",
            FUNC_CURRENT_DATE = "CURRENT_DATE",
            FUNC_TO_NUMBER = "TO_NUMBER",
            FUNC_TO_CHAR = "TO_CHAR",
            FUNC_TO_DATE = "TO_DATE",
            FUNC_TO_TIMESTAMP = "TO_TIMESTAMP",
            FUNC_TABLE = "TABLE",
            FUNC_NOW = "NOW",
            FUNC_CAST = "CAST",
            FUNC_LENGTH = "LENGTH"
            ;
    
    /**
     * 
     * @param functionName
     * @param returnType
     * @param inParram
     * @return 
     */
    public Object callFunction (String functionName, int returnType, Object ... inParram);
    
    
    /**
     * 
     * @param procedureName
     * @param inParam
     * @return 
     */
    public boolean callProcedure (String procedureName, Object ... inParam);
    
    
}
