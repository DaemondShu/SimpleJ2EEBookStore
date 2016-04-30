package data;

import entity.Book;
import entity.User;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by monkey_d_asce on 16-4-30.
 */
@Local
public interface DataManager
{

    boolean user_insert(String name, String pwd);

    boolean user_del(int id);

    boolean user_changepwd(String name, String password);

    String user_querypwd(String name);

    int user_queryid(String name);

    String user_queryname(int id);

    List<User> user_queryall();

    boolean book_insert(String name, double price, String type);

    boolean book_del(int book_id);

    boolean book_del(String book_name);

    boolean book_chprice(int book_id, float price);

    List<Book> book_queryall(String type);

    List<Object[]> book_queryone(int book_id);

    String bookDetail(int book_id);

    int book_queryid(String name);

    boolean order_insert(int user_id, int book_id, String address);

    boolean order_del(int order_id);

    List order_query(int user_id);

    List total_userquery();

    List total_typequery();

    List total_datequery();


/*    void TransactionBegin();

    void TransactionCommit();


    void TransactionRollback();*/
}
