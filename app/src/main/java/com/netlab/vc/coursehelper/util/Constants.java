package com.netlab.vc.coursehelper.util;

import com.netlab.vc.coursehelper.R;

import java.util.HashMap;

/**
 * Created by Vc on 2016/11/23.
 */

public class Constants {
    public static final String baseUrl="http://222.29.98.104:3000/api";
    public static final String privateBaseUrl="http://222.29.98.104:3000/private";
    public static final HashMap<String, String> AddUrls = new HashMap<String, String>(){
        {
            put("LOGIN", "/user/login");
            put("REGISTER", "/user/register");
            put("COURSE_LIST","/user/courses");
            put("COURSE_INFO","/course/info");
            put("QUIZ_LIST","/quiz/list");
            put("QUIZ_CONTENT","/quiz/content");
            put("INFO","/user/info");
            put("QUIZ_SUBMIT","/answer/submit");
            put("ELECTIVE_COURSE", "/course/all");
            put("COURSE_SELECT", "/user/attend");
            put("ANSWER_QUIZ_INFO","/answer/quiz/info");
            put("LESSON_INFO","/lesson/info");
            put("FILE_INFO","/fileinfo/");
            put("SIGN_INFO","/signin/info");
            put("SIGN_SUBMIT","/signin/submit");
            put("ANNOUNCEMENT_INFO", "/notification/getList");
            //teacher API
            put("SIGN_UUID","/sign/uuid");
            put("SIGN_ENABLE","/sign/enable");
            put("SIGN_DISABLE","/sign/disable");
            put("SIGN_REGISTER","/sign/register");
        }
    };
    public static String token= null;
    public static String _id;
    public static String username;
    public static String realname;
    public static String phone,email,type,avatars,password;
    public static boolean admin;
    public static final HashMap<String, Integer> QuestionTypeName = new HashMap<String, Integer>(){
        {
            put("single choice", R.string.single_choice);
            put("multiple choice", R.string.multiple_choice);
            put("true/false", R.string.true_or_false);
            put("blank filling", R.string.blank_filling);
            put("short answer", R.string.short_answer);
        }
    };
    public static final HashMap<Integer, String> AnswerTypeName = new HashMap<Integer, String>(){
        {
            put(0, "A");
            put(1, "B");
            put(2, "C");
            put(3, "D");
            put(4, "E");
            put(5, "F");
            put(6, "G");
            put(7, "H");
            put(8, "I");
            put(9, "J");
            put(10, "K");
        }
    };
}
