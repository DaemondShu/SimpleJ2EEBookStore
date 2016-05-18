package cache;

import javax.ejb.Local;

/**
 * Created by monkey_d_asce on 16-5-18.
 */
@Local
public interface CacheManager
{

    void set(String key, String value);

    String get(String key);

    void clear();
}
