package business;

import data.DataManager;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by monkey_d_asce on 16-3-31.
 */
@Stateless(name = "UserActionEJB")
public class UserActionBean implements UserAction
{
    @PersistenceContext(unitName = "JPADB")
    private EntityManager entityManager;


    public UserActionBean()
    {
    }


    @Override
    public String login(String username, String password)
    {
        try
        {
            DataManager dataManager = new DataManager(entityManager);
            String truepwd = dataManager.user_querypwd(username);
            if (password.equals(truepwd))
            {
                //  session.put("username", username);

                if (username.equals("admin"))
                    return "admin"; //response.sendRedirect("manage.jsp?login=1021");
                else
                    return "user";  //response.sendRedirect("shop.jsp?login=1021");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean changePassword(String username, String pwd1, String pwd2)
    {
        try
        {
            if (pwd1 != null && pwd2 != null)
            {
                DataManager dataManager = new DataManager(entityManager);

                //AccessDB ADB = new AccessDB();
                String truePassword = dataManager.user_querypwd(username);
                if (pwd1.equals(truePassword))
                {
                    dataManager.user_changepwd(username, pwd2);
                    return true;  //response.sendRedirect("shop.jsp?login=1021");
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;  //response.sendRedirect("login.jsp?login=pwdfail");

    }

    @Override
    public boolean register(String username, String pwd1, String pwd2)
    {
        try
        {
            if (pwd1 != null && pwd2 != null)
            {
                DataManager dataManager = new DataManager(entityManager);

                if (pwd1.equals(pwd2))
                {
                    //AccessDB ADB = new AccessDB();
                    return dataManager.user_insert(username, pwd1);

                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String table()
    {
        String result = "";
        try
        {
            DataManager dataManager = new DataManager(entityManager);

            //AccessDB ADB = new AccessDB();
            List<Object[]> resultList = dataManager.user_queryall();
            if (resultList != null)
            {
                // PrintWriter out=response.getWriter();
                for (Object[] obj : resultList)
                {
                    result += "<tr>";
                    for (int i = 0; i < 2; i++)
                        result += "<td>" + obj[i].toString() + "</td>";
                    if (Integer.parseInt(obj[0].toString()) != 1)
                        result += "<td><a onclick=\"deluser(" + obj[0].toString() + ")\" href=\"#\"> <span class=\"glyphicon glyphicon-remove\"> </span> </a></td>";
                    else
                    {
                        result += "<td></td>";
                    }
                    result += "</tr>";
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean del(int userId)
    {
        try
        {
            DataManager dataManager = new DataManager(entityManager);
            //AccessDB ADB = new AccessDB();

            if (userId >= 0)
            {
                return dataManager.user_del(userId);

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
