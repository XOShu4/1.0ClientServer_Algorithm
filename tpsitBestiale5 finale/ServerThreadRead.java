
/**
 * Copyright (c) 22 Giugno anno 0, 2021, SafJNest and/or its affiliates. All rights reserved.
 * SAFJNEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 
 * 
 * 
 * 
 */
import java.io.PrintWriter;

/**
 * @author XOShu4
 *         avviata da ServerThread si occupa di leggere i messaggi del
 *         rispettivo Client dentro MsgBox
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
        super("theReader");// @author NeutronSun, TheGodOfThread. :D
        this.out = out;
        this.msgBox = msgBox;
        this.UserName = UserName;
    }

    public void run() {
        while (true) {
            try { // chiede ogni volta che puo di leggere la msgBox e il return lo manda al
                out.println(":MsgToDec:/" + msgBox.readBox(UserName)); // ClientReader
            } catch (Exception e) {
                return;
            }
        }
    }
}
