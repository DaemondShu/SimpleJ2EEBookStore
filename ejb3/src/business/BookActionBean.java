package business;

import data.DataManager;
import entity.Book;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by monkey_d_asce on 16-3-29.
 */
@Stateless(name = "BookEJB")
public class BookActionBean implements BookAction
{
    @EJB(name = "DataManager")
    DataManager dataManager;

    public BookActionBean()
    {

    }

    public String table()
    {
        String result = "";
        try
        {
            //DataManager dataManager = new DataManager(entityManager);

            List<Book> rs = dataManager.book_queryall(null);

            JsonConfig exclude = new JsonConfig();
            //exclude.setExcludes(new String[] {"price"});
            result = JSONArray.fromObject(rs, exclude).toString();


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
            //DataManager dataManager = new DataManager(entityManager);
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
            //DataManager dataManager = new DataManager(entityManager);

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

    @Override
    public String detail(int bookId)
    {
        try
        {
            return dataManager.bookDetail(bookId);
            //DataManager dataManager = new DataManager(entityManager);
        } catch (Exception e)
        {
            e.printStackTrace();
            return "nothing";
        }
    }


}
