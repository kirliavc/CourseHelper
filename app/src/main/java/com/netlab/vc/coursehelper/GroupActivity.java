package com.netlab.vc.coursehelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netlab.vc.coursehelper.util.Constants;
import com.netlab.vc.coursehelper.util.Parameters;
import com.netlab.vc.coursehelper.util.WebConnection;
import com.netlab.vc.coursehelper.util.jsonResults.ApplyResult;
import com.netlab.vc.coursehelper.util.jsonResults.Group;
import com.netlab.vc.coursehelper.util.jsonResults.GroupResult;
import com.netlab.vc.coursehelper.util.jsonResults.QueryResult;

import java.util.ArrayList;

import static com.netlab.vc.coursehelper.util.Constants._id;

/**
 * Created by dingfeifei on 16/11/20.
 */

public class GroupActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    Group[] groupList=new Group[]{};
    String groupId;
    Boolean In;
    String courseId;
    private ListView groupListView;
    private FloatingActionButton myGroupButton;
    private SwipeRefreshLayout refreshLayout;
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        courseId=intent.getStringExtra("course_id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        groupListView = (ListView)findViewById(R.id.group_list);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.group_refreshLayout);
        myGroupButton = (FloatingActionButton)findViewById(R.id.my_group);
        refreshLayout.setOnRefreshListener(this);
        new GetGroupTask().execute();
        myGroupButton.setOnClickListener(new MyGroupClickListener());
        Log.e("HEHE", "1");
        //return view;
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
        if(item.getItemId() == R.id.submit_group)
        {
            if(In == true){
                Toast.makeText(getApplicationContext(),"您已加入小组！",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyGroupClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (In == false){
                Toast.makeText(getApplicationContext(),"您还没有加入任何小组！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_list, menu);
        return true;
    }

    @Override
    public void onRefresh() {
        if (refreshLayout.isRefreshing()) {
//            ArrayList<NameValuePair> params = new ArrayList<>();
//            BasicNameValuePair valuesPair = new BasicNameValuePair("course_id", courseId);
//            params.add(valuesPair);
            new GetGroupTask().execute();
        }
    }

    public class GetGroupTask extends AsyncTask<Void,Void,Boolean> {
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
                arrayList.add(new Parameters("_id", _id));
                arrayList.add(new Parameters("course_id", courseId));
                Parameters parameters = WebConnection.connect(Constants.baseUrl+Constants.AddUrls.get("GIVE_GROUP"),
                        arrayList,WebConnection.CONNECT_GET);
                //Log.e(parameters.name,parameters.value);
                GroupResult groupResult = new Gson().fromJson(parameters.value,GroupResult.class);
                parameters = WebConnection.connect(Constants.baseUrl+Constants.AddUrls.get("GROUP_QUERY"),
                        arrayList,WebConnection.CONNECT_GET);
                if (parameters.name.equals("503")){
                    In = false;
                }
                else{
                    In = true;
                    QueryResult queryResult = new Gson().fromJson(parameters.value,QueryResult.class);
                    Log.e(parameters.name,parameters.value);
                    groupId = queryResult.getGroup_id();
                }
                if(groupResult.getSuccess()) {
                    Log.e(groupResult.getSuccess().toString(),"1");
                    groupList=groupResult.getGroups();
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
            groupListView.setAdapter(new GroupAdapter(GroupActivity.this,R.layout.group_item,groupList));
            groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(GroupActivity.this);
                    alertdialogbuilder.setMessage("申请加入该组？");
                    AlertDialog alertdialog1=alertdialogbuilder.create();
                    alertdialogbuilder.setPositiveButton("取消", new DialogInterface.OnClickListener(){
                        //RelativeLayout listitem = (RelativeLayout) parent.getItemAtPosition(position);
                        public void onClick(DialogInterface arg0,int arg1) {
                            //android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    });
                    alertdialogbuilder.setNegativeButton("确定", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface arg0,int arg1) {
                            // 按确定按钮表示选该课
                            new ApplyTask(groupList[position].get_id(),Constants.realname, _id).execute();
                        }
                    });
                    alertdialogbuilder.show();
                }
            });
            if(refreshLayout.isRefreshing())
                refreshLayout.setRefreshing(false);
        }
    }

    public class ApplyTask extends AsyncTask<Void,Void,Boolean>{
        String group_id, member_name, member_id;
        public ApplyTask(String _group_id, String _member_name, String _member_id){
            group_id = _group_id;
            member_name = _member_name;
            member_id = _member_id;
        }
        protected Boolean doInBackground(Void... params) {
            try {
                ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
                arrayList.add(new Parameters("_id", _id));
                arrayList.add(new Parameters("group_id", group_id));
                arrayList.add(new Parameters("member_name", member_name));
                arrayList.add(new Parameters("member_id", member_id));
                Log.e("member_name",member_name);
                Parameters parameters = WebConnection.connect(Constants.baseUrl + Constants.AddUrls.get("GROUP_APPLY"),
                        arrayList, WebConnection.CONNECT_POST);
                ApplyResult applyResult = new Gson().fromJson(parameters.value,ApplyResult.class);
                //Log.e("length:",String.valueOf(courseResult.getCourses().length));
                if(applyResult.getSuccess()){
                    Log.e(applyResult.getSuccess().toString(),"1");
                    Toast.makeText(getApplicationContext(),"申请成功！",Toast.LENGTH_SHORT).show();
                    //courseList=courseResult.getCourses();
                    return true;
                }
                else
                    return false;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
