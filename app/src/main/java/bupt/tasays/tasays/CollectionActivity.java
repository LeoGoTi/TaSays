package bupt.tasays.tasays;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bupt.tasays.list_adapter.Comment;
import bupt.tasays.list_adapter.CommentAdapter;
import bupt.tasays.web_sql.GetCommentsThread;

public class CollectionActivity extends AppCompatActivity {
    private static List<Comment> commentList = new ArrayList<>();
    private Handler handler;
    private int userid;
    private static CommentAdapter adapter;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_layout);
        Intent intent=getIntent();
        userid = intent.getIntExtra("userid",-233);//随便一个不存在的数字
        recyclerView=findViewById(R.id.collection_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new CommentAdapter(commentList,true);
        recyclerView.setAdapter(adapter);
        handler=new MyHandler();
        GetCommentsThread getCommentsThread=new GetCommentsThread(handler,userid,true);
        getCommentsThread.start();
    }

    public static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String tempContent, tempCommentInfo, tempUrl;
                    int tempContentid;
                    ResultSet resultSet = (ResultSet) msg.obj;
                    try {
                        resultSet.absolute(1);
                        do {
                            tempContent = resultSet.getString("content");
                            tempUrl = resultSet.getString("url");
                            tempContentid = resultSet.getInt("contentid");
                            tempCommentInfo = "在 " + resultSet.getString("singername") + "-" + resultSet.getString("songname") + " 后的热评";
                            commentList.add(new Comment(tempContent, "T@", tempCommentInfo, tempUrl, tempContentid, true));
                        } while (resultSet.next());
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
}
