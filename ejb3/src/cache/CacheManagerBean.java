package cache;

import redis.clients.jedis.Jedis;

import javax.ejb.Stateless;

/**
 * Created by monkey_d_asce on 16-5-18.
 * 把jedis再包装一层的理由跟database一样，如果以后改用缓存方式，就只需要改这个类，不用修改业务代码。
 * java使用jedis参考：http://blog.csdn.net/songylwq/article/details/26077605
 */
@Stateless(name = "CacheManagerEJB")
public class CacheManagerBean implements CacheManager
{
    Jedis jedis = null;

    public CacheManagerBean()
    {
        try
        {
            jedis = new Jedis("localhost");

            if (jedis != null)
            {
                System.out.println("Connection to redis successfully");
            }
        } catch (Exception e)
        {
            jedis = null;
            System.out.println("failed to connect redis");
        }
    }

    public void set(String key, String value)
    {
        System.out.println("set:" + key);
        jedis.set(key, value);
    }

    public String get(String key)
    {
        String temp = jedis.get(key);

        if (temp == null || temp.isEmpty())
            return null;
        else
        {
            System.out.println("get:" + key);
            return temp;
        }
    }

    public void clear()
    {
        jedis.flushDB();
    }
}
