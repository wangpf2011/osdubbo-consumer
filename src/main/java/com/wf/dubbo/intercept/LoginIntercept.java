package com.wf.dubbo.intercept;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.wf.dubbo.CheckAuthRequest;
import com.wf.dubbo.CheckAuthResponse;
import com.wf.dubbo.UserService;
import com.wf.dubbo.controller.BaseController;
import com.wf.dubbo.support.Anonymous;
import com.wf.dubbo.utils.CookieUtils;


public class LoginIntercept extends HandlerInterceptorAdapter {
	
	private final String ACCESS_TOKEN = "access_token";
	
	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Object action = handlerMethod.getBean();
		if(action instanceof BaseController) {
			throw new Exception("异常");
		}
		
		if(isAnonymous(handlerMethod)) {
			return true;
		}
		String accessToken = CookieUtils.getCookie(request, ACCESS_TOKEN);
		if(StringUtils.isEmpty(accessToken)) {//没有登录
			if(CookieUtils.isAjax(request)) {
				JSONObject object = new JSONObject();
				object.put("code", "-1");
				object.put("msg", "没有登录");
				response.getWriter().write(object.toJSONString());
				return false;
			}
			response.sendRedirect("/osdubbo-consumer/login.shtml");
			return false;
		}
		
		CheckAuthRequest checkAuthRequest = new CheckAuthRequest();
		checkAuthRequest.setToken(accessToken);
		CheckAuthResponse checkAuthResponse = userService.checkAuth(checkAuthRequest);
		if("000000".equals(checkAuthResponse.getCode())) {
			((BaseController)action).setUid(checkAuthResponse.getUid());
		}
		
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean isAnonymous(HandlerMethod handlerMethod) {
		Object action = handlerMethod.getBean();
		Class clazz = action.getClass();
		if(clazz.getAnnotation(Anonymous.class) != null) {
			return true;
		}
		
		Method method = handlerMethod.getMethod();
		return method.getAnnotation(Anonymous.class) != null;
	}
}
