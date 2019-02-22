package com.zhita.controller.daichao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.service.blacklist.BlacklistService;
import com.zhita.util.RedisClientUtil;

@Controller
@RequestMapping("/blacklist")
public class BlacklistController {
	
	@Autowired
	BlacklistService blacklistService;

	
	/**
	 * 
	 * @param userId 用户id
	 * @param name   姓名
	 * @param phone  手机号码
	 * @param code   短信验证码
	 * @param idCard 身份证号
	 * * @param idCard 公司名称
	 * @return
	 */
	@RequestMapping("/setblacklist")
	@ResponseBody
	@Transactional
	public Map<String, Object> setblacklist(int userId, String name, String idCard, String phone, String code,String company) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(name) || StringUtils.isEmpty(idCard)
				|| StringUtils.isEmpty(phone) || StringUtils.isEmpty(code) || StringUtils.isEmpty(company)) {
			map.put("msg", "userId,name,idCard,phone,code或公司名称不能为空");
			map.put("SCode", "401");
			
			return map;
		} else {
			RedisClientUtil redisClientUtil = new RedisClientUtil();
			String key = phone + "Key";
			String redisCode = redisClientUtil.get(key);
			if (redisCode == null) {
				map.put("msg", "验证码已过期，请重新发送");
				map.put("SCode", "402");
				return map;
			}
			if(redisCode.equals(code)) {
				redisClientUtil.delkey(key);//验证码正确就从redis里删除这个key
				String creationTime = System.currentTimeMillis()+"";
				int number = blacklistService.setblacklist(userId,name,idCard,phone,creationTime,company);
				if (number == 1) {		
					map.put("msg","数据插入成功");
					map.put("SCode", "200");
				} else {
					map.put("msg", "数据插入失败");
					map.put("SCode", "405");
				}
			}else {
				map.put("msg","验证码输入错误");
				map.put("SCode", "403");
			}	
		}
		return map;
	}
}