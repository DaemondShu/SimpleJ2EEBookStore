package test;

import javax.ejb.EJB;

/**
 * Created by monkey_d_asce on 16-3-28.
 */
public class Middle
{
    @EJB(name = "Hello")
    private Hello hello;  //放这没用

    public String ret()
    {
        return hello.sayHi();
    }
}
