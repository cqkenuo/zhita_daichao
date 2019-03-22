package com.zhita.model.manage;

public class UnitPrice {
    private Integer id;

    private String account;

    private Integer price;

    private String accounttype;

    private String firmtype;

    private String firm;
    
    private String deleted;
    
    private String modifyTime;
    
    private String company;

    private Integer sourceId;
    
    private Integer businessesId;
    

    public UnitPrice(Integer id, String account, Integer price, String accounttype, String firmtype, String firm,String deleted,String modifyTime,String company,Integer sourceId,Integer businessesId) {
        this.id = id;
        this.account = account;
        this.price = price;
        this.accounttype = accounttype;
        this.firmtype = firmtype;
        this.firm = firm;
        this.deleted = deleted;
        this.modifyTime = modifyTime;
        this.company = company;
        this.sourceId = sourceId;
        this.businessesId = businessesId;
    }

    public UnitPrice() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype == null ? null : accounttype.trim();
    }

    public String getFirmtype() {
        return firmtype;
    }

    public void setFirmtype(String firmtype) {
        this.firmtype = firmtype == null ? null : firmtype.trim();
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm == null ? null : firm.trim();
    }
    
    public String getDeleted() {
        return deleted;
    }
    
    public void setDeleted(String deleted) {
        this.deleted = deleted == null ? null : deleted.trim();
    }
    
    public String getModifyTime() {
        return modifyTime;
    }
    
    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime == null ? null : modifyTime.trim();
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }
    
    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }
    
    public Integer getBusinessesId() {
        return businessesId;
    }

    public void setBusinessesId(Integer businessesId) {
        this.businessesId = businessesId;
    }
    
    
}