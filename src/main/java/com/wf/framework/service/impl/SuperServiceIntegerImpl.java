package com.wf.framework.service.impl;

import com.wf.framework.mybatis.AutoMapperInteger;

/**
 * <p>
 * 主键 Long 类型 IService 实现类（ 泛型：M 是 mapper 对象， T 是实体 ）
 * </p>
 */
public class SuperServiceIntegerImpl<M extends AutoMapperInteger<T>, T> extends ServiceImpl<M, T, Integer> {

}
