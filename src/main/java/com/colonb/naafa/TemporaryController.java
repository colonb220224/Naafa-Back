package com.colonb.naafa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class TemporaryController {

    @RequestMapping("{var}/{seq}")
    String tempMapping(@PathVariable String var){
        return "user/"  +var;
    }

    @RequestMapping("{var}")
    String tempMapping1(@PathVariable String var){
        return "user/"  +var;
    }

    @RequestMapping("admin/{var}")
    String tempMapping2(@PathVariable String var){
        return "admin/"+var;
    }
}
