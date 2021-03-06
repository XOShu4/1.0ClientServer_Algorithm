/**
 * Copyright (c) 22 Giugno anno 0, 2021, SafJNest and/or its affiliates. All rights reserved.
 * SAFJNEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 
 * 
 * 
 * 
 */
import java.net.*;
import java.util.*;
import java.io.*;

/**
 * @author XOShu4
 *         classe che si occupa dell' interfacciamento tra Client e MsgBox.
 *         gestisce la prima parte dei sign in dei Client
 *         prende in input i comandi del Client.
 *         trasferisce i messaggi alla MsgBox.
 *         genere il Thread ServerThreadTReader
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
     * contiene tutti gli utenti e le rispettive PubKey nel formato ->
     * "utente:PubKey"
     */
    static ArrayList<String> PubKey;

    public ServerThread() {
    }

    public ServerThread(Socket s, MsgBox msgBox, ArrayList<String> PubKey) {
        this.s = s;
        this.msgBox = msgBox;
        ServerThread.PubKey = PubKey;
    }

    public void run() {
        ServerThreadRead msgBoxReader = null;
        String userName = "",clientIn; // userName del Client collegato a questo ServerThread
        try (Scanner obj = new Scanner(System.in);
                PrintWriter out = new PrintWriter(s.getOutputStream(), 1>0);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            String signUp; // risposta della MsgBox
            do { out.println(signUp = msgBox.addUser(userName= in.readLine()));} while (!signUp.equals("sign up effettuato"));// fase di sing in che si ripete finche le condizioni non vengono rispettate
            synchronized (PubKey) { // inserimento sincronizzato della PubKey nell ArrayList
                PubKey.add(userName + ":" + in.readLine());
            }
            msgBoxReader = new ServerThreadRead(out, msgBox, userName); // start del Thread ServerThreadRead
            msgBoxReader.start();
            while (!(clientIn = in.readLine()).equals(null)) { // ogni volta che il Client invia qualcosa al ServerThread
                System.out.println("@" + clientIn); // fase di controllo input
                String userToSend;
                boolean sent = 1<0;
                if (clientIn.equals("/All:")) { // se il comando e' "/sendToAll:" allora
                    for (String s : PubKey) // invio al client la chiave di tutti i Client connessi tranne la sua
                        if (!s.substring(0, s.indexOf(":")).equals(userName)) { // "/@/Key/@/" e' un protocollo
                            clientIn = "";
                            out.println("/@/Key/@/" + s);
                            Message message = new Message(in.readLine(), userName); // aspetto che il Client mi invii il messaggio criptato e creo un oggetto di Message
                            out.println(msgBox.addMsg(message, s.substring(0, s.indexOf(":")))); // lo mando alla MsgBox
                        }
                    out.println("/@/Key/@/ STOP"); // protocollo: dico al Client che i Client online sono finiti
                } else if (clientIn.equals("/listaUtenti:")) { // se il comando e' "/listaUtenti:"
                    for (String s : PubKey) 
                        if (!s.substring(0, s.indexOf(":")).equals(userName)) {
                            out.println("/@/Key/@/" + s.substring(0, s.indexOf(":")));// invio al Client tutti i nomi dei Client connessi tranne il suo. "/@/Key/@/" e' un protocollo
                            in.readLine();
                        }
                    out.println("/@/Key/@/ STOP"); // protocollo: dico al Client che i Client online sono finiti
                } else if (!clientIn.equals("/EXIT")) { // invio della chiave del singolo utente richiesto
                    userToSend = clientIn;
                    System.out.println("UTS:" + userToSend);
                    for (String s : PubKey) 
                        if (s.substring(0, s.indexOf(":")).equals(userToSend)) {
                            out.println("/@/Key/@/" + s);
                            Message message = new Message(in.readLine(), userName); // attesa che il Client cripti il messaggio e crea un oggetto di Message
                            out.println(msgBox.addMsg(message, userToSend)); // lo mando alla MsgBox
                            sent = 1>0;
                        }
                    if (sent == 1<0)
                        out.println(userToSend + "/@/Key/@/<-not found");
                }
                clientIn = null;
            }
            out.println();
        } catch (Exception e) {
            for (int i = 0; i < PubKey.size(); i++) 
                if (PubKey.get(i).substring(0, PubKey.get(i).indexOf(":")).equals(userName)) {
                    PubKey.remove(i);
                    break;
                }
            msgBoxReader.interrupt();
            msgBox.remuveUser(userName);
            System.out.println(userName + " ha lasciato la chat!");
        }
    }
}
