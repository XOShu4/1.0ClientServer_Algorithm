import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
/**
 *@author XOShu4
 *avviata da Client si occupa di ricevere tutti i messaggi ricevuti da ServerThread dopo il suo avvio
 */
public class ClientReader extends Thread {  
    /**
     * PrintWriter passato da Client
     */
    PrintWriter out;
    /**
     * BufferedReader passato da Client
     */
    BufferedReader in;
    /**
     * Oggetto delle chiavi RSA passato da Client
     */
    RsaKey Key;
   
    public ClientReader(PrintWriter out, BufferedReader in, RsaKey Key) {
        this.out = out;
        this.in = in;
        this.Key = Key;
    }
   
    public void run() {
        String nServer="";                                  
        try {
            while ((nServer = in.readLine()) != null) {             //input da ServerThread
                if (nServer.contains("/@/Key/@/")) {                //se inizia con il protocollo "/@/Key/@/" allora non va scritto sul terminal ma va mandato al client
                    Client.shared = nServer;                        //utilizzando la varriabile pubblica :( shared
                }
                else if (nServer.contains(":MsgToDec:/")) {         //se inizzia con il protocollo ":MsgToDec:/" va prima decriptato poi scritto su terminal
                    BigInteger appoggio = new BigInteger(nServer.substring(nServer.indexOf("@:") + 2));
                    appoggio = appoggio.modPow(Key.getD(), Key.getN());
                    byte[] erbite = appoggio.toByteArray();
                    String decrypted = new String(erbite, "UTF-8");
                    System.out.println(nServer.substring(11, nServer.indexOf("@:") + 2) + decrypted);
                } else if (!nServer.equals("completed"))            //in qualsiasi altro caso e' un replay che va scritto su terminal apparte completed
                    System.out.println(nServer);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}