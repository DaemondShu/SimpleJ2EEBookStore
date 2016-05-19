package business;

import cache.CacheManager;
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


/*
本来是打算把ejb直接打包成webservice，因为ejb安全域和外部servlet安全域不是同一个，会出现securityDomain冲突替代的情况，不知道怎么统一，就不搞了。
@WebService (
        name = "BookAction",
        targetNamespace = "business"
      //  serviceName = "BookActionService"
)
@SOAPBinding(style = SOAPBinding.Style.RPC)
*/

@Stateless(name = "BookEJB")
public class BookActionBean implements BookAction
{
    static final String BOOK = "BOOK";
    static final String TABLE = "TABLE";

    @EJB(name = "DataManager")
    DataManager dataManager;

    @EJB(name = "CacheManager")
    CacheManager cacheManager;

    public BookActionBean()
    {

    }


    @Override
    // @WebMethod(operationName = "table", action = "table")
    public String table()
    {

        String result = "";
        try
        {
            String temp = cacheManager.get(BOOK + "." + TABLE);
            if (temp == null) //cahce miss
            {

                List<Book> rs = dataManager.book_queryall(null);

                JsonConfig exclude = new JsonConfig();
                //exclude.setExcludes(new String[] {"price"});
                result = JSONArray.fromObject(rs, exclude).toString();

                cacheManager.set(BOOK + "." + TABLE, result);
            } else // cache hit
                result = temp;



        } catch (Exception e)
        {
            e.printStackTrace();
            result = "[]";
        }


        return result;
    }

    // @WebMethod(exclude = true)
    @Override
    public boolean del(int bookId)
    {
        try
        {
            //DataManager dataManager = new DataManager(entityManager);
            if (bookId >= 0)
            {
                if (dataManager.book_del(bookId))
                {
                    cacheManager.clear();  //管理员修改了书本信息，缓存内的数据已经过时，需要清空。
                    return true;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    // @WebMethod(exclude = true)
    @Override
    public boolean add(String name, String type, String price)
    {
        try
        {
            //DataManager dataManager = new DataManager(entityManager);

            if (name != null && type != null && price != null)
            {
                if (dataManager.book_insert(name, Float.parseFloat(price), type))
                {
                    cacheManager.clear(); //管理员修改了书本信息，缓存内的数据已经过时，需要清空。
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
    // @WebMethod(operationName = "detail" , action = "detail")
    public String detail( /*@WebParam(name = "bookId") */int bookId)
    {
        String result = "";
        try
        {
            String temp = cacheManager.get(BOOK + "." + bookId);
            if (temp == null) //cahce miss
            {

                result = dataManager.bookDetail(bookId);
                cacheManager.set(BOOK + "." + bookId, result);
            } else // cache hit
                result = temp;

        } catch (Exception e)
        {
            e.printStackTrace();
            result = "nothing";
        }
        return result;
    }


}
