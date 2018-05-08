package com.jim.common;

import com.jim.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 赵建龙
 * @date 2018/5/8
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);

    private static final String START_TIME = "requestStartTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        logger.info("request start. url:{}, params:{}", url, JsonMapper.obj2String(parameterMap));

        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME, start);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String url = request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        logger.info("request finished. url:{}, params:{}", url, JsonMapper.obj2String(parameterMap));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getRequestURI().toString();
        //Map parameterMap = request.getParameterMap();
        //logger.info("request complete. url:{}, params:{}", url, JsonMapper.obj2String(parameterMap));

        long start = (long) request.getAttribute(START_TIME);
        long end  = System.currentTimeMillis();
        logger.info("request complete. url:{}, cost:{}", url, end - start);
    }
}
