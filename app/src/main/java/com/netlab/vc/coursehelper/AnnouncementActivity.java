package com.netlab.vc.coursehelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.netlab.vc.coursehelper.AutoLoadListview.OnLoadListener;
import com.netlab.vc.coursehelper.AutoLoadListview.OnRefreshListener;
import com.netlab.vc.coursehelper.util.Constants;
import com.netlab.vc.coursehelper.util.Parameters;
import com.netlab.vc.coursehelper.util.WebConnection;
import com.netlab.vc.coursehelper.util.jsonResults.Announcement;
import com.netlab.vc.coursehelper.util.jsonResults.AnnouncementResult;

import java.util.ArrayList;

/**
 * Created by dingfeifei on 16/12/16.
 */

public class AnnouncementActivity extends AppCompatActivity implements OnRefreshListener, OnLoadListener {
    Announcement[] announcementList = new Announcement[]{};
    String courseName;
    private int page = 1;
    //private ListView announceListView;
    private AnnouncementAdapter adapter;
    ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
    //private SwipeRefreshLayout refreshLayout;
    private AutoLoadListview myLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回箭头
        Intent intent = getIntent();
        courseName = intent.getStringExtra("course_id");
        myLayout = (AutoLoadListview) findViewById(R.id.announcement_listview);

        myLayout.setOnRefreshListener(this);
        myLayout.setOnLoadListener(this);
        initData();
    }

    private void initData() {
        page = 1;
        loadData(AutoLoadListview.REFRESH);
    }

    private void loadData(final int what) {
        // 这里模拟从服务器获取数据
        new GetAnnouncementTask().execute();
    }

    @Override
    public void onRefresh() {
        page--;
        if (page <= 0)
            page = 1;
        loadData(AutoLoadListview.REFRESH);
    }

    @Override
    public void onLoad() {
        page++;
        loadData(AutoLoadListview.LOAD);
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




    public class GetAnnouncementTask extends AsyncTask<Void,Void,Boolean> {
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
                    if (announcementList.length > 0)
                        return true;
                    else
                        return false;
                }
                else
                    return false;
            } catch (Exception e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result){
            if(!result){
                page--;
                myLayout.onRefreshComplete();
                return;
            }
            Log.e("result",String.valueOf(result));
            myLayout.setResultSize(announcementList.length);
            myLayout.setAdapter(new AnnouncementAdapter(AnnouncementActivity.this,R.layout.announcement_item,announcementList));
            //adapter = new AnnouncementAdapter(AnnouncementActivity.this, R.layout.announcement_item, announcementList);
            //myLayout.setAdapter(adapter);
            myLayout.onRefreshComplete();
        }
    }
}
