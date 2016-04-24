package data;

import entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by monkey_d_asce on 16-3-26.
 * TODO MOVETO BUSSINESS
 */
public class DataManager
{
    private EntityManager entityManager;

    public DataManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    public boolean user_insert(String name, String pwd)
    {
        try
        {
            //TODO Persist
            Query query = entityManager.createNativeQuery("INSERT into user(name,password) VALUES('" + name + "','" + pwd + "')");
            query.executeUpdate();

            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public boolean user_del(int id)
    {
        try
        {
            //SQL
            Query query = entityManager.createQuery("DELETE FROM user WHERE userId =" + id);
            query.executeUpdate();

            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean user_changepwd(String name, String password)
    {
        try
        {
            Query query = entityManager.createQuery("UPDATE user SET password ='" + password + "' WHERE name='" + name + "'");
            query.executeUpdate();

            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public String user_querypwd(String name)
    {
        try
        {

            Query query = entityManager.createQuery("SELECT password FROM user WHERE name=:name"); //HQL CAN'T SELECT *

            query.setParameter("name", name);

            String result = (String) query.getSingleResult();

            return result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }


    public int user_queryid(String name)
    {
        try
        {
            Query query = entityManager.createNativeQuery("SELECT userId FROM user WHERE name='" + name + "'");

            int result = -1;

            if (query.getSingleResult() != null)
                result = (Integer) query.getSingleResult();

            if (result <= 0) return -1;
            else return result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public String user_queryname(int id)
    {
        try
        {

            Query query = entityManager.createQuery("SELECT name FROM user WHERE userId=" + id);

            String result = (String) query.getSingleResult();

            return result;

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object[]> user_queryall()
    {
        try
        {
            Query query = entityManager.createQuery("SELECT u FROM user u order by u.userId");

            //TODO
            List<Object[]> result = query.getResultList();  //TODO

            return result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public boolean book_insert(String name, double price, String type)
    {


        try
        {
            int id = book_queryid(name);
            Book book = new Book(name, type, price);

            if (id == -1)  //insert
            {
                entityManager.persist(book);
            } else //update
            {
                entityManager.merge(book);
            }
            entityManager.flush(); // TODO

            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean book_del(int book_id)
    {

        try
        {
            Query query = entityManager.createQuery("DELETE FROM book WHERE bookId=" + book_id);
            query.executeUpdate();
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean book_del(String book_name)
    {
        try
        {
            Query query = entityManager.createQuery("DELETE FROM book WHERE name='" + book_name + "'");
            query.executeUpdate();

            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean book_chprice(int book_id, float price)
    {
        try
        {

            Query query = entityManager.createQuery("UPDATE book SET price= " + price + " WHERE bookId=" + book_id);

            query.executeUpdate();

            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public List<Object[]> book_queryall(String type)
    {

        //Statement stmt;
        try
        {
            String sql = type != null ? "SELECT b FROM book b WHERE b.type='" + type + "' order by b.bookId" : "SELECT b FROM book b order by BOOK_ID";


            Query query = entityManager.createQuery(sql);

            List<Object[]> result = query.getResultList();
            return result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object[]> book_queryone(int book_id)
    {
        try
        {
            String sql = "SELECT b FROM book b WHERE bookId=" + book_id;

            Query query = entityManager.createNativeQuery(sql);

            List<Object[]> result = query.getResultList();
            return result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public int book_queryid(String name)
    {
        try
        {
            Query query = entityManager.createQuery("SELECT bookId FROM book WHERE name='" + name + "'");

            int id = -1;
            if (query.getSingleResult() != null)
                id = (Integer) query.getSingleResult();
            return id;
        } catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }


    public boolean order_insert(int user_id, int book_id, String address)
    {
        try
        {
            Date shopdate = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

            String strdate = sf.format(shopdate);
            String sql = "INSERT into `order`(userId,bookId,address,time) VALUES(" + user_id
                    + "," + book_id + ",'" + address + "','" + strdate + "')";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean order_del(int order_id)
    {
        try
        {
            String sql = "DELETE FROM order WHERE orderId=" + order_id;
            Query query = entityManager.createQuery(sql);
            query.executeUpdate();
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

/*	public boolean order_chaddress(int order_id,String address,int user_id)
    {
		Statement stmt;
		try
		{
			stmt = conn.createStatement();
			String sql= "UPDATE `order` SET address ='" + address + "' WHERE ORDER_ID=" + order_id+" and USER_ID="+user_id;
			System.out.print(sql);
			stmt.executeUpdate(sql);
			return true;
		}
		catch ( Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}*/

    public List<Object[]> order_query(int user_id)
    {
        try
        {
            String sql = user_id > -1 ? "Select orderId,name,price,time  FROM  bookstore.order natural join bookstore.book where user_id=" + user_id + " order by time desc"
                    : "Select orderId,name,price,time  FROM  bookstore.order natural join bookstore.book";

            Query query = entityManager.createNativeQuery(sql);

            List<Object[]> result = query.getResultList();
            return result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public List<Object[]> total_userquery()
    {
        try
        {
            //String	sql="Select USER_ID,name,sum(price) total FROM bookstore.user natural left outer join ( SELECT USER_ID,price FROM bookstore.order natural join bookstore.book) aa group by USER_ID,name order by total desc ";
            String sql = "Select USER_ID,name,sum(price) total FROM bookstore.user natural left outer join ( SELECT USER_ID,price FROM bookstore.order natural join bookstore.book) aa group by USER_ID,name order by total desc ";
            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> result = query.getResultList();
            return result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object[]> total_typequery()
    {
        try
        {
            String sql = "Select type,sum(price) total FROM  ( SELECT type,price FROM bookstore.order natural join bookstore.book) aa group by type";
            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> result = query.getResultList();
            return result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public List<Object[]> total_datequery()
    {
        try
        {
            String sql = "SELECT time,sum(price) FROM bookstore.order natural join bookstore.book group by time;";
            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> result = query.getResultList();
            return result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
