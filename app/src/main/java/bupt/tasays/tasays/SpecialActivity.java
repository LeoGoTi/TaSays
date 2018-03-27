package bupt.tasays.tasays;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bupt.tasays.list_adapter.Comment;
import bupt.tasays.list_adapter.CommentAdapter;
import bupt.tasays.web_sql.GetCommentsThread;

public class SpecialActivity extends AppCompatActivity {
    private static List<Comment> commentList = new ArrayList<>();
    private String type;
    private ImageView specialImage;
    private RecyclerView recyclerView;
    private TextView textView;
    private static CommentAdapter adapter;
    private Handler handler;
    private GetCommentsThread getCommentsThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_layout);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");//获取种类
        specialImage=findViewById(R.id.special_image);
        recyclerView=findViewById(R.id.special_recycler);
        textView=findViewById(R.id.special_text);
        //加载图片
        switch (type){
            case "初恋":
                specialImage.setImageResource(R.drawable.ada);
                textView.setText(getString(R.string.ad_first_love));
                break;
            case "毕业":
                specialImage.setImageResource(R.drawable.adb);
                textView.setText(getString(R.string.ad_graduation));
                break;
            case "台湾偶像剧":
                specialImage.setImageResource(R.drawable.adc);
                textView.setText(getString(R.string.ad_soap));
                break;
            case "旅游":
                specialImage.setImageResource(R.drawable.add);
                textView.setText(getString(R.string.ad_travel));
                break;
            default:
                break;
        }
        //加载文字


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);
        handler=new MyHandler();
        getCommentsThread=new GetCommentsThread(handler,type);
        getCommentsThread.start();
    }

    public static class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 1:
                        String tempContent, tempCommentInfo;
                        ResultSet resultSet=(ResultSet)msg.obj;
                        try {
                            resultSet.absolute(1);
                            do {
                                tempContent = resultSet.getString("content");
                                tempCommentInfo = "在 " + resultSet.getString("singername") + "-" + resultSet.getString("songname") + " 后的热评";
                                commentList.add(new Comment(tempContent, "T@", tempCommentInfo));
                            }while(resultSet.next());
                            adapter.notifyDataSetChanged();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    break;
                case 0:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
