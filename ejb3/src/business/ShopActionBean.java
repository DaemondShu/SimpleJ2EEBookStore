package business;

import data.DataManager;
import net.sf.json.JSONArray;

import javax.annotation.Resource;
import javax.ejb.*;
import java.util.List;
import java.util.Map;

/**
 * Created by monkey_d_asce on 16-3-31.
 */

@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless(name = "ShopActionEJB")
public class ShopActionBean implements ShopAction
{


    @EJB(name = "DataManager")
    DataManager dataManager;

    @Resource
    private SessionContext context;

    public ShopActionBean()
    {
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public boolean buy(String cartData, int userId)
    {

        try
        {
            //DataManager dataManager = new DataManager(entityManager);
            // dataManager.TransactionBegin();
            //UserTransaction

            String[] books = cartData.split(";");

            for (int i = 0; i < books.length; i++)
                if (!books[i].equals(""))
                {
                    int bookId = Integer.parseInt(books[i]);
                    if (!dataManager.order_insert(userId, bookId, "address"))
                        throw new Exception("bad order_insert");
                }

            //TODO ALL OR NOTHING
            // dataManager.TransactionCommit();
            return true;
        } catch (Exception e)
        {
            //dataManager.TransactionRollback();
            context.setRollbackOnly();
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean buy(String cartData, String username)
    {
        try
        {
            //DataManager dataManager = new DataManager(entityManager);
            int userId = dataManager.user_queryid(username);
            if (userId <= 0) return false;
            else return buy(cartData, userId);
        } catch (Exception e)
        {
            context.setRollbackOnly();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getOrder(String username)
    {
        String result = "";
        try
        {
            //DataManager dataManager = new DataManager(entityManager);

            int user_id = dataManager.user_queryid(username);

            //System.out.println(user_id);
            List<Map> rs = dataManager.order_query(user_id);

            result = JSONArray.fromObject(rs).toString();
            //System.out.println(result);


        } catch (Exception e)
        {
            context.setRollbackOnly();
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public boolean delOrder(int orderId)
    {

        try
        {
            //DataManager dataManager = new DataManager(entityManager);
            if (orderId >= 0)
            {
                //System.out.println(orderid);
                dataManager.order_del(orderId);
                return true;
            }

        } catch (Exception e)
        {
            context.setRollbackOnly();
            e.printStackTrace();
        }

        return false;
    }
}
