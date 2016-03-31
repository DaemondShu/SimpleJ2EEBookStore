package business;

/**
 * Created by monkey_d_asce on 16-3-31.
 */
public interface UserAction
{
    String login(String username, String password);

    boolean changePassword(String username, String pwd1, String pwd2);

    boolean register(String username, String pwd1, String pwd2);

    String table();

    boolean del(int userId);

}
