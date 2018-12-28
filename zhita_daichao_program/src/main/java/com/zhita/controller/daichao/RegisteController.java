package com.zhita.controller.daichao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.LoansBusinesses;
import com.zhita.service.registe.IntRegisteService;
import com.zhita.service.type.IntTypeService;
import com.zhita.util.PageUtil;


@Controller
@RequestMapping(value="/registe")
public class RegisteController {
	@Resource(name="registeServiceImp")
	private IntRegisteService intRegisteService;

	public IntRegisteService getIntRegisteService() {
		return intRegisteService;
	}

	public void setIntRegisteService(IntRegisteService intRegisteService) {
		this.intRegisteService = intRegisteService;
	}
	//小程序---查询所有贷款商家信息,含分页
    @ResponseBody
    @RequestMapping("/queryAll")
    public Map<String,Object> queryAll(Integer page){
    	int totalCount=intRegisteService.pageCount();//该方法是查询贷款商家总条数
    	PageUtil pageUtil=new PageUtil(page, totalCount);
    	if(page==0) {
    		page=1;
    	}
    	int pages=(page-1)*pageUtil.getPageSize();
    	pageUtil=new PageUtil(pages, totalCount);
    	List<LoansBusinesses> list=intRegisteService.queryAllAdmain(pageUtil.getPage());
    	
    	HashMap<String,Object> map=new HashMap<>();
    	map.put("listLoansBusin",list);
    	map.put("pageutil", pageUtil);
    	return map;
    }
}