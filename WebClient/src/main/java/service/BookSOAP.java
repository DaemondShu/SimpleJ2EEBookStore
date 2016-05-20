package service;

import business.BookAction;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by monkey_d_asce on 16-5-19.
 * 官方文档：https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/6.4/html/Development_Guide/sect-JAX-WS_Web_Service_Clients.html
 * js访问库：https://github.com/doedje/jquery.soap
 */

@WebService(
        name = "BookSOAP"
        // targetNamespace = "service"
        //  serviceName = "BookActionService"
)
@SOAPBinding(style = SOAPBinding.Style.RPC)

public class BookSOAP
{


    @EJB
    private BookAction bookAction;
    /*
     WebMethod里面有时候还要action属性要命名成 namespace#operationName，调了半天！其实不是很懂为什么，比如下面，后来用了js的soap之后就不用了
     action名字不能少，不然他他他就找不到action
      */

    @WebMethod(operationName = "table", action = "table")
    public String table()
    {
        return bookAction.table();
    }

    @WebMethod(operationName = "detail", action = "detail")
    public String detail(@WebParam(name = "bookId") int bookId)
    {
        return bookAction.detail(bookId);
    }
}
