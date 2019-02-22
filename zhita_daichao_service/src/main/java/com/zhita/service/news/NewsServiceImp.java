package com.zhita.service.news;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhita.dao.manage.StrategyMapper;
import com.zhita.model.manage.Strategy;


@Service(value="newsServiceImp")
public class NewsServiceImp implements IntNewsService{
	@Resource(name="strategyMapper")
	private StrategyMapper strategyMapper;

	public StrategyMapper getStrategyMapper() {
		return strategyMapper;
	}
	public void setStrategyMapper(StrategyMapper strategyMapper) {
		this.strategyMapper = strategyMapper;
	}
	
    //后台管理---查询攻略表所有信息，含分页
    public List<Strategy> queryAllNews(String company,Integer page,Integer pagesize){
    	List<Strategy> list=strategyMapper.queryAllNews(company,page,pagesize);
    	return list;
    }
    //后台管理---查询攻略表所有信息，不含分页
    public List<Strategy> queryAllNews1(String company){
    	List<Strategy> list=strategyMapper.queryAllNews1(company);
    	return list;
    }
    //后台管理---查询攻略表总数量
    public int pageCount(String company) {
    	int count=strategyMapper.pageCount(company);
    	return count;
    }
    //后台管理---根据标题字段模糊查询攻略表总数量
    public int pageCountByLike(String title,String company) {
    	int count=strategyMapper.pageCountByLike(title,company);
    	return count;
    }
    
    //后台管理---根据标题字段模糊查询，攻略表信息，含分页
    public List<Strategy> queryNewsByLike(String title,String company,Integer page,Integer pagesize){
    	List<Strategy> list=strategyMapper.queryNewsByLike(title,company,page,pagesize);
    	return list;
    }
    //后台管理---根据标题字段模糊查询，攻略表信息，不含分页
    public List<Strategy> queryNewsByLike1(String title,String company){
    	List<Strategy> list=strategyMapper.queryNewsByLike1(title,company);
    	return list;
    }
    //后台管理---添加攻略信息
    public int addAll(Strategy strategy) {
    	int selnum=strategyMapper.addAll(strategy);
    	return selnum;
    }
    //后台管理---通过主键id查询出攻略信息
    public Strategy selectByPrimaryKey(Integer id) {
    	Strategy strategy=strategyMapper.selectByPrimaryKey(id);
    	return strategy;
    }
    
    //后台管理---通过传过来的攻略对象，对当前对象进行修改保存
    public int updateStrategy(Strategy strategy) {
    	int num=strategyMapper.updateStrategy(strategy);
    	return num;
    }
    //后台管理---通过主键id修改其当前对象的假删除状态
    public int upaFalseDelById(Integer id) {
    	int num=strategyMapper.upaFalseDelById(id);
    	return num;
    }
    //后台管理---修改攻略状态为开启
    public int upaStateOpen(Integer id) {
    	int num=strategyMapper.upaStateOpen(id);
    	return num;
    }
    //后台管理---修改攻略状态为关闭
    public int upaStateClose(Integer id) {
    	int num=strategyMapper.upaStateClose(id);
    	return num;
    }
	@Override
	public String getCover(int id) {
		String cover = strategyMapper.getCover(id);
		return cover;
	}
}
