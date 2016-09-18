package st.jigasoft.dbutil.dba;

/**
 *
 * @author Servidor
 */
public class DUCallSQLite extends DUExecute
{
    private static DUCallSQLite staticInstance;
    public String localFile;

    /**
     * 
     * @param localFile 
     */
    public DUCallSQLite(String localFile)
    {
        this.localFile = localFile;
    }

    /**
     * 
     * @return 
     */
    public String getLocalFile() {
        return localFile;
    }

    /**
     * 
     * @param localFile 
     */
    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }
    
    /**
     * 
     * @return 
     */
    public static DUCallSQLite getStaticInstace()
    {
        return DUCallSQLite.staticInstance;
    }

    @Override
    public String getUrl() {
        return "jdbc:sqlite:"+localFile;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPwd() {
        return null;
    }

    @Override
    public String getDriverName() {
        return null;
    }

    @Override
    public String getMessageError() {
        return null;
    }

    @Override
    public int getPort() {
        return -1;
    }
}
