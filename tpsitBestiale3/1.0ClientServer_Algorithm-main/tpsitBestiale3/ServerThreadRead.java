import java.io.PrintWriter;
/**
 * @author XOShu4
 * avviata da ServerThread si occupa di leggere i messaggi del rispettivo Client dentro MsgBox
 */
public class ServerThreadRead extends Thread {
    /**
     * PrintWriter passato dal ServerThread
     */
    PrintWriter out;
    /**
     * oggetto MsgBox passato da ServerThread
     */
    MsgBox msgBox;
    /**
     * UserName del Client condiviso da ServerThread
     */
    String UserName;

    public ServerThreadRead(PrintWriter out, MsgBox msgBox, String UserName) {
        this.out = out;
        this.msgBox = msgBox;
        this.UserName = UserName;
    }

    public void run() {
        synchronized (msgBox) {                                                          //metodo sincronizzato
            while (true) {  
                try {
                    out.println(":MsgToDec:/" + msgBox.readBox(UserName));               //chiede ogni volta che puo di leggere la msgBox e il return lo manda al ClientReader 
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
