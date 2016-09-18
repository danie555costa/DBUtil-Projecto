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
public class CallMySQL extends DUExecute implements Call
{
    private static CallMySQL staticInstance;
    private String host;
    private String userName;
    private String pwd;
    private String dbName;
    private final int port;
    private boolean autoCommit;

    /**
     * 
     * @param host
     * @param userName
     * @param pwd
     * @param dbName 
     */
    public CallMySQL(String host, String userName, String pwd, String dbName)
    {
        this.host = host;
        this.userName = userName;
        this.pwd = pwd;
        this.dbName = dbName;
        this.port = DEFAULT_MYSQL_PORT;
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
    @Override
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
     * @return 
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * 
     * @param dbName 
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int getPort()
    {
        return port;
    }

    
    @Override
    public Object callFunction(String functionName, int returnType, Object... inParram)
    {
        return null;
    }

    @Override
    public boolean callProcedure(String procedureName, Object... inParam)
    {
        return false;
    }
    
    /**
     * 
     * @param host
     * @param userName
     * @param pwd
     * @param dataBase 
     */
    public static void newStaticInstance (String host, String userName, String pwd, String dataBase)
    {
        if(CallMySQL.staticInstance != null) CallMySQL.staticInstance.closeConnection();
        CallMySQL.staticInstance = new CallMySQL(host, userName, pwd, userName);
    }
    
    /**
     * 
     * @return 
     */
    public static CallMySQL getStaticInstace()
    {
        return CallMySQL.staticInstance;
    }

    @Override
    public String getUrl() 
    {
        return "jdbc:mysql://"+this.host+":"+this.port+"/"+this.dbName;
    }

    @Override
    public String getDriverName() 
    {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String getMessageError() {
        return null;
    }
}
