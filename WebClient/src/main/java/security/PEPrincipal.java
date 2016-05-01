package security;

import java.io.Serializable;
import java.security.Principal;

/**
 * Created by monkey_d_asce on 16-3-27.
 */
public class PEPrincipal implements Principal, Serializable
{
    private static final long serialVersionUID = 1L;
    private String name;

    public PEPrincipal(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Principal))
            return false;
        Principal other = (Principal) obj;
        if (name == null)
        {
            if (other.getName() != null)
                return false;
        } else if (!name.equals(other.getName()))
            return false;
        return true;
    }

    public String getName()
    {
        return name;
    }

    /**
     * 这儿的问题,让我浪费了足足 3 天的时间,因为 JBoss 总是报不是错误的错误信息。
     * 这个方法的实现**必须**是 name.hashCode(), 否则在 JBoss 中运行不正常,可能是 JBoss
     * 中使用 HashSet 来存储.
     */
    @Override
    public int hashCode()
    {
        assert name != null;
        return name.hashCode();
    }

    public String toString()
    {
        return name;
    }

}