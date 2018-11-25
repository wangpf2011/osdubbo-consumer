package com.wf.dubbo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wf.dubbo.UserRequest;
import com.wf.dubbo.UserResponse;
import com.wf.dubbo.UserService;
import com.wf.dubbo.support.Anonymous;
import com.wf.dubbo.support.ResponseData;
import com.wf.dubbo.support.ResponseEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/index/")
public class IndexController extends BaseController{

    @Autowired
    private UserService userServices;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        /*if(request.getSession().getAttribute("user")==null){
            return "/login";
        }*/
        return "/index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @Anonymous
    public String login() {
        return "/login";
    }
    
    /**   
     * @Description: JWT单点登录Session跨域--登录操作
     *
     * @param loginname
     * @param password
     * @param response
     * @return ResponseEntity
     * @throws：异常描述
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
    @Anonymous
    public ResponseEntity doLogin(HttpServletResponse response, String loginname, String password){
    	UserRequest userRequest = new UserRequest();
    	userRequest.setName(loginname);
    	userRequest.setPassword(password);
        UserResponse userResponse = userServices.login(userRequest);
        if("000000".equals(userResponse.getCode())) {
            response.addHeader("Set-Cookie", "access_token="+userResponse.getToken()+";Path=/;HttpOnly");
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value="/submitLogin",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData submitLogin(HttpServletRequest request,String loginname,String password){
    	UserRequest userRequest = new UserRequest();
    	userRequest.setName(loginname);
    	userRequest.setPassword(password);
        UserResponse response = userServices.login(userRequest);
        if("000000".equals(response.getCode())) {
            request.getSession().setAttribute("user","user");
            return setEnumResult(ResponseEnum.SUCCESS, "/");
        }
        ResponseData data = new ResponseData();
        data.setMessage(response.getMsg());
        data.setStatus(ResponseEnum.FAILED.getCode());
        return data;
    }
    
    /**
     * 退出
     * @return
     */
    @RequestMapping(value="/logout",method =RequestMethod.GET)
    public String logout(HttpServletRequest request){
        try {
            request.getSession().removeAttribute("user");
        } catch (Exception e) {
            LOG.error("errorMessage:" + e.getMessage());
        }
        return redirectTo("/index/login.shtml");
    }
}
