package servlet;

import business.ShopAction;
import exception.StoreException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Created by monkey_d_asce on 16-3-31.
 * 购买操作
 */
@WebServlet("/Shop")
public class Shop extends HttpServlet
{
    private static final String ACTION = "action";
    private static final String BOOKID = "bookId";
    private static final String USERCART = "userCart";
    private static final String CARTDATA = "cartData";
    private static final String USERID = "userId";
    private static final String ORDERID = "orderId";
    private static final String USERNAME = "username";
    private static final int ERRORCODE = 520;

    //private static final String
    // private static final String

    @EJB(name = "ShopAction")
    private ShopAction shopAction;

    private HttpServletRequest request;
    private HttpSession session;

    private String val(String key)
    {
        String temp = request.getParameter(key);
        return (temp == null) ? "" : temp;
    }

    //TODO cart放缓存

    private void cartInsert(int bookId, String userCart)
    {
        session.setAttribute(userCart, cartGet(userCart) + ";" + bookId);
    }

    private void cartSet(String userCart, String data)
    {
        if (data == null)
            data = "";
        session.setAttribute(userCart, data);
    }


    private String cartGet(String userCart)
    {
        String tmp = (String) session.getAttribute(userCart);
        return tmp == null ? "" : tmp;

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.request = request;
        this.session = request.getSession(true);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();

        try
        {

            switch (val(ACTION))
            {

                // cart actions
                case "cartInsert":
                    int bookId = Integer.parseInt(val(BOOKID));
                    cartInsert(bookId, val(USERCART));
                    break;
                case "cartSet":
                    cartSet(val(USERCART), val(CARTDATA));
                    break;
                case "cartGet":
                    writer.print(cartGet(val(USERCART)));
                    break;

                // order actions - cart actions
                case "buy":
                    // int userId = Integer.parseInt(val(USERID));
                    if (!shopAction.buy(cartGet(val(USERCART)), val(USERNAME)))
                        throw new StoreException("backend failed");
                    cartSet(val(USERCART), "");
                    break;

                case "delOrder":
                    int orderId = Integer.parseInt(val(ORDERID));
                    if (!shopAction.delOrder(orderId))
                        throw new StoreException("delete order failed");
                    break;

                case "getOrder":
                    writer.print(shopAction.getOrder(val(USERNAME)));
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
