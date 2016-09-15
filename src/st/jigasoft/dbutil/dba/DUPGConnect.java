package st.jigasoft.dbutil.dba;


import java.sql.Connection;
import java.sql.DriverManager;


public class DUPGConnect implements Runnable
{
	private Connection conn;
	private String host = "192.168.43.28";
	private String dataBase = "fiscalizacao";
	private String port = "5432"; 
	private String user = "agenteFisca";
	private String pass = "1234";
	private String url;
	
	private boolean result;
	private String message;
	
	public DUPGConnect()     
	{
		this.setPGConnection(this.host, this.dataBase, this.port, this.user, this.pass);
		this.connectar();
	}
	
	public void setPGConnection(String host, String dataBaseName, String port, String user, String pass)
	{
		this.dataBase = dataBaseName;
		this.host = host;
		this.pass = pass;
		this.user = user;
		this.port = port;
		this.url = "jdbc:postgresql://%s:%s/%s";
		this.url = String.format(this.url, this.host, this.port, this.dataBase);
	}
	
	
	@Override
	public void run()
	{
		//TODO connectar
		try
		{			
			Class.forName("org.postgresql.Driver");
			this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
		}catch(Exception ex)
		{
			this.result =false;
			this.message = ex.getMessage();
		}
	}
	
	public boolean connectar ()
	{
		Thread tre = new Thread(this);
		tre.start();
		try
		{
			tre.join();
			this.result = true;
		}catch (Exception ex)
		{
			this.message = ex.getMessage();
			this.result = false;
		}
		return this.result;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	public void closeConnect ()
	{
		try
		{
			this.conn.close();
			
		}catch(Exception ex)
		{
			this.message = "Erro ao desconectar: \n"+ex.getMessage();
			this.result = false;
		}
		finally
		{
			this.conn = null;
		}
	}
	
	public Connection getConnection()
	{
		return this.conn;
	}
	
	
}
