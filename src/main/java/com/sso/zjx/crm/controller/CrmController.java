package com.sso.zjx.crm.controller;

import com.sso.zjx.crm.config.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class CrmController {

    @Autowired
    MyProperties myProperties;

    @RequestMapping("/")
    public String main(Model model){
        model.addAttribute("logOutUrl",myProperties.getSsoUrl()+"/logOut");
        return "index";
    }


    @ResponseBody
    @RequestMapping("/logOut")
    public String test(HttpSession session){
        session.invalidate();
        return "true";
    }
}
