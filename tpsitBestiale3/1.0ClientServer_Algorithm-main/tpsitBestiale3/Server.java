import java.net.*;
import java.util.ArrayList;
import java.io.*;
/**
 * @author XOShu4
 * gestisce le connessioni dei Client e genera ServerThread per ogni nuovo Client
 * si occupa di istanziare e condividere la ArrayList PubKey e l'oggetto msgBox.
 */ 
public class Server {

    public static void main(String[] args) throws Exception {
        MsgBox msgBox = new MsgBox();
        int portNumber = 65535;
        ArrayList<String> PubKey = new ArrayList<String>();
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(portNumber);) {
                Socket s = serverSocket.accept();                           //accetta la connessione con ogni Client che chiede
                ServerThread st = new ServerThread(s, msgBox, PubKey);      //condivide il socket e la referenza di msgBox e PubKey a ServerThread
                st.start();                                                 //avvia un ServerThread per il corrente Client 
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port " + portNumber
                        + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
    }
}