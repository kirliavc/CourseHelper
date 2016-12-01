package com.netlab.vc.coursehelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.netlab.vc.coursehelper.util.Constants;
import com.netlab.vc.coursehelper.util.Parameters;
import com.netlab.vc.coursehelper.util.WebConnection;
import com.netlab.vc.coursehelper.util.jsonResults.Course;
import com.netlab.vc.coursehelper.util.jsonResults.CourseResult;

import java.util.ArrayList;

/**
 * Created by Vc on 2016/11/4.
 */

public class MainCourseFragment extends Fragment {
    public MainCourseFragment(){}
    Course [] courseList=new Course[]{};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.content_main, container, false);
        new GetMyCourseTask(view).execute();


        Log.e("HEHE", "1");
        return view;
    }
    public class GetMyCourseTask extends AsyncTask<Void,Void,Boolean>{
        private View view;
        public GetMyCourseTask(View mview){
            view=mview;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
                arrayList.add(new Parameters("_id",Constants._id));
                Parameters parameters = WebConnection.connect(Constants.baseUrl+Constants.AddUrls.get("COURSE_LIST"),
                        arrayList,WebConnection.CONNECT_GET);
                Log.e(parameters.name,parameters.value);
                CourseResult courseResult = JSON.parseObject(parameters.value,CourseResult.class);
                if(courseResult.getSuccess()) {
                    Log.e(courseResult.getSuccess().toString(),"1");
                    courseList=courseResult.getCourses();
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
            ListView coursePanel=(ListView)view.findViewById(R.id.course_list) ;
            coursePanel.setAdapter(new CourseAdapter(getContext(),R.layout.course_item,courseList));
            coursePanel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(getActivity(),CourseActivity.class);
                    startActivity(intent);
                }
            });
        }

    }
}
