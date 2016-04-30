package data;

import entity.Book;
import entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by monkey_d_asce on 16-3-26.
 * 提供各种数据库的单词操作。
 */

@Stateless(name = "DataManagerEJB")
public class DataManagerBean implements DataManager
{
    @PersistenceContext(unitName = "JPADB")
    private EntityManager entityManager;


    public DataManagerBean()
    {
    }

/*
    public void TransactionBegin()
    {
        entityManager.getTransaction().begin();
    }

    public void TransactionCommit()
    {
        entityManager.getTransaction().commit();
    }

    public void TransactionRollback()
    {
        entityManager.getTransaction().rollback();
    }

*/

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
            // e.printStackTrace();
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

    public List<User> user_queryall()
    {
        try
        {
            Query query = entityManager.createQuery("SELECT u FROM user u order by u.userId");

            //TODO
            List<User> result = query.getResultList();


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

    public List<Book> book_queryall(String type)
    {

        //Statement stmt;
        try
        {
            String sql = type != null ? "SELECT b FROM book b WHERE b.type='" + type + "' order by b.bookId" : "SELECT b FROM book b order by bookId";


            Query query = entityManager.createQuery(sql);
            List<Book> result = query.getResultList();
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
            //e.printStackTrace();
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
            String sql = "DELETE FROM `order` WHERE orderId=" + order_id;
            //保留字
            Query query = entityManager.createNativeQuery(sql);
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

    public List order_query(int user_id)
    {
        List result = new ArrayList();
        try
        {
            String sql = user_id > -1 ? "Select orderId,name,price,time  FROM  bookstore.order natural join bookstore.book where userId= :userId order by time desc"
                    : "Select orderId,name,price,time  FROM  bookstore.order natural join bookstore.book";

            //  String hql = user_id > -1 ? "Select o.orderId,b.name,b.price,o.time  FROM order as o join book as b where o.userId=" + user_id + " order by o.time desc"
            //          : "Select o.orderId,b.name,b.price,o.time  FROM  order o join book b";

            // String hql = user_id > -1 ? "Select o.orderId,b.name,b.price,o.time  FROM order as o , book as b where (o.userId= :userId and b.bookId=o.bookId ) order by o.time desc"
            //            : "Select o.orderId,b.name,b.price,o.time  FROM  order o join book b";


            Query query = entityManager.createNativeQuery(sql);

            query.setParameter("userId", user_id);

            List<Object[]> tmp = query.getResultList();

            for (Object[] item : tmp)
            {
                Map map = new HashMap<String, Object>();
                map.put("orderId", (int) item[0]);
                map.put("name", (String) item[1]);
                map.put("price", (float) item[2]);
                map.put("time", item[3].toString());
                result.add(map);
            }

            //List<Map> temp = result;

            return result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public List total_userquery()
    {
        List result = new ArrayList();
        try
        {

            //String	sql="Select USER_ID,name,sum(price) total FROM user  natural left outer join ( SELECT USER_ID,price FROM bookstore.order natural join bookstore.book) aa group by USER_ID,name order by total desc ";
            String sql = "Select userId,name,sum(price) total FROM bookstore.user natural left outer join ( SELECT userId,price FROM bookstore.order natural join bookstore.book) aa group by userId,name order by total desc ";
            Query query = entityManager.createNativeQuery(sql);


            List<Object[]> tmp = query.getResultList();

            for (Object[] item : tmp)
            {
                Map map = new HashMap<String, Object>();
                if (item[2] == null || (Double) item[2] <= 0) continue;
                map.put("userId", (int) item[0]);
                map.put("name", (String) item[1]);
                map.put("total", (Double) item[2]);
                result.add(map);
            }

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
