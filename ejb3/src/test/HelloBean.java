package test;

import data.EntityManagerAdv;

import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;

/**
 * Created by monkey_d_asce on 16-3-26.
 */

@Stateless(name = "HelloEJB")
//@LocalBinding(jndiBinding="myFirst/Local")
//@PermitAll
public class HelloBean implements Hello
{


    @PersistenceContext(unitName = "JPADB")
    private EntityManagerAdv entityManager;

    //private DataManager dataManager = new DataManager(entityManager);

    //private EntityManager entityManger;


    //@PermitAll
    @Override
    public String sayHello()
    {
        return "hello";
        //DataManager dataManager = new DataManager(entityManager);
        // return  ((Integer)dataManager.user_queryid("user10")).toString() + dataManager.user_querypwd("user10");
/*
        Query query = entityManager.createQuery("SELECT password FROM user where name='user10' ");

        //return "Hello";
        return (String) query.getSingleResult();*/

    }

    //@RolesAllowed("admin")
    @Override
    public String sayHi()
    {


        return "hi";
    }


}
