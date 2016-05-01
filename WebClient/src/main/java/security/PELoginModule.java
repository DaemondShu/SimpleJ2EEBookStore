package security;

import business.UserAction;

import javax.naming.InitialContext;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Map;


/**
 * Created by monkey_d_asce on 16-3-27.
 */

public class PELoginModule implements LoginModule
{
    // 匿名用户的名字, 这个可以是任何字符串,因为我的应用中需要一个匿名用户。
    private static final String GUEST = "guest";
    private Subject subject; // 主角,即被授权的对象,即拥有用户名的那个主体。
    private CallbackHandler callbackHandler; //用来取回的用户名、密码的对象

    private Map<String, ?> sharedState;  //从容器传来的,这儿用不到
    private Map<String, ?> options; // 仍然是从容器传来的,是配置文件中的选项

    private boolean loginSucceeded = false; // 是否成功登录
    private PEPrincipal userPrincipal; // 可以理解成用户名
    private PEGroup roleGroup; // 用户的角色,EJB 是基于角色授权的。

    //把传来的参数都保存到类里。
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options)
    {
        System.out.println("initialize");
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
    }


    /**
     * 阶段 1, 取得应用程序传来的 用户名/密码, 和数据库中的用户名/密码比对,
     * 如果成功,设置 loginSucceeded =true,
     * 并返回 true.
     */
    @Override
    public boolean login() throws LoginException
    {
        try
        {

        System.out.println("login start");

        NameCallback nameCallback = new NameCallback("Username");
        PasswordCallback passwordCallback = new PasswordCallback("Password", false);

        Callback[] callbacks = new Callback[]{nameCallback, passwordCallback};
        try
        {
            callbackHandler.handle(callbacks);
        } catch (IOException ioe)
        {
            throw new LoginException(ioe.toString());
        } catch (UnsupportedCallbackException uce)
        {
            throw new LoginException(uce.toString());
        }
        // 取回来了。
        String username = nameCallback.getName();
        // 在我的应用程序里,验证用户名和密码,需要用到一个 EJB 的服务,所以还需要
        // 授权,这个 EJB 服务还需要身份认证。因此上,用来认证身份的这个 EJB
        // 服务,就不能用用户登录的用户名和密码,而要用一个特殊的,否则就陷入
        // 循环认证的死循环。
        // 判断一下是否是来验证用户名和密码的匿名用户。


        //System.out.println("login " + username);

            //不是匿名用户,取回密码
            String password = new String(passwordCallback.getPassword());
            passwordCallback.clearPassword();
            // 呵呵,这是我的用来认证用户是否合法的 EJB 服务。当然你的可以不同。
            String role = GetRoleAndCheck(username, password);

            //System.out.println("sss   " + role);


            // 根据用户名和密码成功得到 OperatorEntity 对象
            //下面这几行很重要
            // 存起来用户名
            userPrincipal = new PEPrincipal(role); //TODO username&role
            // 存起来用户的角色。看看下面这个 Group 对象名字必须叫”Roles”。
            roleGroup = new PEGroup("Roles");
            //我的应用里,每个用户只有一个角色,你的如果可以有多个,
            //那么多重复几次下面的行就行了。
            roleGroup.addMember(new PEPrincipal(role));
            // 设置登录成功状态
            loginSucceeded = true;
            // 将调用者表示,登录成功。
            return true;

        } catch (Exception e)
        {
            e.printStackTrace();
            return true;
        }
    }


    private String GetRoleAndCheck(String username, String password)
            throws LoginException
    {

        try
        {

            //return "admin";

            if (username.length() <= 0 || password.length() <= 0)
            {
                //loginSucceeded = false;
                return GUEST;
            }

            //经过测试,在idea里内部module交互用java:module,在外面
            //Hello hello = (Hello) new InitialContext().lookup("java:module/HelloEJB!test.Hello");
            //Hello hello = (Hello) context.lookup("HelloEJB/local");
            UserAction userAction = (UserAction) new InitialContext().lookup("java:module/UserActionEJB!business.UserAction");

            //TODO
            String result = userAction.login(username, password);

            System.out.println("result:" + result);

            return result;


        } catch (Exception e)
        {
            e.printStackTrace();
            return GUEST;
        }
    }


    @Override
    public boolean commit() throws LoginException
    {
        // 判断是否已经成功调用 login()
        if (loginSucceeded == false)
        {
            return false;
        }
        // 认证了 subject 的身份了。

        subject.getPrincipals().add(userPrincipal);
        subject.getPrincipals().add(roleGroup);

        //System.out.println(subject.getPrincipals());
        return true;
    }


    /**
     * 这是 login 不成功的处理,主要是清理工作
     */
    @Override
    public boolean abort() throws LoginException
    {
        if (loginSucceeded == false)
        {
            userPrincipal = null;
            roleGroup = null;
            return false;
        } else
        {
            logout();
            return true;
        }
    }


    /**
     * 退出登录
     */
    @Override
    public boolean logout() throws LoginException
    {
        return false;
    }
}
