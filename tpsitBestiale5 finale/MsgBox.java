
/**
 * Copyright (c) 22 Giugno anno 0, 2021, SafJNest and/or its affiliates. All rights reserved.
 * SAFJNEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 
 * 
 * 
 * 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * @author XOShu4
 * @author NeutronSun
 *         classe che gestisce AsheBox: Sign in, scrittura messaggio, lettura
 *         messaggio.
 */
public class MsgBox {
    /**
     * mappa di ArrayList con tutti i Client e i rispettivi message ricevuti. le key
     * di mappa sono i nomi dei Client
     * <p>
     * NeutronSun: "Ashe otp, champ osceno."
     */
    private HashMap<String, ArrayList<Message>> AsheBox = new HashMap<String, ArrayList<Message>>();
    /**
     * contatore usato nella sincronizzazzione
     */
    private int cont = 0;

    /**
     * controlla se l'userName esiste e rispetta certi criteri
     * 
     * @param userName
     * @return raplay del metodo: buon fine o fallimento di sing in.
     *         <p>
     *         NeutronSun: "Si, RaiPlay...Impara a scrivere pls"
     */
    public synchronized String addUser(String userName) {
        if (AsheBox.containsKey(userName)) // se l'username e' presente dico al ServerThread di far reinserire l'userName al Client
            return "utente gia presente, cambia userName";
        else if (!Pattern.matches("^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$", userName)) // se l'username non rispetta l'espressione regolare dico al ServerThread di far reinserire l'userName al Client
            return "formato errato! Il nome utente deve essere composto da numeri e lettere. sono ammessi trattini solo in mezzo il nome!";
        System.out.println("<new User:>" + userName);           // se tutto e' andato a buon fine reggistro il nuovo utente e dico al ServerThread che e' andato tutto bene
        AsheBox.put(userName, new ArrayList<Message>());
        return "sign up effettuato";
    }

    /**
     * rimuove userName dalla box
     * 
     * @param userName
     */
    public synchronized void remuveUser(String userName) {
        System.out.println("<exit User:>" + userName); // se tutto e' andato a buon fine reggistro il nuovo utente e dico al ServerThread che e' andato tutto bene
        AsheBox.remove(userName);
    }

    /**
     * metodo che inserisce i messaggi negli arrayList dei rispettivi Client
     * riceventi
     * 
     * @param message
     * @param toSend
     * @returnr replay del metodo: buon fine o fallimento del invio messaggio
     * @throws InterruptedException
     */
    synchronized public String addMsg(Message message, String toSend) throws InterruptedException {
        while (cont > 0) { wait();}    // finche conto > 0 allora qualcuno sta leggendo, quindi non posso scrivere. 
        if (AsheBox.containsKey(toSend))// se trovo a chi mandare il messaffio lo inserisco nel suo ArrayList
            AsheBox.get(toSend).add(message);
        notifyAll(); // sveglio tutti i processi in wait()
        return AsheBox.containsKey(toSend) ? "completed" : (toSend + " -> not found"); //@author NeutronSun, is Still here. 
    }

    /**
     * Lettura sincronizzata del arrayList di messaggi
     * 
     * @param UserName
     * @return messaggio per il Client che fa richiesta
     * @throws InterruptedException
     *                              no way
     */
    public String readBox(String UserName) {
        String msg;
        try {
            synchronized (this) {
                while (AsheBox.get(UserName).size() == 0) { wait();} // finche la mappa e' vuota aspetto
                cont++; // mentre leggo aumento di uno cont
            }
            msg = AsheBox.get(UserName).remove(0).getMessage(); // leggo e rimuovo il messaggio
            synchronized (this) {
                cont--; // decremento cont perche ho finito di leggere
                notifyAll(); // sveglio i processi in wait
                return msg;
            }
        } catch (InterruptedException e) {
            return null;
        }
    }
}
