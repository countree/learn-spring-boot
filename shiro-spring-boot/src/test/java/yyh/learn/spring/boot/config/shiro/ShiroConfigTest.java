package yyh.learn.spring.boot.config.shiro;


import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.Test;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

public class ShiroConfigTest {
    @Test
    public void securityManager() throws Exception {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        CustomAuthRealm realm = new CustomAuthRealm();
    }


    @Test
    public void testFastJson() {
        SimpleSession simpleSession = new SimpleSession();

        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        byte[] bytes = jdkSerializationRedisSerializer.serialize(simpleSession);

        System.out.println(JSONObject.toJSONString(simpleSession));

    }

}
