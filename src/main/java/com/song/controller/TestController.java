package com.song.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.song.utils.DecriptUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/9/27.
 */
@Controller
@Scope("prototype")
public class TestController {

    @RequestMapping("/index.jhtml")
    public ModelAndView getIndex(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @RequestMapping("/exceptionForPageJumps.jhtml")
    public ModelAndView exceptionForPageJumps(HttpServletRequest request) throws Exception {
        //throw new BusinessException(LuoErrorCode.NULL_OBJ);
        throw new Exception();
    }

    @RequestMapping(value="/businessException.json", method= RequestMethod.POST)
    @ResponseBody
    public String businessException(HttpServletRequest request) throws Exception {
        //throw new BusinessException(LuoErrorCode.NULL_OBJ);
        throw new Exception();
    }

    @RequestMapping(value="/otherException.json", method=RequestMethod.POST)
    @ResponseBody
    public String otherException(HttpServletRequest request) throws Exception {
        throw new Exception();
    }

    //跳转到登录页面
    @RequestMapping("/login.jhtml")
    public ModelAndView login() throws Exception {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    //跳转到登录成功页面
    @RequestMapping("/loginsuccess.jhtml")
    public ModelAndView loginsuccess() throws Exception {
        ModelAndView mav = new ModelAndView("loginsuccess");
        return mav;
    }

    @RequestMapping("/newPage.jhtml")
    public ModelAndView newPage() throws Exception {
        ModelAndView mav = new ModelAndView("newPage");
        return mav;
    }

    @RequestMapping("/newPageNotAdd.jhtml")
    public ModelAndView newPageNotAdd() throws Exception {
        ModelAndView mav = new ModelAndView("newPageNotAdd");
        return mav;
    }

    /**
     * 验证用户名和密码
     * @param  username,String password
     * @return
     */
    @RequestMapping(value="/checkLogin.json",method=RequestMethod.POST)
    @ResponseBody
    public String checkLogin(String username,String password) {
        Map<String, Object> result = new HashMap<String, Object>();
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(username, DecriptUtil.MD5(password));
            org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
            if (!currentUser.isAuthenticated()){
                //使用shiro来验证
                token.setRememberMe(true);
                currentUser.login(token);//验证角色和权限
            }
        }catch(Exception ex){
            //throw new BusinessException(LuoErrorCode.LOGIN_VERIFY_FAILURE);
        }
        result.put("success", true);
        return JSONUtils.toJSONString(result);
    }

    /**
     * 退出登录
     */
    @RequestMapping(value="/logout.json",method=RequestMethod.POST)
    @ResponseBody
    public String logout() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return JSONUtils.toJSONString(result);
    }
}
