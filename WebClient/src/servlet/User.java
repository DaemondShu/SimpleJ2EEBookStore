package servlet;

import business.UserAction;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.RuntimeNode;
import jdk.nashorn.internal.parser.JSONParser;

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
 * 用于接受用户登陆检查、注册、修改密码等等请求
 */
@WebServlet("/User")
public class User extends HttpServlet
{
    private static final String ACTION = "action";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String PWD1 = "pwd1";
    private static final String PWD2 = "pwd2";
    private static final String USERID = "id";


    @EJB(name = "UserAction")
    private UserAction userAction;

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
            //System.out.println(request.getParameterMap());
            //response.setContentType("Content-Type:text/json");
            switch (val(ACTION))
            {
                case "login":
                    System.out.println(val(USERNAME) + ":" + val(PASSWORD));
                    request.logout(); //否则重复登陆的时候可能会报错
                    request.login(val(USERNAME), val(PASSWORD));
                    System.out.println("principal: " + request.getUserPrincipal());
                    response.getWriter().print(request.getUserPrincipal());
                    request.getSession().setAttribute("username", val(USERNAME));

                    break;

                case "register":
                    if (!userAction.register(val(USERNAME), val(PWD1), val(PWD2)))
                        throw new Exception("register failed");
                    break;

                case "chpwd":
                    if (!userAction.register(val(USERNAME), val(PWD1), val(PWD2)))
                        throw new Exception("change password failed");
                    break;

                case "table":
                    response.getWriter().println(userAction.table());
                    break;

                case "del":
                    int userId = Integer.parseInt(val(USERID));
                    if (!userAction.del(userId))
                        throw new Exception("delete user failed");
                default:
                    throw new Exception("invalid action");
            }
            //login


        }

        catch (Exception e)
        {
            //e.printStackTrace()；
            System.out.println(e.getMessage());
            e.printStackTrace();
            response.sendError(222, e.getMessage());
        }
        writer.flush();
        writer.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request,response);
    }
}
