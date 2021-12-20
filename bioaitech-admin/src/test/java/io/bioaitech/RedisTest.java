package io.bioaitech;

import io.bioaitech.modules.sys.entity.SysUserEntity;
import io.bioaitech.modules.sys.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
	private SysUserService sysUserService;

    @Test
    public void contextLoads() throws Exception {
       /* SysUserEntity user = new SysUserEntity();
        user.setEmail("123456@qq.com");
        redisUtils.set("user", user);

        System.out.println(ToStringBuilder.reflectionToString(redisUtils.get("user", SysUserEntity.class)));*/

	    SysUserEntity userEntity = new SysUserEntity();
	    //用户名 sales 初始密码sales123456
	    //用户名 yanan.liu 初始密码liuyanan
	    userEntity.setUsername("yanan.liu");
	    userEntity.setPassword("liuyanan");
	    userEntity.setMobile("");
	    userEntity.setEmail("yanan.liu@myhealthgene.com");
	    userEntity.setDeptId(Long.valueOf(1));
	    userEntity.setStatus(1);

	    sysUserService.saveUser(userEntity);
    }

}
