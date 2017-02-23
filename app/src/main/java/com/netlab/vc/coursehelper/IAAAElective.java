package com.netlab.vc.coursehelper;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class IAAAElective extends AppCompatActivity {

    class Course{
        String name;
        boolean full;
    }
    ListView listView;
    //TextView textView;
    String cookie;
    RefreshCourseTask refreshCourseTask;
    Timer timer = new Timer();
    SoundPool soundPool;
    Course[] courses;
    String courseNameRegex="(?<=refreshLimit\\(')\\w+";
    String courseNameRegexSelect="(?<=confirmSelect\\(')\\w+";
    String courseIdRegex="(?<='',')\\w+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iaaaelective);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //textView=(TextView)findViewById(R.id.web_content);
        //textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        setSupportActionBar(toolbar);
        cookie=getIntent().getStringExtra("cookie");
        refreshCourseTask=new RefreshCourseTask();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        soundPool= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);
        soundPool.load(this,R.raw.beep,1);
        courses=new Course[50];
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        timer.schedule(task, 1000, 15000);       // timeTask
        //new RefreshCourseTask().execute(";0");


    }
    TimerTask task = new TimerTask() {
        int a=0;
        String page[]={";0",";20"};
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    new RefreshCourseTask().execute(page[a]);
                    a=1-a;
                }
            });
        }
    };
    private class RefreshCourseTask extends AsyncTask<String,Void,Boolean>{

        String content;
        String page[]={";0",";20"};
        int p=0;
        @Override
        protected Boolean doInBackground(String... params) {
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 4000);
            HttpConnectionParams.setSoTimeout(httpParams, 13000);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
            HttpGet httpGet = new HttpGet("http://elective.pku.edu.cn/elective2008/edu/pku/stu/elective/controller/supplement/SupplyCancel.do?netui_row=electableListGrid"+params[p]);
            httpGet.setHeader("Referer", "http://elective.pku.edu.cn/elective2008/edu/pku/stu/elective/controller/supplement/SupplyCancel.do");
            httpGet.setHeader("Cookie", cookie);
            Log.e("123",params[0]);
            try {
                HttpResponse httpResponse=httpClient.execute(httpGet);
                BufferedReader bf;

                bf = new BufferedReader(
                        new InputStreamReader(httpResponse.getEntity().getContent()));

                String line = bf.readLine();
                while (line != null) {
                    content = content + line + "\n";
                    line = bf.readLine();
                }
                //Log.e("123",content);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        @Override
        public void onPostExecute(Boolean success){
            if(!success){
                Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

                vibrator.vibrate(new long[]{300,500},0);
                return;
            }
            /*
            try {
                Parser parser=new Parser();
                parser.setInputHTML(content);

                NodeFilter filter1 = new HasAttributeFilter("href");
                NodeFilter filter2=new HasAttributeFilter("style","width: 30");
                NodeFilter filter = new AndFilter(filter1,filter2);
                NodeList nodeList = parser.extractAllNodesThatMatch(filter);
                for(int i = 0; i<nodeList.size();i++) {
                    Node node = nodeList.elementAt(i);
                    Log.e("node",node.getText());
                    String line=node.getText();
                    Pattern courseNamePattern=Pattern.compile(courseNameRegex);
                    Pattern courseNamePatternSelect=Pattern.compile(courseNameRegexSelect);
                    Pattern courseIdPattern=Pattern.compile(courseIdRegex);
                    Matcher courseNameMat = courseNamePattern.matcher(line);
                    Matcher courseNameSelectMat=courseNamePatternSelect.matcher(line);
                    Matcher courseIdMat=courseIdPattern.matcher(line);
                    if(courseNameMat.find()){
                        Log.e("FULL",courseNameMat.group()+courseIdMat.group());
                    }
                    else{
                        Log.e("Select",courseNameSelectMat.group()+courseIdMat.group());
                    }
                }
            } catch (ParserException e) {
                e.printStackTrace();
            }
            */
            //textView.setText(content);
            if(content.contains("28 / 27")||content.contains("22 / 21")||content.contains("30 / 29")
                    ||content.contains("480 / 479")||content.contains("480 / 478")||content.contains("刷课机")
                    ||content.contains("重新登录")||content.contains("480 / 477")
                    ||content.contains("480 / 476")||content.contains("30 / 28")){
                Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

                vibrator.vibrate(new long[]{300,500},0);
                soundPool.play(1,1, 1, 0, -1, 1);
            }

        }
    }
}
