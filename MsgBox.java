import java.util.ArrayList;
import java.util.HashMap;

public class MsgBox {
    private HashMap<String, ArrayList<Message>> AsheBox = new HashMap<String, ArrayList<Message>>();
    private int cont = 0;
    public String ciao="ciao";

    public String addUser(String userName) {
        if (AsheBox.containsKey(userName)) {
            return "utente gia presente, cambia userName";
        }
        AsheBox.put(userName, new ArrayList<>());
        return "sing up effettuato";
    }

    synchronized public String addMsg(Message message, String[] toSend) throws InterruptedException {
        while (cont > 0) {
            wait();
        }
        String response = "";
        for (String User : toSend) {
            if (!User.equals("")) {
                if (AsheBox.containsKey(User))
                    AsheBox.get(User).add(message);
                else
                    response += User + "/";
            }
        }

        if (response.equals(""))
            response = "completed";
        else
            response += " -> were not found";
        return response;
    }

    public String readBox(String UserName) throws InterruptedException {
        String msg;
        while (AsheBox.get(UserName).size() == 0) {
            wait();
        }
        cont++;
        msg = AsheBox.get(UserName).remove(0).getMessage();
        cont--;
        notifyAll();
        return msg;
    }
}
