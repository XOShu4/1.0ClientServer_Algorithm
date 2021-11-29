import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.math.BigInteger;

public class ServerThread extends Thread {
    String line = null;
    Socket s = null;
    MsgBox msgBox;

    public ServerThread() {
    }

    public ServerThread(Socket s, MsgBox msgBox) {
        this.s = s;
        this.msgBox=msgBox;
    }

    public void run() {
        BigInteger[] PubKey=new BigInteger[2];
        String userName;
        try (Scanner obj = new Scanner(System.in);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
                String singUp;
            do {
                userName=in.readLine();
                singUp =msgBox.addUser(userName);
                out.println(singUp);
            } while (singUp.equals("utente gia presente, cambia userName"));
            String[] appoggioKey = in.readLine().split("/");
            for ( int i=0;i<2;i++) {
                byte[] erbite = appoggioKey[i].getBytes("UTF-8");
                PubKey[i]=new BigInteger(erbite);
            }
           String clientIn;
           ClientReader t= new ClientReader(out, msgBox);
           t.start();
            while ((clientIn=in.readLine())!=null) {
                String SNamesToSend=clientIn.substring(0,clientIn.indexOf(":"));
                String ToMessage=clientIn.substring(clientIn.indexOf(":"));
                String [] UserToSend=SNamesToSend.split("/");
                Message message = new Message(ToMessage, userName, PubKey);
                String response=msgBox.addMsg(message, UserToSend);
                System.out.println(response);
                out.println(response);
            }

        } catch (IOException  |InterruptedException e) {
        }
    }
}