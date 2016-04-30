package test;

import javax.ejb.EJB;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by monkey_d_asce on 16-3-26.
 */
@WebServlet("/testServlet")
//@ServletSecurity(@HttpConstraint(rolesAllowed = { "admin"}))
public class testServlet extends HttpServlet
{
    @EJB(name = "Hello")
    private Hello hello;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {


        try
        {
            InitialContext ctx = new InitialContext();
            //获取ConnectionFactory对象
            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
            //创建QueueConnection对象
            QueueConnection connection = factory.createQueueConnection();
            //创建QueueSession对象，第一个参数表示事务自动提交，第二个参数标识一旦消息被正确送达，将自动发回响应
            QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            //获得Destination对象
            Queue queue = (Queue) ctx.lookup("queue/myQueue");
            //创建文本消息
            TextMessage msg = session.createTextMessage("世界，你好");
            //创建发送者
            QueueSender sender = session.createSender(queue);
            //发送消息
            sender.send(msg);
            //关闭会话
            session.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        //System.out.println(helloBean.sayHello());


        //response.getWriter().println(middle.ret());
        //
    }
}
