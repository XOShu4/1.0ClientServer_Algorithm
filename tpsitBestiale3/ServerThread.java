import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
/**
 * @author XOShu4
 * classe che si occupa dell' interfacciamento tra Client e MsgBox. 
 * gestisce la prima parte dei sing in dei Client
 * prende in input i comandi del Client.
 * trasferisce i messaggi alla MsgBox.
 * genere il Thread ServerThreadTReader
 */

public class ServerThread extends Thread {
    /**
     * porta ed indirizzo della connessione tra client e server
     */
    Socket s = null;
    /**
     * istanza di MsgBox passata dalla classe Server. 
     * contiene tutti i metodi per inserire e leggere i messaggi.
     */
    MsgBox msgBox;
    /**
     * contiene tutti gli utenti e le rispettive PubKey nel formato -> "utente:PubKey"
     */
    ArrayList<String> PubKey;

    public ServerThread() {
    }

    public ServerThread(Socket s, MsgBox msgBox, ArrayList<String> PubKey) {
        this.s = s;
        this.msgBox = msgBox;
        this.PubKey = PubKey;
    }

    public void run() {
        String userName;                                                        //userName del Client collegato a questo ServerThread
        try (Scanner obj = new Scanner(System.in);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            String singUp;                                                      //risposta della MsgBox
            do {                                                                //fase di sing in che si ripete finche le condizioni non vengono rispettate
                userName = in.readLine();
                singUp = msgBox.addUser(userName);
                out.println(singUp);
            } while (!singUp.equals("sing up effettuato"));
            synchronized (PubKey) {                                             //inserimento sincronizzato della PubKey nell ArrayList
                PubKey.add(userName + ":" + in.readLine());
            }
            ServerThreadRead msgBoxReader = new ServerThreadRead(out, msgBox, userName);        //start del Thread ServerThreadRead
            new Thread(msgBoxReader).start();
            
            String clientIn;
            while (!(clientIn = in.readLine()).equals(null)) {                  //ogni volta che il Client invia qualcosa al ServerThread
                System.out.println("@"+clientIn);                               //fase di controllo input
                String msgToSend = "";
                boolean sent = false;
                String userToSend;
                if(clientIn.equals("/sendToAll:")){                             //se il comando e' "/sendToAll:" allora                        
                    for (String s : PubKey) {                                   //invio al client la chiave di tutti i Client connessi tranne la sua
                        if(!s.substring(0, s.indexOf(":")).equals(userName)){   //"/@/Key/@/"  e' un protocollo
                            clientIn="";
                            out.println("/@/Key/@/" + s);
                            msgToSend = in.readLine();                                //aspetto che il Client mi invii il messaggio criptato
                            Message message = new Message(msgToSend, userName);       //creo un oggetto di Message
                            String response = msgBox.addMsg(message, s.substring(0, s.indexOf(":")));           //lo mando alla MsgBox
                            out.println(response);
                        }
                    }
                    out.println("/@/Key/@/ STOP");                                  //protocollo: dico al Client che i Client online sono finiti
                }else if(clientIn.equals("/listaUtenti:")){                         //se il comando e' "/listaUtenti:"
                    for (String s : PubKey) {                                       
                        if(!s.substring(0, s.indexOf(":")).equals(userName)){       
                            out.println("/@/Key/@/" + s.substring(0, s.indexOf(":")));//invio al Client tutti i nomi dei Client connessi tranne il suo
                            in.readLine();                                            //"/@/Key/@/"  e' un protocollo
                        }
                    }
                    out.println("/@/Key/@/ STOP");                                  //protocollo: dico al Client che i Client online sono finiti
                }else{                                                              //invio della chiave del singolo utente richiesto
                    userToSend = clientIn;  
                    System.out.println("UTS:"+userToSend);
                    for (String s : PubKey) {
                        if (s.substring(0, s.indexOf(":")).equals(userToSend)) {
                            out.println("/@/Key/@/" + s);
                            msgToSend = in.readLine();                              //attesa che il Client cripti il messaggio 
                            Message message = new Message(msgToSend, userName);     //crea un oggetto di Message
                            String response = msgBox.addMsg(message, userToSend);   //lo mando alla MsgBox
                            out.println(response);
                            sent = true;
                        }
                    }
                    if (sent == false)
                        out.println(userToSend + "<-were not found");
                    clientIn = null;
                }
            }

        } catch (IOException | InterruptedException e) {
        }
    }
}