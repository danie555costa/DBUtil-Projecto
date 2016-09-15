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
public class DUCallSQLServel extends  DUExecute implements Call
{
    private static DUCallSQLServel staticInstance;
    private String host;
    private String userName;
    private String pwd;
    private String dbName;
    private int port;

    /**
     * 
     * @param host
     * @param userName
     * @param pwd
     * @param dbName 
     */
    public DUCallSQLServel(String host, String userName, String pwd, String dbName) {
        this.host = host;
        this.userName = userName;
        this.pwd = pwd;
        this.dbName = dbName;
        this.port = DEFAULT_SQLSERVER_PORT;
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
    public int getPort() {
        return port;
    }

    /**
     * 
     * @param port 
     */
    public void setPort(int port) {
        this.port = port;
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
     * @param host
     * @param userName
     * @param pwd
     * @param dataBase 
     */
    public static void newStaticInstance (String host, String userName, String pwd, String dataBase)
    {
        if(DUCallSQLServel.staticInstance != null) DUCallSQLServel.staticInstance.closeConnection();
        DUCallSQLServel.staticInstance = new DUCallSQLServel(host, userName, pwd, userName);
    }
    
    /**
     * 
     * @return 
     */
    public static DUCallSQLServel getStaticInstace()
    {
        return DUCallSQLServel.staticInstance;
    }

    @Override
    public String getUrl()
    { 
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDriverName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMessageError() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
