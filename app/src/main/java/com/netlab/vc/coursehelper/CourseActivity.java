package com.netlab.vc.coursehelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.netlab.vc.coursehelper.util.Constants;
import com.netlab.vc.coursehelper.util.Parameters;
import com.netlab.vc.coursehelper.util.WebConnection;
import com.netlab.vc.coursehelper.util.jsonResults.Course;

import java.util.ArrayList;

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
    private String course_id;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回箭头
        Intent intent=getIntent();
        course_id=intent.getStringExtra("course_id");
        Log.e("course_id",course_id);
        findViews();
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        setListeners();
        getData();
    }
    @Override
    public void onBackPressed(){
        Log.e("BackPressed","1");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                Intent intent = new Intent(CourseActivity.this, TestListActivity.class);
                intent.putExtra("course_id",course_id);
                startActivity(intent);
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

        getData();
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
    public void getData(){
        new GetCourseInfoTask(course_id).execute();
    }
    public class GetCourseInfoTask extends AsyncTask<Void,Void,Boolean>{
        private String course_id;
        private String _id;
        public GetCourseInfoTask(String _course_id){
            course_id=_course_id;
            _id= Constants._id;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
                arrayList.add(new Parameters("_id", Constants._id));
                arrayList.add(new Parameters("course_id", course_id));
                Log.e("course_id",course_id);
                Parameters parameters = WebConnection.connect(Constants.baseUrl + Constants.AddUrls.get("COURSE_INFO"),
                        arrayList, WebConnection.CONNECT_GET);
                Log.e(parameters.name, parameters.value);
                course= new Gson().fromJson(parameters.value,Course.class);

                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean success){
            if(!success){
                //TODO
                return;
            }
            courseName.setText(course.getName());
            courseTeacher.setText(course.getTerm());
            courseDate.setText(course.getIntroduction());
            progressBar.setVisibility(View.GONE);
            if(refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(false);
        }
    }


}