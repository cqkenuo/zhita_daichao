package com.zhita.controller.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.ManageLogin;
import com.zhita.service.login.IntLoginService;
import com.zhita.util.PageUtil;
import com.zhita.util.RedisClientUtil;
import com.zhita.util.SMSUtil;



@Controller
@RequestMapping("/admin_login")
public class LoginController {
	@Autowired
	IntLoginService loginService;
	
	//发送验证码
	@RequestMapping("/sendSMS")
	@ResponseBody
	public Map<String, String> sendSMS(String phone){
		Map<String, String> map = new HashMap<>();
		SMSUtil smsUtil = new SMSUtil();
		String state = smsUtil.sendSMS(phone, "json");
        map.put("msg",state);		
		return map;	
	}

	//通过验证码登录
	/**
	 * 
	 * @param userName 用户名
	 * @param code 验证码
	 * @param phone 手机号
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> login(String userName,String code,String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(code) || StringUtils.isEmpty(phone)) {
			map.put("msg", "userName,code或phone不能为空");
			return map;
		} else {
			RedisClientUtil redisClientUtil = new RedisClientUtil();
			String key = phone+"Key";
			String redisCode = redisClientUtil.get(key);
			if(redisCode==null) {
				map.put("msg", "验证码已过期，请重新发送");
				return map;
			}
			if(redisCode.equals(code)) {
				ManageLogin manageLogin = loginService.findFormatByLoginName(userName); // 判断该用户是否存在
				if (manageLogin == null) {
					map.put("msg", "用户名不存在");
					return map;
				}else {
					String loginStatus = "1";
					String registrationTime = System.currentTimeMillis()+"";  //获取当前时间戳
					int number = loginService.updateAdminLoginStatus(loginStatus,phone,userName,registrationTime);
					if (number == 1) {	
						int id = loginService.getAdminId(phone,userName); //获取该用户的id
						map.put("msg", "用户登录成功，登录状态修改成功");
						map.put("loginStatus", loginStatus);
						map.put("userId", id);
					} else {
						map.put("msg", "用户登录失败，登录状态修改失败");
					}
				}

			}else {
				map.put("msg", "验证码输入错误");
			}

		}

		return map;

	}
	
	// 退出登录
	@RequestMapping("/logOut")
	@ResponseBody
	public Map<String, String> logOut(int userId) {
		Map<String, String> map = new HashMap<>();
		if (StringUtils.isEmpty(userId)) {
			map.put("msg", "userId不能为空");
			return map;
			}else {
				String loginStatus = "0";
				int number = loginService.updateAdminLogOutStatus(loginStatus,userId);
				if (number == 1) {														
					map.put("msg", "用户退出成功，登录状态修改成功");
					map.put("loginStatus", loginStatus);
				} else {
					map.put("msg", "用户退出失败，登录状态修改失败");
				}
			}

		return map;

	}
	//查询出所有用户信息——含用户信息  用户的角色  以及权限   含分页
    @ResponseBody
    @RequestMapping("/queryAllManageLogin")
    public Map<String,Object> queryAllManageLogin(Integer page){
    /*	int totalCount=loginService.pageCountManageLogin();//查询出管理登陆用户表一共有多少条数据
    	PageUtil pageUtil=new PageUtil(page,1,totalCount);
    	if(page<1) {
    		page=1;
    	}
    	else if(page>pageUtil.getTotalPageCount()) {
    		if(totalCount==0) {
    			page=pageUtil.getTotalPageCount()+1;
    		}else {
    			page=pageUtil.getTotalPageCount();
    		}
    	}
    	int pages=(page-1)*pageUtil.getPageSize();
    	pageUtil.setPage(pages);*/
    	List<ManageLogin> list=loginService.queryManageLogin();
    	for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getUsername());
		}
    	
    	HashMap<String,Object> map=new HashMap<>();
    	map.put("listManagelogin",list);
    	//map.put("pageutil", pageUtil);
    	return map;
    }

}
