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
import java.util.Scanner;

/**
 * @author XOShu4
 *         avviata da Client si occupa di ricevere tutti i messaggi ricevuti da
 *         ServerThread dopo il suo avvio
 */
public class ClientReader extends Thread {
    /**
     * per capire il sistema operativo
     */
    private static String OS = System.getProperty("os.name").toLowerCase();
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
     * Messaggio che va condiviso con con Client inviato da ServerThread
     */
    String shared = "";
    /**
     * Messaggio che il Client legge dalla funzione getShared()
     */
    String returnShared = "";
    /**
     * Oggetto che invia messaggi a Terminal
     */
    PrintWriter toTerminal;
    /**
     * Porta del socket presa da file
     */
    private static int port;
    /**
     * Se true allora esce dal while
     */
    private boolean Exit = false;

    /**
     * Avvio di un altro socket tra ClientReader e Terminal, con porta presa da
     * file,
     * che viene ripresa da Terminal e riscritta nel file incrementata di uno.
     * viene aperto un altro cmd gestito dalla classe Terminal (UwU)
     * 
     * @param out PrintWriter passato da Client
     * @param in  BufferedReader passato da Client
     * @param Key Oggetto delle chiavi RSA passato da Client
     * @throws IOException
     * mi sembra impossibile
     * @throws InterruptedException
     * improponibile
     */
    public ClientReader(PrintWriter out, BufferedReader in, RsaKey Key) throws IOException, InterruptedException {
        this.out = out;
        this.in = in;
        this.Key = Key;
        File processCheck = new File("porte.txt");
        while (!processCheck.canWrite()) {
            Thread.sleep(10);
        } // legge da file se nessuno sta occupando la risorsa
        Scanner scannerDaFile = new Scanner(processCheck);
        port = Integer.parseInt(scannerDaFile.nextLine());
        scannerDaFile.close();
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Runtime rt = Runtime.getRuntime();
            if (isWindows()) {
                String variableToPass = "cmd /c start cmd.exe /k java Terminal";  // avvia il nuovo cmd su windows 
                rt.exec(variableToPass);    
            } else if (isMac()) {
                String[] args = new String[] {"/bin/bash", "-c", "java Terminal"};      //dovrebbe avviare il cmd di mac, PROB. NON FUNZIONA (non testato)
                rt.exec(args);
            }
            else
                System.out.println("app non supportata.");
            Socket terminalS = serverSocket.accept(); // crea la nuova connessione
            toTerminal = new PrintWriter(terminalS.getOutputStream(), true);
        }
    }

    public void run() {
        String nServer = "";
        Client obj = new Client();
        try {
            toTerminal.println(obj.getUame());
            while ((nServer = in.readLine()) != null && Exit == false) { // input da ServerThread
                if (nServer.contains("/@/Key/@/")) // se inizia con il protocollo "/@/Key/@/" allora non va mandato a
                    shared = nServer; // Terminal ma va mandato al client, poi letto tramite la funzione getShared :(
                 else if (nServer.contains(":MsgToDec:/")) { // se inizzia con il protocollo ":MsgToDec:/" va prima UwU
                    BigInteger appoggio = new BigInteger(nServer.substring(nServer.indexOf("@:") + 2)); // decriptato poi inviato al Terminal
                    appoggio = appoggio.modPow(Key.getD(), Key.getN());
                    byte[] erbite = appoggio.toByteArray();
                    String decrypted = new String(erbite, "UTF-8");
                    toTerminal.println(nServer.substring(11, nServer.indexOf("@:") + 2) + decrypted);
                } else if (!nServer.equals("completed"))  // in qualsiasi altro caso e' un replay che va mandato a
                    toTerminal.println(nServer); // Terminal apparte completed
            }

        } catch (IOException e) {
        }
    }

    public String getShared() {
        if (!shared.equals("")) {
            returnShared = shared;
            shared = "";
        }
        return returnShared;
    }

    public void getEXIT() {
        toTerminal.println("/EXIT");
        Exit = true;
    }
    public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}
 
	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}
 

}