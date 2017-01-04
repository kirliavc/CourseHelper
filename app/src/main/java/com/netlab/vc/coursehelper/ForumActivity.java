package com.netlab.vc.coursehelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netlab.vc.coursehelper.util.Constants;
import com.netlab.vc.coursehelper.util.Parameters;
import com.netlab.vc.coursehelper.util.WebConnection;
import com.netlab.vc.coursehelper.util.jsonResults.Forum;
import com.netlab.vc.coursehelper.util.jsonResults.ForumResult;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dingfeifei on 16/12/16.
 */

public class ForumActivity extends AppCompatActivity implements OnScrollListener,SwipeRefreshLayout.OnRefreshListener, ViewPager.OnPageChangeListener {
    Forum[] forumList;
    String courseId;
    private int page = 1;
    private int lastVisibleIndex;
    private int newIndex;
    private ForumAdapter adapter;
    ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
    private SwipeRefreshLayout refreshLayout;
    private ListView myLayout;
    private LinearLayout footer;
    private FloatingActionButton newPostButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回箭头
        Intent intent = getIntent();
        forumList=new Forum[]{};
        courseId = intent.getStringExtra("course_id");
        myLayout = (ListView) findViewById(R.id.forum_listview);
        footer=(LinearLayout)findViewById(R.id.footer_layout);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        newPostButton=(FloatingActionButton)findViewById(R.id.fab_newpost);
        newPostButton.setOnClickListener(new NewPostClickListener());
        refreshLayout.setOnRefreshListener(this);
        myLayout.setOnScrollListener(this);
        //initData();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class NewPostClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(ForumActivity.this,NewPostActivity.class);
            intent.putExtra("course_id",courseId);
            startActivity(intent);
        }
    }
    private void initData() {
        page = 1;
        loadData();
    }

    private void loadData() {
        // 这里模拟从服务器获取数据
        new GetForumTask().execute();
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
            new GetForumTask().execute();
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

    @Override
    public void onRefresh() {
        if(refreshLayout.isRefreshing()){
            forumList=new Forum[]{};
            page=1;
            new GetForumTask().execute();
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        forumList=new Forum[]{};
        page=1;
        new GetForumTask().execute();
    }

    public class GetForumTask extends AsyncTask<Void,Void,Boolean> {
        @Override
        public void onPreExecute(){
            footer.setVisibility(View.VISIBLE);
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
                arrayList.add(new Parameters("_id", Constants._id));
                arrayList.add(new Parameters("course_id", courseId));
                arrayList.add(new Parameters("type", "EX"));
                arrayList.add(new Parameters("page", String.valueOf(page)));
                Parameters parameters = WebConnection.connect(Constants.baseUrl+Constants.AddUrls.get("FORUM_INFO"),
                        arrayList,WebConnection.CONNECT_GET);
                Log.e(parameters.name,parameters.value);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期的格式，遇到这个格式的数据转为Date对象
                Gson gson = gsonBuilder.create();
                ForumResult forumResult = gson.fromJson(parameters.value, ForumResult.class);
                if(forumResult.getSuccess()) {
                    forumList= concat(forumList,forumResult.getForums());
                    if (forumResult.getForums().length > 0)
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
            adapter = new ForumAdapter(ForumActivity.this, R.layout.forum_item, forumList);
            myLayout.setAdapter(adapter);
            myLayout.setSelection(newIndex);
            myLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(ForumActivity.this, PostDetailActivity.class);
                    intent.putExtra("course_id",courseId);
                    intent.putExtra("posting_id",forumList[position].getPosting_id());
                    startActivity(intent);
                }
            });
            if(refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(false);
        }
    }
}
