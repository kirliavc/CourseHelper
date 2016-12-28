package com.netlab.vc.coursehelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.netlab.vc.coursehelper.util.Parameters;
import com.netlab.vc.coursehelper.util.WebConnection;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab_upload);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_CANCELED);
                //回调图片类使用的
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri + "");
            //new UploadImageTask().execute(uri.toString());

            String[] pojo = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(uri, pojo, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                int colunm_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

                String path = cursor.getString(colunm_index);
                Log.e("path", path);
                new UploadImageTask().execute(path);
            }
            else{
                Log.e("Cursor","null");
            }

        }
        else{
            Log.e("Result","123");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public class UploadImageTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            try{
                ArrayList<Parameters> arrayList=new ArrayList<>();
                arrayList.add(new Parameters("title","123"));
                Parameters parameters= WebConnection.uploadFile(
                        "http://222.29.98.104:8081/upload",arrayList,params[0]);
                return parameters.name.equals("200");
            }
            catch (Exception e){
                return false;
            }
        }
    }
}
