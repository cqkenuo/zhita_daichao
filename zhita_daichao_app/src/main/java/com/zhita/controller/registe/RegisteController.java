package com.zhita.controller.registe;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.LoanCustomvalue;
import com.zhita.model.manage.LoansBusinesses;
import com.zhita.service.commodityfootprint.CommodityFootprintService;
import com.zhita.service.registe.IntRegisteCopyService;
import com.zhita.service.registe.IntRegisteCustomvalueService;
import com.zhita.service.registe.IntRegisteService;
import com.zhita.service.sourcedadson.SourceDadSonService;
import com.zhita.util.PageUtil;


@Controller
@RequestMapping(value="/registe")
public class RegisteController {
	
	@Autowired
	IntRegisteService intRegisteService;
	
	@Autowired
	CommodityFootprintService cFootprintService;
	
	@Autowired
	SourceDadSonService sourceDadSonService;
	
	@Autowired
	IntRegisteCopyService intRegisteCopyService;
	
	@Autowired
	IntRegisteCustomvalueService intRegisteCustomvalueService;
	
	//小程序---查询所有贷款商家信息,含分页
    @ResponseBody
    @RequestMapping("/queryAll")
    @Transactional
    public Map<String,Object> queryAll(Integer page,String company,String oneSourceName,String twoSourceName){        	
    	HashMap<String,Object> map=new HashMap<>();
    	PageUtil pageUtil=null;
    	int totalCount = 0;
    	int pages = 0;
    	List<LoansBusinesses> list = null;
    	if(oneSourceName==null&&twoSourceName==null) {
    		totalCount=intRegisteService.pageCount2(company);//该方法是查询贷款商家总条数
        	pageUtil=new PageUtil(page,10,totalCount);
    		int totalPageCount = pageUtil.getTotalPageCount();
    		
    		int newTotalCount = totalCount*6;
    		pageUtil=new PageUtil(page,10,newTotalCount);
    		int newTotalPageCount = pageUtil.getTotalPageCount();
    		
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
        	pages=(page-1)*pageUtil.getPageSize();
        	pageUtil.setPage(pages); 	
        	if(totalPageCount==newTotalPageCount) {
            	list=intRegisteService.queryAllAdmainpro(pageUtil.getPage(),pageUtil.getPageSize(),company);	
        	}else {
				if(page<=totalPageCount) {
					list=intRegisteService.queryAllAdmainpro(pageUtil.getPage(),pageUtil.getPageSize(),company);
				}
				if (page>totalPageCount&&page<=newTotalPageCount) {
					list = intRegisteService.randQueryAllAdmainpro(company);
				}
			}

        	pageUtil=new PageUtil(page,10,newTotalCount);

    		
        	if(list!=null) {
            for (LoansBusinesses loansBusinesses : list) {
    	        String businessName = loansBusinesses.getBusinessname();
    	        int fakeApplications = loansBusinesses.getApplications(); //假的申请人数
    	        int applications = (int)cFootprintService.getApplications(businessName,company)+fakeApplications;//获取申请人数	  
    	        loansBusinesses.setApplications(applications);
    	        String loanlimitbig = loansBusinesses.getLoanlimitbig().setScale(0)+"";
    	        String loanlimitsmall = loansBusinesses.getLoanlimitsmall().setScale(0)+"";
    	        String loanlimit = loanlimitsmall+"~"+loanlimitbig;
    	        loansBusinesses.setLoanlimit(loanlimit);          
    		}
        	}
        	map.put("listLoansBusin",list);
        	map.put("pageutil", pageUtil);	
    	}else {
	
    	String  tableType = sourceDadSonService.getTableType(oneSourceName,twoSourceName,company);
    	if(tableType.equals("1")) {        	
        	totalCount=intRegisteService.pageCount2(company);//该方法是查询贷款商家总条数
        	pageUtil=new PageUtil(page,10,totalCount);
    		int totalPageCount = pageUtil.getTotalPageCount();
    		
    		int newTotalCount = totalCount*6;
    		pageUtil=new PageUtil(page,10,newTotalCount);
    		int newTotalPageCount = pageUtil.getTotalPageCount();
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
        	pages=(page-1)*pageUtil.getPageSize();
        	pageUtil.setPage(pages); 
        	if(totalPageCount==newTotalPageCount) {
            	list=intRegisteService.queryAllAdmainpro(pageUtil.getPage(),pageUtil.getPageSize(),company);	
        	}else {
				if(page<=totalPageCount) {
					list=intRegisteService.queryAllAdmainpro(pageUtil.getPage(),pageUtil.getPageSize(),company);
				}
				if (page>totalPageCount&&page<=newTotalPageCount) {
					list = intRegisteService.randQueryAllAdmainpro(company);
				}
			}
        	
        	pageUtil=new PageUtil(page,10,newTotalCount);
        	if(list!=null) {
            for (LoansBusinesses loansBusinesses : list) {
    	        String businessName = loansBusinesses.getBusinessname();
    	        int fakeApplications = loansBusinesses.getApplications(); //假的申请人数
    	        int applications = (int)cFootprintService.getApplications(businessName,company)+fakeApplications;//获取申请人数	  
    	        loansBusinesses.setApplications(applications);
    	        String loanlimitbig = loansBusinesses.getLoanlimitbig().setScale(0)+"";
    	        String loanlimitsmall = loansBusinesses.getLoanlimitsmall().setScale(0)+"";
    	        String loanlimit = loanlimitsmall+"~"+loanlimitbig;
    	        loansBusinesses.setLoanlimit(loanlimit);          
    		}
        	}
        	map.put("listLoansBusin",list);
        	map.put("pageutil", pageUtil);	
    	}
            
        	if(tableType.equals("2")) {
            	totalCount=intRegisteCopyService.pageCountAppCopy(company,oneSourceName,twoSourceName);//该方法是查询贷款商家总条数
            	pageUtil=new PageUtil(page,10,totalCount);
            	pageUtil=new PageUtil(page,10,totalCount);
        		int totalPageCount = pageUtil.getTotalPageCount();
        		
        		int newTotalCount = totalCount*6;
        		pageUtil=new PageUtil(page,10,newTotalCount);
        		int newTotalPageCount = pageUtil.getTotalPageCount();
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
            	pages=(page-1)*pageUtil.getPageSize();
            	pageUtil.setPage(pages); 	
            	list=intRegisteCopyService.queryAllAdmainproAppCopy(pageUtil.getPage(),pageUtil.getPageSize(),company,oneSourceName,twoSourceName);
            	if(totalPageCount==newTotalPageCount) {
                	list=intRegisteService.queryAllAdmainpro(pageUtil.getPage(),pageUtil.getPageSize(),company);	
            	}else {
    				if(page<=totalPageCount) {
    					list=intRegisteService.queryAllAdmainpro(pageUtil.getPage(),pageUtil.getPageSize(),company);
    				}
    				if (page>totalPageCount&&page<=newTotalPageCount) {
    					list = intRegisteService.randQueryAllAdmainpro(company);
    				}
    			}
            	pageUtil=new PageUtil(page,10,newTotalCount);
            	if(list!=null) {
                    for (LoansBusinesses loansBusinesses : list) {
            	        int fakeApplications = loansBusinesses.getApplications(); //假的申请人数
            	        int applications = fakeApplications;
            	        loansBusinesses.setApplications(applications);
            	        String loanlimitbig = loansBusinesses.getLoanlimitbig().setScale(0)+"";
            	        String loanlimitsmall = loansBusinesses.getLoanlimitsmall().setScale(0)+"";
            	        String loanlimit = loanlimitsmall+"~"+loanlimitbig;
            	        loansBusinesses.setLoanlimit(loanlimit);          
            		}
            	} 

        	map.put("listLoansBusin",list);
        	map.put("pageutil", pageUtil);	
    	}
    	}


    	
    	return map;
    }
    
    /*@ResponseBody
    @RequestMapping("/dynamicData")
    @Transactional
    public HashMap<String,Object> queryAll(String oneSourceName,String twoSourceName){
    	HashMap<String,Object> map=new HashMap<>();
    	map.put("left", "可贷额度");
    	map.put("centerTop", "%");
    	map.put("centerBottom", "日利率");
    	map.put("button", "立即放款");
    	map.put("right", "人已放款");
		return map;    
    	
    }*/

    @ResponseBody
    @RequestMapping("/dynamicData")
    @Transactional
    public HashMap<String,Object> queryAll(String oneSourceName,String twoSourceName){
    	List<LoanCustomvalue> list=intRegisteCustomvalueService.queryAll();
    	
    	HashMap<String,Object> map=new HashMap<>();
    	for (int i = 0; i < list.size(); i++) {
    		if(i==0){
    			map.put("left", list.get(0).getFields());
    		}
    		if(i==1){
    			map.put("centerTop", list.get(1).getFields());
    		}
        	if(i==2){
        		map.put("centerBottom", list.get(2).getFields());
        	}
        	if(i==3){
        		map.put("button", list.get(3).getFields());
        	}
        	if(i==4){
        		map.put("right", list.get(4).getFields());
        	}
		}
    	return map;
    }
}