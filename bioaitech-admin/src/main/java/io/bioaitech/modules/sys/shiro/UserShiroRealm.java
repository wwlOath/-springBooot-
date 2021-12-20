package io.bioaitech.modules.sys.shiro;

import io.bioaitech.modules.sys.entity.SysUserEntity;
import io.bioaitech.modules.sys.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;


/**
 * 自定义权限匹配和账号密码匹配
 *
 * @attention
 */
public class UserShiroRealm extends AuthorizingRealm {


	@Autowired
	@Lazy
	private SysUserService userService;

	/**
	 * 授权(验证权限时调用)
	 * @Attention
	 * @Param:
	 * @Return:
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		SysUserEntity sysUserEntity = (SysUserEntity) principals.getPrimaryPrincipal();
		//这里可以进行授权和处理
		return authorizationInfo;
	}

	/**
	 * 身份认证（密码校验）
	 * @Attention
	 * @Param:
	 * @Return:
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		//获取用户的输入的账号.
		String username = (String) token.getPrincipal();
		//通过username从数据库中查找 User对象，如果找到，没找到.
		//实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		//参数封装
		SysUserEntity userEntity =new SysUserEntity();
		userEntity.setUsername(username);
		SysUserEntity byUsername = userService.getByUsername(username);
		if (byUsername == null){
			return null;
		}

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				byUsername,                                  //用户名
				byUsername.getPassword(),                    //密码
				ByteSource.Util.bytes(byUsername.getSalt()), //salt=username+salt
				getName()
		);
		return authenticationInfo;
	}

}
