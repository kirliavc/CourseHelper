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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netlab.vc.coursehelper.util.Constants;
import com.netlab.vc.coursehelper.util.Parameters;
import com.netlab.vc.coursehelper.util.WebConnection;
import com.netlab.vc.coursehelper.util.jsonResults.Announcement;
import com.netlab.vc.coursehelper.util.jsonResults.AnnouncementResult;

import java.util.ArrayList;

/**
 * Created by dingfeifei on 16/12/16.
 */

public class AnnouncementActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    Announcement[] announcementList=new Announcement[]{};
    String courseName;
    private int page = 1;
    private ListView announceList;
    private SwipeRefreshLayout refreshLayout;
    private Button btn_next, btn_pre;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        courseName = intent.getStringExtra("course_id");
        announceList = (ListView)findViewById(R.id.announcement_listview);
        btn_next = (Button)findViewById(R.id.btnNext);
        btn_pre = (Button)findViewById(R.id.btnPre);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.announcement_swipeRefreshLayout);
        refreshLayout.setOnRefreshListener(this);
        new GetAnnouncementTask().execute();
        btn_next.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                page++;
                new GetAnnouncementTask().execute();
            }
        });
        btn_pre.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (page >= 2) {
                    page--;
                    new GetAnnouncementTask().execute();
                }
                else
                    Toast.makeText(getApplicationContext(),"当前已是第一页",Toast.LENGTH_SHORT).show();
            }
        });
        Log.e("HEHE", "1");
        //return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        if (refreshLayout.isRefreshing()) {
//            ArrayList<NameValuePair> params = new ArrayList<>();
//            BasicNameValuePair valuesPair = new BasicNameValuePair("course_id", courseId);
//            params.add(valuesPair);
            new GetAnnouncementTask().execute();
        }
    }

    public class GetAnnouncementTask extends AsyncTask<Void,Void,Boolean> {
        /*
        private View view;
        public GetMyCourseTask(View _view){
            view=_view;
        }
        */
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
                arrayList.add(new Parameters("_id", Constants._id));
                arrayList.add(new Parameters("page", String.valueOf(page)));
                arrayList.add(new Parameters("course_id", courseName));
                Parameters parameters = WebConnection.connect(Constants.baseUrl+Constants.AddUrls.get("ANNOUNCEMENT_INFO"),
                        arrayList,WebConnection.CONNECT_GET);
                Log.e(parameters.name,parameters.value);
                AnnouncementResult announcementResult = new Gson().fromJson(parameters.value, AnnouncementResult.class);
                Log.e("length:",String.valueOf(announcementResult.getAnnouncements().length));
                if(announcementResult.getSuccess()) {
                    Log.e(announcementResult.getSuccess().toString(),"1");
                    announcementList=announcementResult.getAnnouncements();
                    return true;
                }
                else
                    return false;
            } catch (Exception e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result){
            if (announcementList.length == 0){
                Toast.makeText(getApplicationContext(),"当前已是最后一页",Toast.LENGTH_SHORT).show();
                page--;
                return;
            }
            announceList.setAdapter(new AnnouncementAdapter(AnnouncementActivity.this,R.layout.announcement_item,announcementList));
            if(refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(false);
        }
    }
}
