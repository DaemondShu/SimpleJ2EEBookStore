package business;

import javax.ejb.Local;

/**
 * Created by monkey_d_asce on 16-3-31.
 */

@Local
public interface BookAction
{
    String table();

    boolean del(int bookId);

    boolean add(String name, String type, String price);

    String detail(int bookId);
}
