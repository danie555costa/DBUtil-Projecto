package st.jigasoft.dbutil.dba;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import st.jigasoft.dbutil.util.DUDateTime;
import java.sql.DriverManager;

/**
 *
 * @author Servidor
 */
public abstract class DUExecute
{
    private Connection connection;
    private static boolean showParam;
    private boolean autoCommit;
    
    public DUExecute()
    {
        this.autoCommit = true;
    }
    
    /**
     * Criar uma nova connexao para a base de dados que se pretende atinguir
     * @return 
     */
    protected Connection  creatNewConnection ()
    {
        return genericConnection(getUrl(), getDriverName(), getUserName(), getPwd(), getMessageError());
    }
    
    public abstract  String getUrl();
    public abstract  String getUserName();
    public abstract  String getPwd();
    public abstract  String getDriverName();
    public abstract  String getMessageError();
    public abstract  int getPort();
    
    
    /**
     * 
     * @param autoCommit 
     */
    public void setAutoCommit(boolean autoCommit)
    {
        this.autoCommit = autoCommit;
    }
    
    /**
     * 
     * @return 
     */
    public boolean commit()
    {
        try 
        {
            this.connection.commit();
            return true;
        } catch (SQLException ex) 
        {
            Logger.getLogger(DUExecute.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * 
     * @return 
     */
    public boolean rollback()
    {
        try 
        {
            this.connection.rollback();
            return true;
        } catch (SQLException ex) 
        {
            Logger.getLogger(DUExecute.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
    }
    
    /**
     * Obter a connexao actual
     * @return 
     */
    protected Connection getConnection ()
    {
        try
        {
            if(this.connection == null
                    || this.connection.isClosed())
            {
                try{this.connection.close();}catch(Exception ex){}
                this.connection = null;
                this.connection = creatNewConnection();
                if(this.connection != null)
                    this.connection.setAutoCommit(autoCommit);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DUExecute.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  this.connection;
    }
    
    /**
     * Fechar a connexao
     */
    public void  closeConnection ()
    {
        try 
        {
            this.connection.close();
            this.connection = null;
        } catch (SQLException ex) {}
    }
    
    
    /**
     * Criar as interogacao para as chamadas de base de dados
     * @param nunInterogation
     * @return 
     */
    protected String creatInterogation(int nunInterogation)
    {
        String interogations = "";
        for (int i =0; i<nunInterogation; i++) 
            interogations = interogations + "?" +((i+1 <nunInterogation)? ", ": "");
        return interogations;
    }
    
    /**
     * 
     * @param tableName
     * @param columnSelect
     * @param inParam
     * @return 
     */
    public ResultSet select(String tableName, String columnSelect, Object ... inParam)
    {
        try
        {
            if(columnSelect == null || columnSelect.isEmpty()) columnSelect = "*";
            String sql = "SELECT "+columnSelect+ " FROM "+tableName;
            System.out.println(sql);
            PreparedStatement prep = this.getConnection().prepareStatement(sql);
            int objCount =1;
            if(inParam != null)
                this.mapParams(prep, objCount, inParam);
           return prep.executeQuery();
        }catch(Exception ex)
        {
            Logger.getLogger(DUExecute.class.getName()).log(Level.SEVERE, getMessageError(), ex);
        }
        return null;
    }
    
    /**
     * 
     * @param functionName
     * @param inParam
     * @return 
     */
    public Object selectValue (String functionName, Object ... inParam)
    {
        try {
            String sql = "SELECT "+functionName+"("+this.creatInterogation((inParam != null)?inParam.length:0)+")";
            Object resp;
            try (PreparedStatement prep = this.getConnection().prepareStatement(sql)) 
            {
                int objCount =1;
                if(inParam != null)
                    for(Object o: inParam)
                    {
                        System.out.println("PARAM "+objCount+" : "+o);
                        prep.setObject(objCount++, o);
                    }
                prep.execute();
                ResultSet rs = prep.getResultSet();
                rs.next();
                resp = rs.getObject(1);
            }
            this.closeConnection();
            return resp;
        } catch (SQLException ex) {
            Logger.getLogger(DUExecute.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean insert(String tableName, String explicitColumn, Object ... inValues)
    {
        return false;
    }
    
    /**
     * 
     * @param tableName
     * @param columnSet
     * @param clauseWhere
     * @param inValues
     * @return 
     */
    public int update (String tableName, String columnSet, String clauseWhere, Object ... inValues)
    {
        return -1;
    }
    
    /**
     * 
     * @param tableName
     * @param whereCaluse
     * @param inValues
     * @return 
     */
    public int delete (String tableName, String whereCaluse, Object ... inValues)
    {
        String sql = "DELETE FROM "+tableName + " WHERE " + whereCaluse;
        try
        {
            PreparedStatement prepSmt  =  getConnection().prepareCall(sql);
            int count = 0;
            for(Object o: inValues)
                prepSmt.setObject(count++, o);
            int result = prepSmt.executeUpdate();
            return result;
        }catch (Exception ex)
        {
        }
        return  -1;
    }
    
    public boolean testNewConnection()
    {
        try 
        {
            Connection con = this.creatNewConnection();
            if(con != null && con.isValid(5000))
            {
                con.close();
                return true;
            }
        } catch (Exception e) {
            
        }
        return false;
    }
    
    /**
     * Esse procedimento serve para receber uma String e executar o quere imbutida nela
     * @param sqlQuere O quere que sera executado
     * @param action A acao que o query ira receber 
     * @param imParam Os parametros a entrar no query
     * @return 
     */
    public ResultSet executQuere (String sqlQuere, Consumer<ResultSet> action, Object ... imParam)
    {
        return null;
    }
    
    /**
     * Esse procedimento sere para executar uma atulizacao na base de dados 
     * @param sqlUpdate O comando a acao a ser executada na base de dados
     * @param action A acao que sera executado no java pos terminar de executar a acao em base de dados
     * @param imParam Os parametros que irao entrar para na execucao do comando 
     * @return  
     */
    public int executUpdate (String sqlUpdate, Consumer<CallableStatement> action, Object ... imParam)
    {
        return -1;
    }
    
    

    /**
     * 
     * @return 
     */
    public static boolean isShowParam() 
    {
        return showParam;
    }

    /**
     * 
     * @param showParam 
     */
    public static void setShowParam(boolean showParam) {
        DUExecute.showParam = showParam;
    }

    protected boolean mapParams(PreparedStatement prep, int count, Object[] inParram)
    {
        try
        {
            Object o;
            for (Object inParram1 : inParram)
            {
                o = inParram1;
                if(o instanceof Date)
                {
                    o = DUDateTime.toSQLDate((Date) o);
                }
                System.out.println("PARAM "+count+" = "+o+"   |   "+(o != null? o.getClass(): ""));
                prep.setObject(count++, o);
            }
            return true;
        }catch(Exception ex)
        {
            Logger.getLogger(DUExecute.class.getName()).log(Level.SEVERE, getMessageError(), ex);
            return false;
        }
    }
    
    
    /**
     * Essa funcoa recebe os parametros e faz a connexao de uma forma generica com as base de dados
     * @param url
     * @param className
     * @param userName
     * @param pwd
     * @param messageError
     * @return 
     */
    private static Connection genericConnection (String url, String className, String userName, String pwd, String messageError)
    {
        try
        {
            Connection con = null;
            if(className != null) Class.forName(className);
            if(userName != null && pwd !=null && url != null) 
                con =  DriverManager.getConnection(url, userName, pwd);
            else if(url != null && userName == null && pwd == null) 
                con = DriverManager.getConnection(url);
            return con;
        } catch (ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(DUExecute.class.getName()).log(Level.SEVERE, messageError, ex);
        }
        return null;
    }
    
}
