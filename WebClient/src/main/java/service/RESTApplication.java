package service;

import javax.naming.NamingException;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by monkey_d_asce on 16-5-20.
 */

@ApplicationPath("/BookREST")
public class RESTApplication extends Application
{

    private Set<Class<?>> classes = new HashSet<Class<?>>();

    public RESTApplication() throws NamingException
    {
        // no instance is created, just class is listed
        classes.add(BookREST.class);
        // 网上有很多教程都是说这边singletons添加 new BookREST(), 最后看了这个，https://developer.jboss.org/thread/170194
    }

    @Override
    public Set<Class<?>> getClasses()
    {
        return classes;
    }

}