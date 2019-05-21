package com.zhita.service.tongji;

import java.util.List;

import com.zhita.model.manage.SourceTongji;
import com.zhita.model.manage.TongjiSorce;

public interface IntTongjiService {
    //后台管理---通过渠道名称  查询出统计表所有的商家名称
    public List<String> quereyAllLoansNameBySourceName(String sourceName);
    
    //后台管理---查询出统计表数据总数量
    public int pageCount(String source,String startTime,String endTime);
    
    //后台管理---通过渠道名称   查询出统计表数据总数量
    public int pageCountBySourceName(String company,String sourceName);
    
    //后台管理---查询渠道统计所有信息，含分页
    public List<SourceTongji> queryAllPage(String source,String startTime,String endTime);
    
    //后台管理---查询渠道统计所有信息，不含分页
    public List<SourceTongji> queryAllPage1(String company);
    
    //后台管理---通过渠道名称   查询统计表所有信息，含分页
    public List<SourceTongji> queryAllPageBySourceName(String company,String sourceName,Integer page,Integer pagesize);
    
    //后台管理---通过渠道名称   查询统计表所有信息，不含分页
    public List<SourceTongji> queryAllPageBySourceName1(String company,String sourceName);
    
    //后台管理---查询统计pv
    public Integer queryPV(String company,String sourceName);
    
    //后台管理---查询统计uv
    public Integer queryUV(String company,String sourceName,String startTime,String endTime);
    
    //后台管理---查询统计uv1
    public Integer queryUV1(String businame,String sourceName,String startTime,String endTime);
    
    //后台管理---查询统计申请数
    public Integer queryApplicationNumber(String company,String sourceName,String startTime,String endTime);
    
    //后台管理---查询当前渠道的折扣率
    public String queryDiscount(String source,String company);
    
    //后台管理---查询当前渠道在user表的所有注册时间
    public List<String> queryTime(String company,String sourceName);
    
    //后台管理---查询在user表的所有注册时间
    public List<String> queryTimeme1(String company);
    
    //后台管理---查询该时间段里    在用户表一共哪些渠道，以及这些渠道的在用户表的注册数量
    List<TongjiSorce> queryAllSourceByUser(String[] company,String StartTime,String EndTime);
}
