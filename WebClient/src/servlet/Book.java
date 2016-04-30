package servlet;

import business.BookAction;
import exception.StoreException;

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
    // key names
    private static final String ACTION = "action";
    private static final String BOOKID = "bookId";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String PRICE = "price";
    private static final int ERRORCODE = 520;


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
                case "del":
                    int bookId = Integer.parseInt(val(BOOKID));
                    if (!bookAction.del(bookId))
                        throw new StoreException("delete book failed");
                    break;
                case "add":
                    if (!bookAction.add(val(NAME), val(TYPE), val(PRICE)))
                        throw new StoreException("insert book failed");
                    break;
                default:
                    throw new StoreException("invalid action");
            }
        } catch (StoreException e)
        {
            String t = e.getMessage();
            System.out.println(t);
            response.setStatus(ERRORCODE);
            writer.write(t);
        } catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(500);
        }
        writer.flush();
        writer.close();

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

}
