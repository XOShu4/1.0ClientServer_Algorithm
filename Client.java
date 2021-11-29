import java.io.*;
import java.net.*;

public class Client {
    private static RsaKey Key = new RsaKey();

    public static void main(String[] args) throws IOException, InterruptedException {
        String hostName = "localhost";
        int portNumber = 65535;
        ServerThread obj = new ServerThread();
        try (Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {  
            //login or register 
            String serverReply;
            String userName;
            do {
                System.out.println("inserisci nome utente");
                userName=stdIn.readLine();
                out.println(userName);
                serverReply=in.readLine(); 
                System.out.println(serverReply);
            } while (serverReply.equals("utente gia presente, cambia userName"));
            out.println(Key.getD()+"/"+Key.getN());
            System.out.println("Connessione con il server effettuata");
            System.out.println("comandi: ");
            System.out.println("/lista utenti");
            System.out.println("/NomeUtente1/NomeUtenteN: msg");
            String userInput=null;
            String serverInput=null;
            try {
                while ((userInput = stdIn.readLine()) != null) {
                    if(userInput.charAt(0)=='/' && userInput.contains(":")){
                        //send to serverT
                        out.println(userInput);
                       if(!(serverInput=in.readLine()).equals("completed"))
                            System.out.println(serverInput);
                        }
                    else
                        System.out.println("comando non riconosciuto");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
