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
import java.net.Socket;
import java.util.Scanner;

/**
 *classe che gestisce il nuovo cmd. 
 *legge porta socket su file e la riscrive incrementata.
 *gestisce tutti i messaggi che sono indirizzati al Client dal ServerThread.
 <p>
  NeutronSun: "<3"
 * @author NeutronSun
 * @author XOShu4
 */
public class Terminal {
    public static void main(String[] args) {
        try {
            
            int port=0;
            File processCheck = new File( "porte.txt" );        //aspetta che il file non sia occupato
           while(!processCheck.canWrite()){Thread.sleep(10);}
            Scanner scannerDaFile = new Scanner(processCheck);
            port=Integer.parseInt(scannerDaFile.nextLine());
            FileWriter fileout = new FileWriter("porte.txt");
            scannerDaFile.close();
            fileout.write(String.valueOf(port+1));              //legge la porta e la riscrive incrementata di uno
            fileout.close();
            Socket socket = new Socket("localhost", port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Terminal di<"+in.readLine()+">");
            String chat;
            while (!(chat=in.readLine()).equals("EXIT")) {
                System.out.println(chat);
            }
            socket.close();
        } catch (Exception e) {

        }
    }
}
