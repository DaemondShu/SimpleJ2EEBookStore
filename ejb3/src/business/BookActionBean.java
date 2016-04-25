package business;

import data.DataManager;
import entity.Book;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by monkey_d_asce on 16-3-29.
 */
@Stateless(name = "BookEJB")
public class BookActionBean implements BookAction
{
    @PersistenceContext(unitName = "JPADB")
    private EntityManager entityManager;

    public BookActionBean()
    {

    }

    public String table()
    {
        String result = "";
        try
        {
            DataManager dataManager = new DataManager(entityManager);

            List<Book> rs = dataManager.book_queryall(null);

            result = JSONArray.fromObject(rs).toString();

            /*
            for (Book obj : rs)
            {
                System.out.println(JSONObject.fromObject(obj));
            }*/


            /*
            if (rs != null)
            {

                for (Object[] obj : rs)
                {
                    result += "<tr>";
                    for (int i = 0; i < 4; i++)
                        result += "<td>" + obj[i].toString() + "</td>";
                    result += "<td><a onclick=\"delbook(" + obj[0].toString() + ")\" href=\"#\"> <span class=\"glyphicon glyphicon-remove\"> </span> </a></td>";
                    result += "</tr>";
                }
            }*/

        } catch (Exception e)
        {
            e.printStackTrace();
            result = "[]";
        }
        return result;
    }

    public boolean del(int bookId)
    {
        try
        {
            DataManager dataManager = new DataManager(entityManager);
            if (bookId >= 0)
            {
                return dataManager.book_del(bookId);
            }
        } catch (Exception e)
        {
            e.printStackTrace();

        }
        return false;
    }

    public boolean add(String name, String type, String price)
    {
        try
        {
            DataManager dataManager = new DataManager(entityManager);

            if (name != null && type != null && price != null)
            {
                if (dataManager.book_insert(name, Float.parseFloat(price), type))
                    return true;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }


}
