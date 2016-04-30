package business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Created by monkey_d_asce on 16-3-31.
 */
@Stateless(name = "SaleStatActionEJB")
public class SaleStatActionBean implements SaleStatAction
{
    @PersistenceContext(unitName = "JPADB")
    private EntityManager entityManager;
/*
    public SaleStatActionBean()
    {
    }

    @Override
    public String CountByUser()
    {

        String result = "";
        try
        {
            DataManager dataManager = new DataManager(entityManager);
            List<Object[]> rs = dataManager.total_userquery();

            if (rs != null)
            {
                for (Object[] obj : rs)
                {
                    String id = obj[0].toString();
                    if (Integer.parseInt(id) == 1) continue;
                    result += "<tr>";
                    result += "<td>" + id + "</td>";

                    result += "<td>" + obj[1].toString() + "</td>";
                    if (obj[2] == null || obj[2].toString().equals(""))
                        result += "<td>" + 0 + "</td>";
                    else
                        result += "<td>" + obj[2].toString() + "</td>";

                    result += "</tr>";
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String CountByType()
    {
        String result = "";
        try
        {
            DataManager dataManager = new DataManager(entityManager);
            List<Object[]> rs = dataManager.total_typequery();

            if (rs != null)
            {
                for (Object[] obj : rs)
                {
                    result += obj[0].toString() + ";" + obj[1].toString() + ";";
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String CountByDate()
    {
        String result = "";
        try
        {
            DataManager dataManager = new DataManager(entityManager);
            List<Object[]> rs = dataManager.total_datequery();
            if (rs != null)
            {
                for (Object[] obj : rs)
                {
                    result += obj[0].toString() + ";" + obj[1].toString() + ";";
                }

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }*/
}
