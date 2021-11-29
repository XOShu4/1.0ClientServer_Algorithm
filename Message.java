import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

public class Message {
    private String message; //messaggio da inviare
    private String time;
    private String Sender;
    private boolean readed=false; 
    private BigInteger [] PubKeySender;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    public Message(String message, String Sender, BigInteger [] PubKeySender ){
        this.PubKeySender=PubKeySender;
        this.Sender=Sender;
        this.message=message;
        time = sdf.format(date);
    }
    public boolean isReaded(){
        return readed;
    }
    public String getMessage(){
        return time+"// "+Sender+": "+message;
    }

}
