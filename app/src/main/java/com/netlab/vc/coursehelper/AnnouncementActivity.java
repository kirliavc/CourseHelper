package com.netlab.vc.coursehelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.netlab.vc.coursehelper.util.Constants;
import com.netlab.vc.coursehelper.util.Parameters;
import com.netlab.vc.coursehelper.util.WebConnection;
import com.netlab.vc.coursehelper.util.jsonResults.Announcement;
import com.netlab.vc.coursehelper.util.jsonResults.AnnouncementResult;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dingfeifei on 16/12/16.
 */

public class AnnouncementActivity extends AppCompatActivity implements OnScrollListener {
    Announcement[] announcementList = new Announcement[]{};
    String courseName;
    private int page = 1;
    private int lastVisibleIndex;
    private int newIndex;
    //private ListView announceListView;
    private AnnouncementAdapter adapter;
    ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
    //private SwipeRefreshLayout refreshLayout;
    private ListView myLayout;
    private LinearLayout footer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回箭头
        Intent intent = getIntent();
        courseName = intent.getStringExtra("course_id");
        myLayout = (ListView) findViewById(R.id.announcement_listview);
        footer=(LinearLayout)findViewById(R.id.footer_layout);
        myLayout.setOnScrollListener(this);
        initData();
    }

    private void initData() {
        page = 1;
        loadData();
    }

    private void loadData() {
        // 这里模拟从服务器获取数据
        new GetAnnouncementTask().execute();
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
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==OnScrollListener.SCROLL_STATE_IDLE
                &&lastVisibleIndex==adapter.getCount()){

            page++;
            new GetAnnouncementTask().execute();
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int
            totalItemCount) {
        lastVisibleIndex=firstVisibleItem+visibleItemCount;
        newIndex=firstVisibleItem;
    }
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public class GetAnnouncementTask extends AsyncTask<Void,Void,Boolean> {
        @Override
        public void onPreExecute(){
            footer.setVisibility(View.VISIBLE);
        }
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
                if(announcementResult.getSuccess()) {
                    announcementList= concat(announcementList,announcementResult.getAnnouncements());
                    if (announcementResult.getAnnouncements().length > 0)
                        return true;
                    else{
                        page--;
                        return false;
                    }

                }
                else
                    return false;
            } catch (Exception e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result){
            footer.setVisibility(View.GONE);
            if(!result){
                return;
            }
            //myLayout.setAdapter(new AnnouncementAdapter(AnnouncementActivity.this,R.layout.announcement_item,announcementList));
            adapter = new AnnouncementAdapter(AnnouncementActivity.this, R.layout.announcement_item, announcementList);
            myLayout.setAdapter(adapter);
            myLayout.setSelection(newIndex);
        }
    }
}
