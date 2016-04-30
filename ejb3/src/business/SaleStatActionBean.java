package business;

import data.DataManager;
import net.sf.json.JSONArray;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;


/**
 * Created by monkey_d_asce on 16-3-31.
 */
@Stateless(name = "SaleStatActionEJB")
public class SaleStatActionBean implements SaleStatAction
{
    @EJB(name = "DataManager")
    DataManager dataManager;



    public SaleStatActionBean()
    {
    }

    @Override
    public String CountByUser()
    {

        String result = "[]";
        try
        {
            // DataManager dataManager = new DataManager(entityManager);
            List<Object[]> rs = dataManager.total_userquery();

            return JSONArray.fromObject(rs).toString();

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
            //DataManager dataManager = new DataManager(entityManager);
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
            //DataManager dataManager = new DataManager(entityManager);
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
    }
}
