package com.aoto.xandroid.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/31.
 */

public class Tools {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str){
        if(str!=null){
            str=str.trim();
        }
        if(str!=null&&!str.equals("")&&!str.equals("null")&&!str.equals("NULL")&&!str.equals("Null")&&str.length()>0)
            return true;
        return false;
    }
}
