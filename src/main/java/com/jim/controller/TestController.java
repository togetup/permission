package com.jim.controller;

import com.jim.common.JsonData;
import com.jim.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 赵建龙
 * @date 2018/5/3
 */
@Controller
@Slf4j
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

//    @RequestMapping("/hello")
//    @ResponseBody
//    public String hello(){
//        logger.info("hello");
//        return "hello, permission";
//    }


    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hellojd(){
//        logger.error("hellojd");

//        throw new RuntimeException("test exception");
        throw new PermissionException("test exception");
//        return JsonData.success("hello, permission");
    }


}
