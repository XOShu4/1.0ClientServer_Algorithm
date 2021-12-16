
/**
 * Copyright (c) 22 Giugno anno 0, 2021, SafJNest and/or its affiliates. All rights reserved.
 * SAFJNEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 
 * 
 * 
 * 
 */
import java.io.*;
import java.math.BigInteger;
import java.net.*;

/**
 * @author XOShu4
 *         Classe adibita all'interfacciamento utente.
 *         Gestisce la prima fase log in, la ricezione messaggi utente, la
 *         richiesta chiavi, e la criptazione dei messaggi.
 */
public class Client {
    /** Velocita della stampa su dibStamp */
    static private int speed = 20;
    /** Istanzia classe RsaKey generando le chiavi */
    private static RsaKey Key = new RsaKey();
    /**
     * Variabile condivisa tra Client e ClientThread in cui vengono inserite alcune
     * risposte del Server
     */
    static String shared = "";
    /**
     * Username del Client
     */
    static String userName = "";

    /**
     * @param SubCypherText inserito dall'utente. e' la stringa da criptare.
     * 
     * @param e             Passato da ServerThread e' parte della chiave pubblica
     *                      dell'utente a cui si vuole mandare il messaggio.
     * 
     * @param n             Passato da ServerThread e' parte della chiave pubblica
     *                      dell'utente a cui si vuole mandare il messaggio.
     * 
     *                      Funzione volta a trasformare in BigInteger il messaggio
     *                      per poi criptarlo.
     * 
     * @return subCodeText, numero BigInteger prodotto dalla criptazione del
     *         messaggio.
     * 
     * @throws UnsupportedEncodingException
     * 
     */

    private static BigInteger subCript(String subCypherText, BigInteger e, BigInteger n)
            throws UnsupportedEncodingException {
        BigInteger subCodeText;
        byte[] erbite = subCypherText.getBytes("UTF-8");
        subCodeText = new BigInteger(erbite);
        subCodeText = subCodeText.modPow(e, n);
        return subCodeText;
    }

    /**
     * Funzione che estrapola le Key del Client ricevente e, utilizzando il metodo
     * subCript, cripta il messaggio
     * 
     * @param AppKey
     * @param ToMessage
     * @return appMsg, numero BigInteger prodotto dalla criptazione del
     *         messaggio.
     * @throws UnsupportedEncodingException
     */
    private static BigInteger msgEnc(String AppKey, String ToMessage) throws UnsupportedEncodingException {
        String onlyKays = AppKey.substring(AppKey.indexOf(":") + 1);
        String[] PubKey = onlyKays.split("/");
        BigInteger e = new BigInteger(PubKey[0]);
        BigInteger n = new BigInteger(PubKey[1]);
        BigInteger appMsg;
        appMsg = subCript(ToMessage, e, n);
        return appMsg;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String hostName = "localhost";
        int portNumber = 65535;
        try (Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            String serverReply;
            /** sing in utente */
            do {
                SafJNest.printDbmrStyle("inserisci nome utente", speed);
                userName = stdIn.readLine();
                out.println(userName);
                serverReply = in.readLine();
                SafJNest.printDbmrStyle(serverReply);
            } while (!serverReply.equals("sign up effettuato"));
            SafJNest.printDbmrStyle("Connecting...", speed);
            SafJNest.loadingBee(8);
            /** passaggio chiave publica al ServerThread */
            out.println(Key.getE() + "/" + Key.getN());
            /** avvio thread ClientReader */
            ClientReader t = new ClientReader(out, in, Key);
            new Thread(t).start();
            SafJNest.printDbmrStyle("Connessione con il server effettuata\nCOMANDI:\n/listaUtenti:\n/NomeUtente1/NomeUtenteN: msg\n/All: msg\n/Speed:\n/EXIT", speed);
            String userInput = null;
            try { // inizio presa in input messaggi utente. i comandi inseriti vengono smistati.
                while (!(userInput = stdIn.readLine()).equals("/EXIT")) {// uscita del utente e rimozione del username
                    if (userInput.equals("/EXIT")) {
                        out.println("/EXIT");
                        t.getEXIT();
                        socket.close();
                    }
                    if (userInput.equals("/Speed:")) {
                        SafJNest.printDbmrStyle("f-> Veloce\nm-> Medio\ns->piano", speed);
                        userInput = stdIn.readLine();
                        if (userInput.equals("f"))
                            speed = 10;
                        else if (userInput.equals("m"))
                            speed = 20;
                        else if (userInput.equals("s"))
                            speed = 30;
                        else
                            SafJNest.printDbmrStyle("comando non riconosciuto!", speed);
                    } else if (userInput.contains("/All:")) { // invio del messaggio a tutti i Client online
                        out.println("/All:");
                        while (!shared.contains("STOP")) { // finche ci sono Client allora
                            do {
                                Thread.sleep(100);
                                shared = t.getShared();
                            } while (shared.equals("")); // attendo che ClientTherad scriva su shared
                            if (!shared.contains("STOP")) {
                                String AppKey = shared;
                                shared = "";
                                String ToMessage = userInput.substring(userInput.indexOf(":") + 1);
                                BigInteger appMsg = msgEnc(AppKey, ToMessage);
                                out.println(appMsg); // invio messaggio criptato
                            }
                        }
                        shared = "";
                    } else if (userInput.contains("/listaUtenti:")) { // visualizazzione di tutti i client online
                        out.println("/listaUtenti:");
                        while (!shared.contains("STOP")) { // finche ci sono Client allora
                            do {
                                Thread.sleep(100);
                                shared = t.getShared();
                            } while (shared.equals("")); // attendo che ClientTherad scriva su shared
                            if (!shared.contains("STOP")) {
                                SafJNest.printDbmrStyle("<User>" + shared.substring(9), speed);
                                out.println(); // usato per abozzare una sincronizazzione tra Client e ServerThread <3
                                shared = "";
                            }
                        }
                        shared = "";
                    } else if (userInput.charAt(0) == '/' && userInput.contains(":")) { // invio a uno o piu client di
                                                                                        // un messaggio
                        String SNamesToSend = userInput.substring(1, userInput.indexOf(":"));
                        String[] UserToSend = SNamesToSend.split("/");
                        String ToMessage = userInput.substring(userInput.indexOf(":") + 1);
                        if(ToMessage.length()>0){
                            for (String s : UserToSend) { // i nome utente a cui recapitare il messaggio vengono inviati
                                out.println(s); // serverThread, il quale attende arrivo delle chiavi da parte del
                                do { // ClientReader
                                    Thread.sleep(100);
                                    shared = t.getShared();
                                } while (shared.equals(""));
                                if (shared.contains("<-not found")) 
                                    SafJNest.printDbmrStyle(s + "<- not found", speed);
                                else {
                                    String AppKey = shared;
                                    shared = "";
                                    BigInteger appMsg = msgEnc(AppKey, ToMessage);
                                    out.println(appMsg); // invio messaggio criptato dalla funzione subCript
                                }
                            }
                        }
                    } else
                        SafJNest.printDbmrStyle("comando non riconoscito", speed);
                }
                SafJNest.printDbmrStyle("Exit...", speed);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
  
    public String getUame() {
        return userName;
    }
}
