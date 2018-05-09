package com.jim.util;


import org.apache.commons.lang3.StringUtils;

/**
 * @author 赵建龙
 * @date 2018/5/9
 */
public class LevelUtil {

    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    public static String calculateLevel(String parentLevel, int parentId){
        if (StringUtils.isBlank(parentLevel)){
            return ROOT;
        }else{
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}
