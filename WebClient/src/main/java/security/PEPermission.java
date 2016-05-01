package security;

import java.security.BasicPermission;

/**
 * Created by monkey_d_asce on 16-3-30.
 * 在jboss下拿不到securitymanager，就没法用了
 */
public class PEPermission extends BasicPermission
{

    public PEPermission(String s)
    {
        super(s);
    }


}
