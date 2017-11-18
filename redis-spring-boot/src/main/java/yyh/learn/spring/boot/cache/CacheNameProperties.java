package yyh.learn.spring.boot.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CacheNameProperties {

    @Value("${redis.cache.names:defaultCacheName}")
    private String cacheNames;

    public List<CacheNameExpireBean> getCacheNames() {
        String[] names = cacheNames.split(",");
        List<CacheNameExpireBean> cacheNameExpireBeanList = new ArrayList<CacheNameExpireBean>();
        for (String name : names) {
            if (StringUtils.isNoneBlank(name)) {
                String[] nameExpire = name.split(":");
                CacheNameExpireBean cacheNameExpireBean = new CacheNameExpireBean();
                if (nameExpire.length == 1)
                    cacheNameExpireBean.setCacheName(nameExpire[0]);
                else if (nameExpire.length == 2) {
                    cacheNameExpireBean.setCacheName(nameExpire[0]);
                    try {
                        cacheNameExpireBean.setExpireTime(Long.valueOf(nameExpire[1]));
                    } catch (Exception e) {
                        throw new IllegalArgumentException("expireTime 类型为：[long] 单位/秒");
                    }
                } else {
                    throw new IllegalArgumentException("cacheName 配置格式错误,格式为[cacheName:expireTime] 单位/秒");
                }
                cacheNameExpireBeanList.add(cacheNameExpireBean);
            }
        }
        return cacheNameExpireBeanList;
    }

    public void setCacheNames(String cacheNames) {
        this.cacheNames = cacheNames;
    }
}
