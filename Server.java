import java.net.*;
import java.io.*;


public class Server {
   

    public static void main(String[] args) throws Exception {
        MsgBox msgBox = new MsgBox();
        int portNumber = 65535;
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(portNumber);) {
                Socket s = serverSocket.accept();
                ServerThread st = new ServerThread(s, msgBox);
                st.start();
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port " + portNumber
                        + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
    }

    
}