/**
 * Copyright (c) 22 Giugno anno 0, 2021, SafJNest and/or its affiliates. All rights reserved.
 * SAFJNEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 
 * 
 * 
 * 
 */
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @author XOShu4
 * Classe contenente gli attributi del oggetto message.
 * <p>
 * NeutronSun: "Non avrei saputo spiegarlo meglio.";
 */
public class Message {
    /**
     * contiene il testo da inviare
     */
    private String message; 
    /**
     * contiene l'oriario in cui e' stato inviato
     */
    private String time;
    /**
     * contiene chi l'ha inviato
     */
    private String Sender;
    Date date = new Date();                                //farmattazzione di time
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

    public Message(String message, String Sender) {
        this.Sender = Sender;
        this.message = message;
        time = sdf.format(date);
    }
    /**
     * get del messaggio finale
     * @return formattazione delle variavili che compongono il messaggio completo: time + Sender + message
     */
    public String getMessage() {
        return "[" + time + "] " + Sender + "@:" + message;
    }

}
