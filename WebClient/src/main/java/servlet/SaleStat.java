package servlet;

import business.SaleStatAction;
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
 * Created by monkey_d_asce on 16-4-24.
 */
@WebServlet("/SaleStat")
public class SaleStat extends HttpServlet
{
    private static final String ACTION = "action";
    private static final int ERRORCODE = 520;
    @EJB(name = "SaleStatAction")
    SaleStatAction saleStatAction;
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
                case "userStat":
                    writer.print(saleStatAction.CountByUser());
                    break;
                case "typeStat":
                    writer.print(saleStatAction.CountByType());
                    break;
                case "dateStat":
                    writer.print(saleStatAction.CountByDate());
                    break;
                default:
                    throw new StoreException("invalid actions");
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
