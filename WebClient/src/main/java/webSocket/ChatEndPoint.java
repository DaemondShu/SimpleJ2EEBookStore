package webSocket;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * Created by monkey_d_asce on 16-5-1.
 */

@ServerEndpoint("/chat")
public class ChatEndPoint
{
    //private static final AtomicInteger connectionIds = new AtomicInteger(0);
    private static final Set<ChatEndPoint> connections = new CopyOnWriteArraySet<ChatEndPoint>();

    private Session session;
    private String user;


    private static String getUsers()
    {
        JSONObject response = new JSONObject();
        response.put("action", "users");

        JSONArray tmp = new JSONArray();
        for (ChatEndPoint client : connections)
        {
            tmp.add(client.user);
        }
        response.put("users", tmp);
        return response.toString();
    }


    private static void broadcast(String msg)
    {
        for (ChatEndPoint client : connections)
        {
            try
            {
                synchronized (client)
                {
                    client.session.getBasicRemote().sendText(msg);

                }
            } catch (IOException e)
            {
                System.out.println(client.user + "quit");
                connections.remove(client);
                try
                {
                    client.session.close();
                } catch (IOException e1)
                {
                    e.printStackTrace();
                }

                broadcast(getUsers());
                break;
            }
        }
    }

    @OnOpen
    public void start(Session session)
    {
        System.out.println("Start");
        this.session = session;
        connections.add(this);

    }

    @OnMessage
    public void onMessage(String message)
    {
        try
        {


            JSONObject json = JSONObject.fromObject(message);

            switch ((String) json.get("action"))
            {
                case "setUser":
                    user = (String) json.get("username");
                    broadcast(getUsers());
                    break;

                case "chat":
                    JSONObject response = new JSONObject();
                    response.put("action", "chat");
                    response.put("value", (String) json.get("value"));
                    broadcast(response.toString());
                    break;
            }

            //return "success";
        } catch (Exception e)
        {
            e.printStackTrace();
            //return "fail";
        }
    }

    @OnClose
    public void end()
    {
        connections.remove(this);
    }


}
