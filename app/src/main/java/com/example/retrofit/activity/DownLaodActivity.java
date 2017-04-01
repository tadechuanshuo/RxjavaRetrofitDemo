package com.example.retrofit.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.retrofit.R;
import com.example.retrofit.activity.adapter.DownAdapter;
import com.example.retrofit.downlaod.DownInfo;
import com.example.retrofit.downlaod.HttpDownManager;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 多任務下載
 */
public class DownLaodActivity extends AppCompatActivity {
    List<DownInfo> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_laod);
        initResource();
        initWidget();
    }

    /*数据*/
    private void initResource(){
        listData=new ArrayList<>();
        String[] downUrl=new String[]{"http://cdn.ali.vcinema.com.cn/newEncode201609/267f33a134760ebfbc9f4513cdea9940/267f33a134760ebfbc9f4513cdea9940_D_L_E.mp4",
                "http://cdn.ali.vcinema.com.cn/newEncode201703/9a7d320c50f3838ee5560475b1321e70/9a7d320c50f3838ee5560475b1321e70_D_L_E.mp4"};
        for (int i = 0; i < downUrl.length; i++) {
//            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
//                    "test"+i + ".jpg");
            File sdCardDir = getFilesDir();
            File fileDir = new File(sdCardDir, "downloadgg");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            String filename = "test"+i+".mp4";
            File outputFile = new File(fileDir, filename);
            DownInfo apkApi=new DownInfo(downUrl[i]);
            apkApi.setSavePath(outputFile.getAbsolutePath());
            listData.add(apkApi);
        }
    }

    /*加载控件*/
    private void initWidget(){
        EasyRecyclerView recyclerView=(EasyRecyclerView)findViewById(R.id.rv);
        DownAdapter adapter=new DownAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.addAll(listData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*停止全部下载*/
        HttpDownManager.getInstance().stopAllDown();
    }
}
