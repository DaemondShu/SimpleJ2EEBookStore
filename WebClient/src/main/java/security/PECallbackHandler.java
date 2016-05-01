package security;

import javax.security.auth.callback.*;
import java.io.IOException;

/**
 * Created by monkey_d_asce on 16-3-28.
 */
public class PECallbackHandler implements CallbackHandler
{
    private String username;
    private String password;


    public PECallbackHandler(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException
    {
        //System.out.println("handle");
        for (Callback callback : callbacks)
        {
            if (callback instanceof NameCallback)
            {
                ((NameCallback) callback).setName(username);
            } else if (callback instanceof PasswordCallback)
            {
                ((PasswordCallback) callback).setPassword(password.toCharArray());
            }
        }
        //System.out.println("handle over");
    }
}
