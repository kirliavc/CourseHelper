package com.netlab.vc.coursehelper;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.netlab.vc.coursehelper.util.Constants;
import com.netlab.vc.coursehelper.util.DateFormatter;
import com.netlab.vc.coursehelper.util.Parameters;
import com.netlab.vc.coursehelper.util.WebConnection;
import com.netlab.vc.coursehelper.util.jsonResults.PostResult;

import java.util.ArrayList;

import static com.netlab.vc.coursehelper.util.WebConnection.CONNECT_POST;

public class PostDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,ThreadListAdapter.LikeCallBack {
    String postingId,courseId;
    private SwipeRefreshLayout refreshLayout;
    private TextView postTitle, postText, postDate, likeNumber;
    private ListView threadListView;
    private View headerView;
    private ThreadListAdapter adapter;
    private EditText editReply;
    private Button sendReply;
    private boolean isFirstCreate=true;
    PostResult postResult;
    PostDetailActivity postDetailActivity;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回箭头
        postDetailActivity=this;
        Intent intent = getIntent();
        postingId = intent.getStringExtra("posting_id");
        courseId=intent.getStringExtra("course_id");
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        threadListView=(ListView)findViewById(R.id.reply_list);
        headerView=getLayoutInflater().inflate(R.layout.thread_title,null);

        postTitle = (TextView)headerView.findViewById(R.id.thread_title);
        postText = (TextView)headerView.findViewById(R.id.thread_content);
        postDate = (TextView)headerView.findViewById(R.id.post_time);
        likeNumber = (TextView)headerView.findViewById(R.id.like_number);
        editReply=(EditText)findViewById(R.id.edit_reply);
        sendReply=(Button)findViewById(R.id.send_reply);
        sendReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editReply.getWindowToken(), 0) ;
                new ReplyTask().execute(editReply.getText().toString());
                editReply.setText("");
            }
        });
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

    @Override
    public void clickLike(View v,String replyId,String type,int position) {
        new LikeTask().execute(replyId,type,position);
    }

    public class ReplyTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            try{
                ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
                arrayList.add(new Parameters("_id", Constants._id));
                arrayList.add(new Parameters("posting_id", postingId));
                arrayList.add(new Parameters("content",params[0]));
                Parameters parameters= WebConnection.connect(
                        Constants.baseUrl+Constants.AddUrls.get("FORUM_REPLY"),arrayList,CONNECT_POST);
                return parameters.name.equals("200");
            }
            catch (Exception e){
                return false;
            }
        }
        @Override
        public void onPostExecute(Boolean success){
            if(!success){
                return;
            }
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
            if(isFirstCreate){
                PostDetailActivity.this.setTitle(postResult.getTitle());
                postTitle.setText(postResult.getTitle());
                postText.setText(postResult.getContent());
                postDate.setText(DateFormatter.format(postResult.getPostDate()));
                likeNumber.setText(String.valueOf(postResult.getLike()));
                threadListView.addHeaderView(headerView);
                isFirstCreate=false;
            }

            adapter=new ThreadListAdapter(getApplicationContext(),
                    R.layout.thread_item,postResult.getReplys(),PostDetailActivity.this);
            threadListView.setAdapter(adapter);
            if(refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(false);
        }
    }
    public class LikeTask extends AsyncTask<Object,Void,Boolean>{
        private int position;
        @Override
        protected Boolean doInBackground(Object... params) {
            try {
                ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
                arrayList.add(new Parameters("_id", Constants._id));
                arrayList.add(new Parameters("like_id", params[0].toString()));
                arrayList.add(new Parameters("course_id", courseId));
                arrayList.add(new Parameters("type", params[1].toString()));
                position=Integer.parseInt(params[2].toString());
                Parameters parameters = WebConnection.connect(
                        Constants.baseUrl + Constants.AddUrls.get("FORUM_LIKE"), arrayList, CONNECT_POST);
                return parameters.name.equals("200");
            }
            catch(Exception e){
                return false;
            }
        }
        @Override
        public void onPostExecute(Boolean success){
            if(!success){
                return;
            }
            int originalLike=postResult.getReplys()[position].getLike().intValue();
            postResult.getReplys()[position].setLike(originalLike+1);
        }
    }
}
