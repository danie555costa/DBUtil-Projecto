/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.sockt;

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Servidor
 */
public class ServerSocketListiner implements Runnable
{
    private final HashMap<String, ClientNet> canals;
    private boolean continueListiner;
    private ClientSelector clientSelector;
    private final ArrayList<OnNetIntentTreater> listTreater;
    private NetIntentConverter converter;
    private final XStream xstream;
    private ServerSocket server;
    private Thread thread;
    private final int priority;
    private final int port;
    private String registredName;
    private final ArrayList<InetAddress> listIp;
    
    public ServerSocketListiner(int port, String registredName, ClientSelector selector, NetIntentConverter converter)
    {
        this.clientSelector = selector;
        this.canals = new HashMap<>();
        this.listTreater = new  ArrayList<>();
        this.listIp = new ArrayList<>();
        this.converter = converter;
        this.xstream = new XStream();
        this.priority = Thread.MIN_PRIORITY;
        this.port = port;
        this.registredName = registredName;
        
        xstream.registerConverter(new NetIntentConverter());
        xstream.alias(this.converter.getTagRoot(), NetIntent.class);
        selector.onAttachSelector(this.canals);    
    }

    
   
    public void start()
    {
        try
        {
            this.server = new ServerSocket(this.port); 
            this.listIp.clear();
            this.canals.clear();
            
            this.continueListiner = true;
            this.thread = new Thread(this);
            this.thread.setPriority(priority);
            this.thread.start();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() 
    {
        //Listiner the net
        while(this.continueListiner)
        {
            Socket socketUser;
            try 
            {
                socketUser = server.accept();
                if(socketUser!=null)
                {
                    System.out.println("NEW SOCKET INTENT :"+socketUser.getInetAddress().getHostAddress());
                    if(this.listIp.contains(socketUser.getInetAddress()))
                    {
                        System.err.println("Has Inet connected "+socketUser.getInetAddress());
                        socketUser.close();
                    }
                    else 
                    {
                        System.err.println("New cliente comfirmed");
                        listIp.add(socketUser.getInetAddress());
                        ClientNet request = new ClientNet(socketUser);
                        clientSelector.onNewClientInstance(request);
                        Thread th = new  Thread(request);
                        th.start();
                    }
                }
                else
                {
                    System.out.println("Invalid soket: the soket is null");
                }
            } catch (IOException ex) 
            {
                Logger.getLogger(ServerSocketListiner.class.getName()).log(Level.SEVERE, "SocketLisniner", ex);
            }
        }
    }
    
     
    /**
     * Stop the server
     * break the socket server and end socket service
     */
    public void stop()
    {
        try 
        {
            if(this.server == null 
                    || this.thread == null) return;
            clientSelector.onServerClosed();
            for(OnNetIntentTreater onit: this.listTreater)
                onit.onServerClosed();
            
            this.continueListiner = false;
            this.thread.interrupt();
            this.server.setSoTimeout(0);
            this.server.close();
            for(Map.Entry<String, ClientNet> item: this.canals.entrySet())
                item.getValue().disconnect();
            this.canals.clear();
            this.listIp.clear();
            
        } catch (Throwable ex)
        {
            Logger.getLogger(ServerSocketListiner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public int getPriority() {
        return priority;
    }

    public ClientSelector getClientSelector() {
        return clientSelector;
    }

    public void setClientSelector(ClientSelector clientSelector) {
        this.clientSelector = clientSelector;
    }

    public NetIntentConverter getConverter() {
        return converter;
    }

    public void setConverter(NetIntentConverter converter) {
        this.converter = converter;
    }

    public int getPort() {
        return port;
    }
    
    public void setSelector(ClientSelector clientSelector)
    {
        this.clientSelector = clientSelector;
    }
    
    public void addNetIntentTreater(OnNetIntentTreater netIntentTreater)
    {
        this.listTreater.add(netIntentTreater);
    }

    private String getServerName() 
    {
        return this.registredName;
    }

    public String getRegisterName() 
    {
        return this.registredName;
    }
    
    public  class ClientNet implements Runnable
    {
        private ObjectOutputStream outData;
        private ObjectInputStream inData;
        private String identifier;
        private Socket userSocket;
        private boolean continueRead;
        private boolean runing;

        
        private ClientNet(Socket userSocket)
        {
            try 
            {            
                this.outData = new ObjectOutputStream(userSocket.getOutputStream());
                this.inData = new ObjectInputStream(userSocket.getInputStream());
                this.userSocket = userSocket;
                this.continueRead = true;
                
            } catch (IOException ex) 
            {
            	ex.printStackTrace();
            }
        }
        
        
        @Override
        public void run() 
        {
            if(runing) return;
            this.runing = true;
            try 
            {
                String xml="";
                //Listner the connectd client
                while (((xml= (String) this.inData.readObject()) != null) && this.continueRead)
                {
                    System.out.println("XML: "+xml);
                    NetIntent data = (NetIntent) xstream.fromXML(xml);
                    System.out.println(getClass().getSimpleName()+"-> SENDED XML AT: "+data.getSendTime());
                    System.out.println(getClass().getSimpleName()+"-> RECIVED XML AT: "+getCurrentTimeStamp());
                    continueRead = this.treatRequest(data, xml);
                    if(!continueRead) break;
                }
                disconnect();
            } 
            catch (Exception ex) 
            {
                System.out.println("ERROR IN CONNECTION");
                ex.printStackTrace();
                try 
                {
                    System.out.println("AUTO DISCONNECTING SERVICE");
                    disconnect();
                } catch (Exception ex1) 
                {
                }
            }
        }

        public boolean isRuning() {
            return runing;
        }
        
        /**
         * Trarar o novo intente
         * @param intent 
         */
        
        private boolean treatRequest(NetIntent intent, String xml) 
        {
            String key = intent.getSender();
            boolean accepted = false;
            if(!canals.containsKey(key))
            {
                if(intent.getIntent() == NetIntent.Intent.CONNECT
                        && intent.getReciver().equals(getServerName())
                        && clientSelector.accept(intent)) 
                {
                    canals.put(key, this);
                    intent.setResult(NetIntent.Result.SUCESS);
                    this.identifier = intent.getSender();
                    
                    clientSelector.onNewClientRegistred(this);
                    for (OnNetIntentTreater treater : listTreater) 
                    {
                        treater.onNewClientRegistred(this);
                    }
                    
                    this.sendIntent(intent);
                    return true;
                }
                else 
                {
                    clientSelector.onNewClientRegeted(this);
                    return false;
                }
            }
            else if(intent.getIntent() == NetIntent.Intent.DISCONNECT)
            {
                return false;
            }
      
            
            NetIntent result;
            boolean canTreater;
            for(OnNetIntentTreater treater: listTreater)
            {
                result = null;
                canTreater = treater.canTreater(intent);
                if(canTreater) result = treater.onTreater(intent);
        
                if(result != null)
                {
                    System.out.println("Sending here");
                    ClientNet reciver = canals.get(result.getReciver());
                    reciver.sendIntent(result);
                    accepted = true;
                }
            }
            
            
            return true;
        }
        
        
        /**
         * Enviar entente
         * @param intent 
         */
        public void sendIntent(NetIntent intent)
        {
            try
            {
                intent.setSendTime(new Date());
                if(intent.getResult() == null)
                    intent.setResult(NetIntent.Result.WAIT);
                String xml = xstream.toXML(intent);
                if(xml!=null)
                {
                    System.out.println(getClass().getSimpleName()+"-> Sending | intent "+intent);
                    this.outData.writeObject(xml);
                }
            } catch (IOException ex)
            {
                Logger.getLogger(ServerSocketListiner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void disconnect ()
        {
            try
            {
                clientSelector.onClientSocketClosed(this);
                for(OnNetIntentTreater onit: listTreater)
                {
                    onit.onClientSocketClosed(this);
                }
                
                canals.remove(this.identifier);
                listIp.remove(this.userSocket.getInetAddress());
                
                this.inData.close();
                this.outData.close();
                this.userSocket.close();
                this.continueRead = false;
            }
            catch (IOException ex) 
            {
                Logger.getLogger(ServerSocketListiner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public String getIdentifier() 
        {
            return this.identifier;
        }
        
        
    }
    
    
    public static String getCurrentTimeStamp()
    {
        return getTextTime(new Date());
    }
    
    public static String getTextTime(Date date)
    {
        return NetIntentConverter.DATE_FORMATTER.format(date);
    }
    
    
    /**
     * 
     */
    private interface ListinerWorke
    {
        /**
         * On Server as closed
         */
        default public void onServerClosed(){};
        
        /**
         * On Client Service Closed
         * @param clienteService 
         */
        default public void onClientSocketClosed(ClientNet clienteService){}
        
         /**
         * 
         * @param acceptedClient 
         */
        default public void  onNewClientRegistred(ClientNet acceptedClient){};
        
    }
 
    /**
     * Intencoes dos clientes listner
     */
    public interface OnNetIntentTreater extends ListinerWorke
    {
        /**
         * Verificar se o transfer esta valido
         * @param intent
         * @return 
         */
        public boolean canTreater(NetIntent intent);
        
        /**
         * Treat o transfer 
         * @param intent
         * @return Null caso o transfer não ser necessario enviar
         */
        public NetIntent onTreater( NetIntent intent);
    }
    
    
    /**
     * Interface para definir os clientes que farao parte da nete ou nao
     */
    public interface ClientSelector extends ListinerWorke
    {
               
        /**
         * Atachar a lista dos clientes todos
         * @param canal 
         */
        default public void onAttachSelector(HashMap<String, ClientNet> canal){}
        
         /**
         * Quando instancear um novo cliente
         * @param clientService 
         */
        default public void onNewClientInstance(ClientNet clientService){};
        
         /**
         *  Validar se o cliente faz parer da iuntentencao local
         * @param intent 
         * @return 
         */
        public boolean accept(NetIntent intent);

        default public void onNewClientRegeted(ClientNet clientService){};
    }
}