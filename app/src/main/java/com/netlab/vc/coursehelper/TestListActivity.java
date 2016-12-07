package com.netlab.vc.coursehelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by dingfeifei on 16/12/2.
 */

/*
 * 展示测试列表的activity
 */
public class TestListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ListView testList;
    private ProgressBar progressBar;
    private TextView noTest;
    private String course_id;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Intent intent=getIntent();
        course_id=intent.getStringExtra("course_id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        findViews();
    }
    void findViews(){
        testList=(ListView) findViewById(R.id.test_list);
        progressBar=(ProgressBar) findViewById(R.id.load_progress);
        noTest=(TextView)findViewById(R.id.no_contents);
    }

    @Override
    public void onRefresh() {

    }
}
