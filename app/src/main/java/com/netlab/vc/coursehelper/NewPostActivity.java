package com.netlab.vc.coursehelper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.netlab.vc.coursehelper.util.Constants;
import com.netlab.vc.coursehelper.util.Parameters;
import com.netlab.vc.coursehelper.util.WebConnection;

import java.util.ArrayList;

public class NewPostActivity extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private Button submitPost;
    private String courseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        courseId=getIntent().getStringExtra("course_id");
        title=(EditText)findViewById(R.id.edit_title);
        content=(EditText)findViewById(R.id.edit_content);
        submitPost=(Button)findViewById(R.id.submit_post);
        submitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreatePostTask().execute();
            }
        });
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
    public class CreatePostTask extends AsyncTask<Void,Void,Boolean> {

        private Editable mTitle;
        private Editable mContent;

        @Override
        public void onPreExecute() {
            mTitle = title.getText();
            mContent = content.getText();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
                arrayList.add(new Parameters("_id", Constants._id));
                arrayList.add(new Parameters("user_id", Constants._id));
                arrayList.add(new Parameters("name",Constants.realname));
                arrayList.add(new Parameters("course_id", courseId));
                arrayList.add(new Parameters("title", mTitle.toString()));
                arrayList.add(new Parameters("content", mContent.toString()));
                arrayList.add(new Parameters("postType","EX"));
                Parameters parameters = WebConnection.connect(Constants.baseUrl + Constants.AddUrls.get("FORUM_POST"),
                        arrayList, WebConnection.CONNECT_POST);
                return parameters.name.equals("200");
            }
            catch(Exception e){
                return  false;
            }
        }
        @Override
        public void onPostExecute(Boolean success){
            if(!success){
                Toast.makeText(getApplicationContext(),"发帖失败！",Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getApplicationContext(),"发帖成功！",Toast.LENGTH_SHORT).show();
            NewPostActivity.this.finish();
        }
    }
}
