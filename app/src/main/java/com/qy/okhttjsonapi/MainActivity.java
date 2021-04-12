package com.qy.okhttjsonapi;
import android.app.VoiceInteractor;
import android.net.nsd.NsdManager;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private static final int GET = 1;
    private Button btn_okhttp;
    private TextView tv_okhttp;


    private OkHttpClient client = new OkHttpClient();


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET:
                    tv_okhttp.setText((String) msg.obj);
                    break;

            }



        }

//        public  void  handleMessage(Message message){
//
//            super.handleMessage(message);
//            switch ( message.what){
//                case  GET:
//                    tv_okhttp.setText((String) message.obj);
//break;
//            }
      //  }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_okhttp = (Button) findViewById(R.id.btn_okhttp);
        tv_okhttp = (TextView) findViewById(R.id.tv_okhttp);

        btn_okhttp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_okhttp:
                getDataFromGet();
                break;
        }
    }

    private void getDataFromGet() {
        new Thread() {
            @Override
            public void run() {
                //super.run();
                try {
                    String result = get("http://apis.juhe.cn/simpleWeather/wids?key=c94e3d2e46b171d4a04526fdb0511cc1");

                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject results = jsonObject.getJSONObject("result");
                    String android= results.getString("win");

                   // Log.e("ZHUTI",android);
                    Log.e("Jiexi", android);
                    Message msg = Message.obtain();
                    msg.what = GET;
                    msg.obj = android;
                    handler.sendMessage(msg);
                } catch (IOException e)
                {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }



   /* private void  getDataFromGet(){
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = get("https://gank.io/api/today");
                          JSONObject jsonObject = new JSONObject(result);

                          JSONObject results = jsonObject.getJSONObject("results");

                          String android= results.getString("Android");

                          Log.e("ZHUTI", android);
                          Message msg = Message.obtain();
                          msg.what = GET;
                          msg.obj = android;
                          handler.sendMessage(msg);

                      }catch (IOException e){
                          e.printStackTrace();}
//                      } catch (IOException e){
//                          e.printStackTrace();
//                      }
                  }
        }.start();

    } */
    private  String get(String url) throws IOException{

        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return  response.body().string();
        //

    }

}