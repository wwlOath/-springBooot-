package io.bioaitech.modules.sys.controller;

import com.google.code.kaptcha.Producer;
import io.bioaitech.common.utils.R;
import io.bioaitech.common.utils.RedisUtils;
import io.bioaitech.modules.sys.entity.SysUserEntity;
import io.bioaitech.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class SysLoginController {
	@Autowired
	private Producer producer;
	@Autowired
	private RedisUtils redisUtils;

	@ResponseBody
	@RequestMapping("/bioaitech/captcha")
	public R captcha(){
		//生成文字验证码
		String text = producer.createText();
		//生成图片验证码
		BufferedImage image = producer.createImage(text);
		String id = "biaoaitech-captcha-"+ UUID.randomUUID();
		redisUtils.set(id,text,120);
		//保存到shiro session
		BASE64Encoder encoder = new BASE64Encoder();
		//io流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			//写入流中
			ImageIO.write(image, "png", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//转换成字节
		byte[] bytes = baos.toByteArray();
		//转换成base64串
		String pngBase64 =  encoder.encodeBuffer(bytes).trim();
		pngBase64 = pngBase64.replaceAll("\n", "").replaceAll("\r", "");
		Map<Object, Object> map = new HashMap<>(2);
		map.put("base64","data:image/png;base64,"+ pngBase64);
		map.put("id",id);
		return R.ok().put("data",map);
	}

	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public R login(String username, String password, String captcha,String captchaId) {
		String kaptcha = redisUtils.get(captchaId);
		if(!captcha.equalsIgnoreCase(kaptcha)){
			return R.error("验证码不正确");
		}
		try{
			Subject subject = ShiroUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
		}catch (UnknownAccountException e) {
			return R.error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
			return R.error("账号或密码不正确");
		}catch (LockedAccountException e) {
			return R.error("账号已被锁定,请联系管理员");
		}catch (AuthenticationException e) {
			return R.error("账户验证失败");
		}

		return R.ok().put("Authorization", ShiroUtils.getSession().getId().toString());
	}

	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/getMyInfo")
	public R info(){
		SysUserEntity userEntity = ShiroUtils.getUserEntity();
		return R.ok().put("user", userEntity);
	}

	/**
	 * 退出
	 * @return R
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public R logout() {
		ShiroUtils.logout();
		return R.ok();
	}

	/**
	 * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
	 * @return
	 */
	@RequestMapping(value = "/unauth")
	@ResponseBody
	public Object unauth() {
		return R.error(5000, "未登录");
	}

}
