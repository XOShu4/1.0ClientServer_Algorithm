import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientReader extends Thread {
    PrintWriter out;
    MsgBox msgBox;
    public ClientReader(PrintWriter out, MsgBox msgBox) {
        this.out=out;
        this.msgBox=msgBox;
    }
    public void run() {
        
    }
        
}