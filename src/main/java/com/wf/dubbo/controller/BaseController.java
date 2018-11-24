package com.wf.dubbo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wf.dubbo.support.ResponseData;
import com.wf.dubbo.support.ResponseEnum;

public class BaseController {
    public Logger LOG= LoggerFactory.getLogger(BaseController.class);


    protected String redirectTo(String url) {
        StringBuffer rto = new StringBuffer("redirect:");
        rto.append(url);
        return rto.toString();
    }

    protected ResponseData setEnumResult(ResponseEnum anEnum,Object data){
        ResponseData res =new ResponseData();
        res.setStatus(anEnum.getCode());
        res.setData(data);
        res.setMessage(anEnum.getMsg());
        return res;
    }
    
    ThreadLocal<String> uidLocal = new ThreadLocal<>();
    
    public String getUid() {
    	return uidLocal.get();
    }
    
    public void setUid(String uid) {
    	uidLocal.set(uid);
    }
}
