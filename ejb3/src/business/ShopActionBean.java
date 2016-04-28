package business;

import data.DataManager;
import entity.Order;
import net.sf.json.JSONObject;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.transform.Result;
import java.util.List;

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
    public String getOrder(String username)
    {
        String result = "";
        try
        {
            DataManager dataManager = new DataManager(entityManager);

            int user_id = dataManager.user_queryid(username);

            System.out.println(user_id);
            List<Order> rs = dataManager.order_query(user_id);

            System.out.println(JSONObject.fromObject(rs));

            return result;

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

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
        /*
        try
        {
            AccessDB ADB = new AccessDB();
            if (orderid != null)
            {
                System.out.println(orderid);
                ADB.order_del(Integer.parseInt(orderid));
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        */
        return false;
    }
}
