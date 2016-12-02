package com.netlab.vc.coursehelper.util;

import java.util.HashMap;

/**
 * Created by Vc on 2016/11/23.
 */

public class Constants {
    public static final String baseUrl="http://222.29.98.104:3000/api";
    public static final HashMap<String, String> AddUrls = new HashMap<String, String>(){
        {
            put("LOGIN", "/user/login");
            put("REGISTER", "/user/register");
            put("COURSE_LIST","/user/courses");
            put("COURSE_INFO","/course/info");
        }
    };
    public static String token= null;
    public static String _id;
    public static String username;
    public static String realname;
}
