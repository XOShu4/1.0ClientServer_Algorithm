import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
/**
 *classe che gestisce il nuovo cmd. 
 *legge porta socket su file e la riscrive incrementata.
 *gestisce tutti i messaggi che sono indirizzati al Client che ha avviato il ClientReader che ha avviato Terminal
 * @author Sanseverino Lorenzo
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
            while (true) {
                System.out.println(in.readLine());
            }
        } catch (Exception e) {

        }
    }
}