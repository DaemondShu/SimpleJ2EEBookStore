package business;

import data.DataManager;
import entity.Order;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

/**
 * Created by monkey_d_asce on 16-3-31.
 */
@Stateful(name = "ShopActionEJB")
public class ShopActionBean implements ShopAction
{

    @PersistenceContext(unitName = "JPADB")
    private EntityManager entityManager;

    public ShopActionBean()
    {
    }


    /*
    TODO: NEED FIX
     */

    @Override
    public boolean buy(String cartData, int userId)
    {

        try
        {
            DataManager dataManager = new DataManager(entityManager);


            String[] books = cartData.split(";");

            for (int i = 0; i < books.length; i++)
                if (!books[i].equals(""))
                {
                    int bookId = Integer.parseInt(books[i]);
                    dataManager.order_insert(userId, bookId, "address");
                }

            //TODO ALL OR NOTHING

            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean buy(String cartData, String username)
    {
        try
        {
            DataManager dataManager = new DataManager(entityManager);
            int userId = dataManager.user_queryid(username);
            if (userId <= 0) return false;
            else return buy(cartData, userId);
        } catch (Exception e)
        {
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
            DataManager dataManager = new DataManager(entityManager);

            int user_id = dataManager.user_queryid(username);

            //System.out.println(user_id);
            List<Map> rs = dataManager.order_query(user_id);

            result = JSONArray.fromObject(rs).toString();
            //System.out.println(result);


        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;

        /*
                try
		{
			AccessDB ADB = new AccessDB();
			int user_id = ADB.user_queryid(username);
			System.out.println(user_id);
			List<Object[]> rs = ADB.order_query(user_id);
			if (rs != null)
			{

				for (Object[] obj : rs)
				{
					out.print("<tr>");
					for (int i = 0; i < 4; i++)
						out.print("<td>" + obj[i].toString() + "</td>");
					out.print("<td><a onclick=\"delorder("
							+ obj[0].toString()
							+ ")\" href=\"#\"> <span class=\"glyphicon glyphicon-remove\"> </span> </a></td>");

					out.print("</tr>");
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		out.print("");
		out.close();
         */
    }

    @Override
    public boolean delOrder(int orderId)
    {

        try
        {
            DataManager dataManager = new DataManager(entityManager);
            if (orderId >= 0)
            {
                //System.out.println(orderid);
                dataManager.order_del(orderId);
                return true;
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
