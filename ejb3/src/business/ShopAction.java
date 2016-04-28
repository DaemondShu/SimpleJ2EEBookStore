package business;

/**
 * Created by monkey_d_asce on 16-3-31.
 * 订单操作
 */
public interface ShopAction
{

    /*
    //String get();  TODO: move to servlet
    boolean add(int bookId,String usercart);
    boolean set(String usercart);
    */


    String getOrder(String username);

    boolean delOrder(int orderId);

    boolean buy(String cartData, int userId);
}
