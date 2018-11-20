package com.wf.dubbo.services;

import com.wf.framework.mybatis.AutoMapper;
import com.wf.framework.service.impl.SuperServiceImpl;

public class BaseServiceImpl<M extends AutoMapper<T>, T> extends SuperServiceImpl<M, T> {

}
