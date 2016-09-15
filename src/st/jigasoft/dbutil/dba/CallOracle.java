/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.dba;

import java.sql.ResultSet;

/**
 *
 * @author Servidor
 */
public class CallOracle extends DUExecute implements Call
{
    private static String dbArrayName;
    private static CallOracle staticInstance;
    
    private String host;
    private String userName;
    private String pwd;
    private int port;

    /**
     * 
     * @param host
     * @param userName
     * @param pwd 
     */
    public CallOracle(String host, String userName, String pwd) {
        this.host = host;
        this.userName = userName;
        this.pwd = pwd;
        this.port = DEFAULT_ORACLE_PORT;
    }

    /**
     * 
     * @return 
     */
    public String getHost() {
        return host;
    }

    /**
     * 
     * @param host 
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 
     * @return 
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @param userName 
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getPwd() {
        return pwd;
    }

    /**
     * 
     * @param pwd 
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    

    /**
     * 
     * @param port 
     */
    public void setPort(int port) {
        this.port = port;
    }
    
    /**
     * 
     * @param dbArrayName 
     */
    public static void setDbArrayName(String dbArrayName) {
        CallOracle.dbArrayName = dbArrayName;
    }
    
    @Override
    public Object callFunction(String functionName, int returnType, Object... inParram) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean callProcedure(String procedureName, Object... inParam) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @param funtion
     * @param inParam
     * @return 
     */
    @Override
    public ResultSet selectValue(String funtion, Object ... inParam)
    {
        if(inParam != null 
                && funtion != null
                && inParam.length >0)
            funtion = funtion+"("+creatInterogation(inParam.length)+")";
        if(funtion == null) return null;
        return super.select("DUAL", funtion, inParam);
    }
    
    /**
     * 
     * @param functionName
     * @param column
     * @param inParam
     * @return 
     */
    public ResultSet callFunctionTable (String functionName, String column, Object ... inParam)
    {
        if(functionName != null)
        {
            if(inParam != null && inParam.length >0) 
                functionName = functionName+"("+creatInterogation(inParam.length)+")";
            functionName = Call.FUNC_TABLE+"("+functionName+")";
            return select(functionName, column, inParam);
        }
        return null;
    }
    
    /**
     * 
     * @param host
     * @param userName
     * @param pwd 
     */
    public static void newStaticInstance (String host, String userName, String pwd)
    {
        if(CallOracle.staticInstance != null) CallOracle.staticInstance.closeConnection();
        CallOracle.staticInstance = new CallOracle(host, userName, pwd);
    }
    
    /**
     * 
     * @return 
     */
    public static CallOracle getStaticInstace()
    {
        return CallOracle.staticInstance;
    }

    @Override
    public String getUrl() 
    {
        return "jdbc:oracle:thin:@"+this.host+":"+this.port+":XE";
    }


    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public String getDriverName() {
        return "oracle.jdbc.driver.OracleDriver";
    }

    @Override
    public String getMessageError() {
        return null;
    }

    
    

}
