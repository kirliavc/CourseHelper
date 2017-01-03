package com.netlab.vc.coursehelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.netlab.vc.coursehelper.util.Constants;
import com.netlab.vc.coursehelper.util.Parameters;
import com.netlab.vc.coursehelper.util.WebConnection;
import com.netlab.vc.coursehelper.util.jsonResults.PostResult;

import java.util.ArrayList;

public class PostDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener  {
    String postingId;
    private SwipeRefreshLayout refreshLayout;
    private TextView postTitle, postText, postDate, likeNumber;
    private ForumAdapter adapter;
    PostResult postResult;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回箭头
        Intent intent = getIntent();
        postingId = intent.getStringExtra("posting_id");
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        postTitle = (TextView)findViewById(R.id.post_title);
        postText = (TextView)findViewById(R.id.post_text);
        postDate = (TextView)findViewById(R.id.post_time);
        likeNumber = (TextView)findViewById(R.id.like_number);
        refreshLayout.setOnRefreshListener(this);
        new GetPostTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        if(refreshLayout.isRefreshing()){
            new GetPostTask().execute();
        }
    }

    public class GetPostTask extends AsyncTask<Void,Void,Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
                arrayList.add(new Parameters("_id", Constants._id));
                arrayList.add(new Parameters("posting_id", postingId));
                Parameters parameters = WebConnection.connect(Constants.baseUrl+Constants.AddUrls.get("FORUM_DETAIL"),
                        arrayList,WebConnection.CONNECT_GET);
                Log.e(parameters.name,parameters.value);
                postResult = new Gson().fromJson(parameters.value, PostResult.class);
                if(postResult.getSuccess())
                    return true;
                else
                    return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result){
            if (!result) {
                //TODO
                return;
            }
            postTitle.setText(postResult.getTitle());
            postText.setText(postResult.getContent());
            postDate.setText(String.valueOf(postResult.getPostDate()));
            likeNumber.setText(String.valueOf(postResult.getLike()));
            if(refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(false);
        }
    }
}
