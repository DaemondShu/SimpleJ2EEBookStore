package webSocket;

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
                System.out.println("Chat Error: Failed to send message to client");
                connections.remove(client);
                try
                {
                    client.session.close();
                } catch (IOException e1)
                {
                    // Ignore
                }
                broadcast(msg);
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
    public String onMessage(String message)
    {
        System.out.println(message);
        return "ok";
    }

    @OnClose
    public void end()
    {
        connections.remove(this);
    }
}
