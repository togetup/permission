package com.jim.controller;

import com.jim.common.JsonData;
import com.jim.exception.ParamException;
import com.jim.exception.PermissionException;
import com.jim.param.TestVo;
import com.jim.util.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

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
        logger.info("hellojd");

//        throw new RuntimeException("test exception");
        throw new PermissionException("test exception");
//        return JsonData.success("hello, permission");
    }


    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) throws ParamException {
        logger.info("validate");

        // 第1种写法
        //try {
        //    Map<String, String> map = BeanValidator.validateObject(vo);
        //    //if (map != null && map.entrySet().size() > 0){
        //    if (MapUtils.isNotEmpty(map)){
        //        for (Map.Entry<String, String> entry : map.entrySet()){
        //            logger.info("{}->{}", entry.getKey(), entry.getValue());
        //        }
        //    }
        //} catch (Exception e){
        //}

        // 第二种写法
        BeanValidator.check(vo);

        return JsonData.success("test validate");
    }
}
