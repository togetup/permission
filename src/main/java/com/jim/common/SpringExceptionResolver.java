package com.jim.common;

import com.jim.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 赵建龙
 * @date 2018/5/4
 */
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(SpringExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        String url = httpServletRequest.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "System error";

        // .json, .page
        // 这里我们要求项目中所有请求json数据，都是用.json结尾。
        if (url.endsWith(".json")){
            if (e instanceof PermissionException){
                JsonData result = JsonData.fail(e.getMessage());

                // 下面的jsonView对应spring-servlet.xml下的 jsonView bean
                mv = new ModelAndView("jsonView", result.toMap());
            }else{
                logger.error("unknow json exception, url:" + url, e);
                JsonData result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView", result.toMap());
            }
        }else if(url.endsWith(".page")){
            // 这里我们要求项目中所有请求page数据，都是用.page结尾。
            logger.error("unknow page exception, url:" + url, e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("exception", result.toMap());
        }else{
            logger.error("unknow exception, url:" + url, e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView", result.toMap());
        }

        return mv;
    }
}
