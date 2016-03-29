package test;

import javax.ejb.Remote;

/**
 * Created by monkey_d_asce on 16-3-26.
 */
@Remote
public interface Hello
{
    String sayHello();

    String sayHi();
}
