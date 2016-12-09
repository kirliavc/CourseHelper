package com.netlab.vc.coursehelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.netlab.vc.coursehelper.util.Constants;
import com.netlab.vc.coursehelper.util.Parameters;
import com.netlab.vc.coursehelper.util.WebConnection;
import com.netlab.vc.coursehelper.util.jsonResults.Answer;
import com.netlab.vc.coursehelper.util.jsonResults.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dingfeifei on 16/12/2.
 */

public class QuestionListActivity extends AppCompatActivity {
    private String quizId;
    private Question[] questions;
    private Answer[] originAnswers;
    private ListView questionListView;
    private Boolean isAnswered;
    List<Map<String,Object>>mapList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        quizId=intent.getStringExtra("quiz_id");
        new getQuestionTask().execute();
    }
    class getQuestionTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                ArrayList<Parameters> arrayList = new ArrayList<Parameters>();
                arrayList.add(new Parameters("_id", Constants._id));
                arrayList.add(new Parameters("quiz_id", quizId));
                Parameters parameters = WebConnection.connect(Constants.baseUrl + Constants.AddUrls.get("QUIZ_CONTENT"),
                        arrayList, WebConnection.CONNECT_GET);
                Log.e(parameters.name, parameters.value);
                questions = new Gson().fromJson(parameters.value, Question[].class);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean success){
            if(!success){
                return;
            }
            for(int i=0;i<questions.length;i++){
                Map<String,Object> questionItem=new HashMap<>();
                questionItem.put("content",questions[i].getContent());
                questionItem.put("type",questions[i].getType());
                questionItem.put("correctAnswer",questions[i].getCorrectAnswer());
                questionItem.put("options",questions[i].getOptions());
                questionItem.put("originAnswer",originAnswers[i]);
            }

        }
    }
}
