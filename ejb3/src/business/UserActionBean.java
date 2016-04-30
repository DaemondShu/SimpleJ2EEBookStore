package business;

import data.DataManager;
import entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by monkey_d_asce on 16-3-31.
 */
@Stateless(name = "UserActionEJB")
public class UserActionBean implements UserAction
{
    //@PersistenceContext(unitName = "JPADB")
    //private EntityManager entityManager;

    @EJB(name = "DataManager")
    DataManager dataManager;

    public UserActionBean()
    {

    }


    @Override
    public String login(String username, String password)
    {
        try
        {
            //DataManager dataManager = new DataManager(entityManager);
            String truepwd = dataManager.user_querypwd(username);
            if (password.equals(truepwd))
            {
                //  session.put("username", username);

                if (username.equals("admin"))
                    return "admin";
                else
                    return "user";
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "guest";
    }

    @Override
    public boolean changePassword(String username, String pwd1, String pwd2)
    {
        try
        {
            if (pwd1 != null && pwd2 != null)
            {

                String truePassword = dataManager.user_querypwd(username);
                if (pwd1.equals(truePassword))
                {
                    dataManager.user_changepwd(username, pwd2);
                    return true;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean register(String username, String pwd1, String pwd2)
    {
        try
        {
            if (pwd1 != null && pwd2 != null)
            {

                if (pwd1.equals(pwd2))
                {

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
        String result = "[]";
        try
        {
            //DataManager dataManager = new DataManager(entityManager);

            //AccessDB ADB = new AccessDB();
            List<User> resultList = dataManager.user_queryall();
            if (resultList != null)
            {
                JsonConfig exclude = new JsonConfig();
                exclude.setExcludes(new String[]{"password"});
                result = JSONArray.fromObject(resultList, exclude).toString();

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
