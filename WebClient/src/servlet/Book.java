package servlet;

import business.BookAction;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by monkey_d_asce on 16-3-31.
 */
@WebServlet("/Book")
public class Book extends HttpServlet
{
    private static final String ACTION = "action";

    @EJB(name = "BookAction")
    private BookAction bookAction;

    private HttpServletRequest request;

    private String val(String key)
    {
        String temp = request.getParameter(key);
        return (temp == null) ? "" : temp;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.request = request;
        request.getSession(true);
        response.setCharacterEncoding("UTF-8");

        //response.setContentType("application/json; charset=utf-8");
        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();

        try
        {
            switch (val(ACTION))
            {
                case "table":
                    writer.print(bookAction.table());
                    break;
                //TODO
                default:
                    throw new Exception("invalid action");
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
            response.sendError(222, e.getMessage());
        }
        writer.flush();
        writer.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
