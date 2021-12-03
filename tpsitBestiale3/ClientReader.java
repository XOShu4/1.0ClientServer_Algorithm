import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author XOShu4
 *         avviata da Client si occupa di ricevere tutti i messaggi ricevuti da
 *         ServerThread dopo il suo avvio
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
    /**
     * messaggio che va condiviso con con Client inviato da ServerThread
     */
    String shared = "";
    /**
     * messaggio che il Client legge dalla funzione getShared()
     */
    String returnShared = "";
    /**
     * oggetto che invia messaggi a Terminal
     */
    PrintWriter toTerminal;
    /**
     * porta del socket presa da file
     */
    private static int port;
    /**
     * avvio di un altro socket tra ClientReader e Terminal, con porta presa da file, 
     * che viene ripresa da Terminal e riscritta nel file incrementata di uno.
     * viene aperto un altro cmd gestito dalla classe Terminal (UwU)
     * @param out PrintWriter passato da Client
     * @param in BufferedReader passato da Client
     * @param Key Oggetto delle chiavi RSA passato da Client
     * @throws IOException
     * @throws InterruptedException
     */
    public ClientReader(PrintWriter out, BufferedReader in, RsaKey Key) throws IOException, InterruptedException {
        this.out = out;
        this.in = in;
        this.Key = Key;
        File processCheck = new File( "porte.txt" );
        while(!processCheck.canWrite()){Thread.sleep(10);}          //legge da file se nessuno sta occupando la risorsa
            Scanner scannerDaFile = new Scanner(processCheck);
            port=Integer.parseInt(scannerDaFile.nextLine());
            scannerDaFile.close();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Runtime rt = Runtime.getRuntime();                      //avvia il nuovo cmd
            String variableToPass = "cmd /c start cmd.exe /k java Terminal";
            rt.exec(variableToPass);
            Socket terminalS = serverSocket.accept();               //crea la nuova connessione
            toTerminal = new PrintWriter(terminalS.getOutputStream(), true);
        }
    }

    public void run() {
        String nServer = "";
        Client obj = new Client();
        try {
            toTerminal.println(obj.getUame());
            while ((nServer = in.readLine()) != null) {         // input da ServerThread
                if (nServer.contains("/@/Key/@/")) {            // se inizia con il protocollo "/@/Key/@/" allora non va mandato a                  
                    shared = nServer;                          //Terminal ma va mandato al client, poi letto tramite la funzione getShared :(
                } else if (nServer.contains(":MsgToDec:/")) {                                            // se inizzia con il protocollo ":MsgToDec:/" va prima UwU                     
                    BigInteger appoggio = new BigInteger(nServer.substring(nServer.indexOf("@:") + 2));  // decriptato poi inviato al Terminal
                    appoggio = appoggio.modPow(Key.getD(), Key.getN());
                    byte[] erbite = appoggio.toByteArray();
                    String decrypted = new String(erbite, "UTF-8");
                    toTerminal.println(nServer.substring(11, nServer.indexOf("@:") + 2) + decrypted);
                } else if (!nServer.equals("completed")){                        // in qualsiasi altro caso e' un replay che va mandato a                                     
                    toTerminal.println(nServer);                                 // Terminal apparte completed
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getShared() {
        if (!shared.equals("")) {
            returnShared = shared;
            shared = "";
        }
        return returnShared;
    }

}