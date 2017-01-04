package com.netlab.vc.coursehelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.netlab.vc.coursehelper.util.Parameters;

import java.util.ArrayList;

/**
 * Created by dingfeifei on 16/12/16.
 */

public class ForumActivity extends AppCompatActivity implements  ViewPager.OnPageChangeListener {



    ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
    private ViewPager vpager;

    private FloatingActionButton newPostButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回箭头
        vpager=(ViewPager)findViewById(R.id.forum_pager);
        vpager.setAdapter(new ForumPageAdapter(getSupportFragmentManager()));
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
        newPostButton=(FloatingActionButton)findViewById(R.id.fab_newpost);
        newPostButton.setOnClickListener(new NewPostClickListener());


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
            intent.putExtra("course_id",getIntent().getStringExtra("course_id"));
            startActivity(intent);
        }
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




}
