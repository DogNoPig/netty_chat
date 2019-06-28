package com.xw.mui_demo.controller;

import com.xw.config.GlobalCorsConfig;
import com.xw.mui_demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xw
 * @date 2019/6/28 10:30
 */
@RestController
@RequestMapping("index")
public class LoginController {

    @RequestMapping("login")
    public Map<String,Object> login(@RequestBody User user){
        System.out.println(user);
        Map<String,Object> map = new HashMap<>();

        if ("xw".equals(user.getUserName()) && "123".equals(user.getPassword())){
            map.put("code",200);
            map.put("message","登录成功！");
        }else{
            map.put("code",400);
            map.put("message","登录失败！");
        }
        return map;
    }
}
