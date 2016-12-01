package com.netlab.vc.coursehelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by dingfeifei on 16/11/20.
 */


/*
 * 课程信息展示界面
 * 可以跳转到课程的所有功能
 * 目前的功能有签到、通知、测试、内容、论坛和小组
 */
public class CourseActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    //分别进入通知、签到、测试、内容、论坛和小组界面的按钮
    private LinearLayout announcementList, testList, contentList, forumList, groupList;
    private TextView courseName, courseTeacher, courseDate, signUpedInfo, absenceInfo;//各种数据的Textview
    private Course course;//当前course的信息
    private Button signUp;//签到按钮
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    protected static final String TAG = "CourseActivity";//LOG用到的标记

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回箭头
        findViews();
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        setListeners();
        //getData();
    }

    private void findViews(){
        announcementList = (LinearLayout) findViewById(R.id.announcement_list);
        testList = (LinearLayout) findViewById(R.id.test_list);
        contentList = (LinearLayout) findViewById(R.id.content_list);
        forumList = (LinearLayout) findViewById(R.id.forum_list);
        groupList = (LinearLayout) findViewById(R.id.group_list);
        courseName = (TextView) findViewById(R.id.course_name);
        courseTeacher = (TextView) findViewById(R.id.course_teacher);
        courseDate = (TextView) findViewById(R.id.course_date);
        signUpedInfo = (TextView) findViewById(R.id.sign_uped_info);
        absenceInfo = (TextView) findViewById(R.id.absence_info);
        signUp = (Button) findViewById(R.id.sign_up);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        progressBar = (ProgressBar) findViewById(R.id.load_progress);
    }

    private void setListeners() {
        announcementList.setOnClickListener(new LinearLayout.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this, LoginActivity.class);
            }
        });
        testList.setOnClickListener(new LinearLayout.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this, LoginActivity.class);
            }
        });
        contentList.setOnClickListener(new LinearLayout.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this, LoginActivity.class);
            }
        });
        forumList.setOnClickListener(new LinearLayout.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this, LoginActivity.class);
            }
        });
        groupList.setOnClickListener(new LinearLayout.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this, LoginActivity.class);
            }
        });
        refreshLayout.setOnRefreshListener(this);
        //TODO 签到
    }

    private void refresh(){
        int a = 1;
    }

    @Override
    public void onRefresh() {
        if (refreshLayout.isRefreshing()) {
//            ArrayList<NameValuePair> params = new ArrayList<>();
//            BasicNameValuePair valuesPair = new BasicNameValuePair("course_id", courseId);
//            params.add(valuesPair);
            refresh();
        }
    }




}