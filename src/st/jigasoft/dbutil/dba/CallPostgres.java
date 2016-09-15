/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.dba;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Servidor
 */
public class CallPostgres extends DUExecute implements Call
{
    private static CallPostgres staticInstance;
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
    public CallPostgres(String host, String userName, String pwd, String dbName) {
        this.host = host;
        this.userName = userName;
        this.pwd = pwd;
        this.dbName = dbName;
        this.port = DEFAULT_POSTGRES_PORT;
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

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

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

    /**
     * 
     * @param functionName
     * @param returnType
     * @param inParram
     * @return 
     */
    @Override
    public Object callFunction(String functionName, int returnType, Object... inParram)
    {
        try 
        {
            String sql = "{? = call "+functionName+"("+super.creatInterogation((inParram != null)? inParram.length: 0) + ")}";
            System.out.println(sql);
            Object result = null;
            //Registrar os parametros de saida
            try (CallableStatement call = super.getConnection().prepareCall(sql)) 
            {
                //Registrar os parametros de saida
                
                call.registerOutParameter(1, returnType);
                boolean resultMap = this.mapParams(call, 2, inParram);
                if(resultMap)
                {
                    call.execute();
                    result = call.getObject(1);
                }
            }
            System.out.println("Result = "+result);
            super.closeConnection();
            return  result;
        } catch (Exception ex) {
            Logger.getLogger(CallPostgres.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro Desconhecido\n"+ex.getMessage();
        }
    }

    /**
     * 
     * @param procedureName
     * @param inParam
     * @return 
     */
    @Override
    public boolean callProcedure(String procedureName, Object... inParam) {
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
        if(CallPostgres.staticInstance != null) CallPostgres.staticInstance.closeConnection();
        CallPostgres.staticInstance = new CallPostgres(host, userName, pwd, dataBase);
    }
    
    /**
     * 
     * @return 
     */
    public static CallPostgres getStaticInstace()
    {
        return CallPostgres.staticInstance;
    }

    @Override
    protected boolean mapParams(PreparedStatement call, int i, Object ... inParram)
    {
        return super.mapParams(call, i, inParram);
    }

    public ResultSet selectFromFunction(String tableName, String columnNames, Object ... inParam) 
    {
        tableName = tableName + "("+creatInterogation(inParam.length)+")";
        return select(tableName, columnNames, inParam);
    }

    @Override
    public String getUrl() 
    {
        return "jdbc:postgresql://"+this.host+":"+this.port+"/"+this.dbName;
    }

    @Override
    public String getDriverName() 
    {
        return "org.postgresql.Driver";
    }

    @Override
    public String getMessageError()
    {
        return null;
    }
    
}
