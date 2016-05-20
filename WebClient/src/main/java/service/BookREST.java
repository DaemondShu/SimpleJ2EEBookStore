package service;

import business.BookAction;

import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by monkey_d_asce on 16-5-19.
 */

@Path("/")
public class BookREST
{


    /*
      rest访问不到是因为RESTApplication没搞好
      ejb可能会出现null的情况，要注意补上beans.xml，这个貌似叫cdi注入，原理不是很懂，错误解决网站见https://developer.jboss.org/thread/170194,这个错硬是调了半天
      网上那些靠配置web.xml的解决ejb的都是不行的，web.xml里对rest的配置其实都在RESTApplication里了！
     */
    @EJB(name = "BookAction")
    private BookAction bookAction;


    @GET
    @Path("/table")
    @Produces(MediaType.TEXT_PLAIN)
    public String table() throws NamingException
    {

        return bookAction.table();
    }

    @GET
    @Path("/detail/{bookId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String detail(@PathParam("bookId") int bookId)
    {
        return bookAction.detail(bookId);
    }
}
