package test;

import javax.ejb.EJB;
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


            response.getWriter().println(hello.sayHello());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        //System.out.println(helloBean.sayHello());


        //response.getWriter().println(middle.ret());
        //
    }
}
