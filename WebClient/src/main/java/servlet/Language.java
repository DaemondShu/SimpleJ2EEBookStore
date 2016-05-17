package servlet;

import exception.StoreException;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Created by monkey_d_asce on 16-5-17.
 */
@WebServlet("/Language")
public class Language extends HttpServlet
{
    private static final String LANGUAGE = "language";
    private static final String CHINESE = "cn";
    private static final String ENGLISH = "en";
    private static final String ResourceBundleName = "webElements";

    private static final int ERRORCODE = 520;

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
            /*
            如果出现中文乱码，是因为properties是没法直接写中文的，把里面的语言转成unciode就好了。
            注：第一个参数UTF-8是表示源文件里面的中文编码，在linux下默认是utf-8，windows下可能是GBK
            在我这边我把中文放在webElements_tempcn，这个文件我是不读的，真正读写的文件的是根据以下这句中文转码语句生成真正被读的webELements_cn
            native2ascii -encoding UTF-8 webElements_tempcn.properties webElements_cn.properties
            */

            Locale locale = new Locale(val(LANGUAGE));
            ResourceBundle dict = ResourceBundle.getBundle(ResourceBundleName, locale);
            if (dict == null || dict.keySet().isEmpty())
                throw new StoreException("no such language");

            JSONObject json = new JSONObject();
            for (String key : dict.keySet())
            {
                json.put(key, dict.getString(key));
            }
            writer.println(json.toString());


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
