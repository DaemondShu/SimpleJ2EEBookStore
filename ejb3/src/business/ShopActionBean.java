package business;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public boolean buy(String userCart, int userId)
    {
        /*
        try
        {
            DataManager dataManager = new DataManager(entityManager);


            String username = userCart.substring(0, userCart.lastIndexOf("cart"));

            int userid = dataManager.user_queryid(username);
            String booksess = tmp.toString();
            String[] books = booksess.split(";");
            for (int i = 0; i < books.length; i++)
                if (!books[i].equals(""))
                {
                    DataManager.order_insert(userid,
                            Integer.parseInt(books[i]), "address");
                }
            session.put(usercart, "");

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        out.print("");
        out.close();
        */
        return false;

    }

    @Override
    public String getOrder(String username)
    {
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
        return null;
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
