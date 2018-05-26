package bupt.tasays.tasays;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bupt.tasays.list_adapter.Song;
import bupt.tasays.list_adapter.SongAdapter;
import bupt.tasays.web_sql.GetSongsThread;

public class SongsActivity extends AppCompatActivity {
    private List<Song> songList = new ArrayList<>();
    private int count;
    private int[] res = new int[100];
    private String back_songs;
    private String search;
    private RecyclerView recyclerView;
    private ImageView imageView;
    static SongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent from = getIntent();
        back_songs = from.getStringExtra("back_songs");
        search=from.getStringExtra("search");
        this.count = stringToArray(back_songs);
        setContentView(R.layout.songs_layout);
        TextView textView=findViewById(R.id.songs_search);
        imageView=findViewById(R.id.song_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//直接销毁
            }
        });
        textView.setText(search);
        recyclerView = findViewById(R.id.songs_frame_recycler);
//        Toast.makeText(this,back_songs,Toast.LENGTH_SHORT).show();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SongAdapter(songList);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        //子线程更新音乐区
        Handler handler = new MyHandler();
        new Thread(new GetSongsThread(handler,res,count)).start();
    }

    public int stringToArray(String back)//返回个数
    {
        int count = 0;
        int i = 0;
        String[] arrayStr = back.split(",");
        while (i < arrayStr.length) {
            res[i] = Integer.parseInt(arrayStr[i]);
            i++;
            count++;
        }
        return count;
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://成功
                    String singername,songname,album,url;
                    int songid,singerid;
                    ResultSet resultSet=(ResultSet)msg.obj;
                    try {
                        for(int i=1;i<count+1;i++){
                            resultSet.absolute(i);
                            singerid=resultSet.getInt("singerid");
                            songid=resultSet.getInt("songid");
                            singername=resultSet.getString("singername");
                            songname=resultSet.getString("songname");
                            album=resultSet.getString("album");
                            url=resultSet.getString("url");
                            songList.add(new Song(singerid,singername,songid,songname,album,url));
                        }
                        adapter.notifyDataSetChanged();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
