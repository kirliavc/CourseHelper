package com.netlab.vc.coursehelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.netlab.vc.coursehelper.util.Constants;
import com.netlab.vc.coursehelper.util.Parameters;
import com.netlab.vc.coursehelper.util.WebConnection;
import com.netlab.vc.coursehelper.util.jsonResults.Quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dingfeifei on 16/12/2.
 */

/*
 * 展示测试列表的activity
 */
public class TestListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ListView testListView;
    private ProgressBar progressBar;
    private TextView noTest;
    private String course_id;
    private List<Quiz> quizList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Intent intent=getIntent();
        course_id=intent.getStringExtra("course_id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        findViews();
        getData();
    }
    void findViews(){
        testListView=(ListView) findViewById(R.id.test_list);
        progressBar=(ProgressBar) findViewById(R.id.load_progress);
        noTest=(TextView)findViewById(R.id.no_contents);
    }
    void getData(){


    }
    @Override
    public void onRefresh() {

    }
    public class getTestListTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
                arrayList.add(new Parameters("_id", Constants._id));
                arrayList.add(new Parameters("course_id", course_id));
                Parameters parameters = WebConnection.connect(Constants.baseUrl + Constants.AddUrls.get("QUIZ_LIST"),
                        arrayList, WebConnection.CONNECT_GET);
                Log.e(parameters.name, parameters.value);
                quizList = JSON.parseArray(parameters.value, Quiz.class);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean success){
            if(!success)return;
            List<Map<String,Object> >mapList=new ArrayList<>();
            for(int i=0;i<quizList.size();i++){
                Map<String,Object> quizItem=new HashMap<>();
                quizItem.put("quiz_name",quizList.get(i).getName());
                quizItem.put("finish_time",)
            }
            SimpleAdapter testListAdapter=new SimpleAdapter(this,
        }
    }
}
