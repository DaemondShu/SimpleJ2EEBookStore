package test;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by monkey_d_asce on 16-3-28.
 */
@WebServlet("/testSecurity")
public class testSecurity extends HttpServlet
{
    @EJB
    Hello hello;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //response.setContentType("text/html");


        String user = request.getParameter("user");
        String pwd = request.getParameter("password");

        //System.setProperty("java.security.auth.login.config","../jaasR.config");

        try
        {

            /*
            InitialContext context = new InitialContext();

            Hello hello = (Hello) context.lookup("HelloEJB/remote");

            response.getWriter().println(hello.sayHello());
*/
/*
            request.login(user,pwd);

            response.getWriter().println(request.getUserPrincipal());

            if (request.isUserInRole("admin")) response.getWriter().print("admin");

            */

            request.getSession(true);
            request.login(user, pwd);

            System.out.println(request.getUserPrincipal());

            //request.login(user,pwd);
            response.sendRedirect("/bookstore/testServlet");
/*
            PECallbackHandler peCallbackHandler = new PECallbackHandler(user,pwd);
            LoginContext loginContext = new LoginContext("UserLogin",peCallbackHandler);
            loginContext.login();


            Subject subject = loginContext.getSubject();
            PrivilegedAction action = new PrivilegedAction()
            {

                @Override
                public Object run()
                {
                    try
                    {
                        Hello hello = (Hello) new InitialContext().lookup("java:module/HelloEJB!test.Hello");
                        //response.sendRedirect("/bookstore/testServlet");
                        return hello.sayHi();


                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    return null;
                }
            };

            */

            //String result = (String) Subject.doAsPrivileged(subject,action,null);  //跟直接运行没什么区别啊

            //response.getWriter().println("success..." + result);
            //response.sendRedirect("/bookstore/testServlet");
        } catch (Exception e)
        {

            e.printStackTrace();
            response.getWriter().println("fail...");
        }

    }
}
