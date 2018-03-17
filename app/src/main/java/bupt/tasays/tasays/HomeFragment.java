package bupt.tasays.tasays;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bupt.tasays.DB_Direct.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-12-11.
 */

public class HomeFragment extends Fragment
{
    private List<Comment> commentList=new ArrayList<>();
    private boolean added=false;
    private DBManager dbManager;
    private String sql;         //sql语句

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        initComments();
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.main_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        CommentAdapter adapter=new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initComments()
    {

//        if(!added) {
//            commentList.add(new Comment("想给他吃颗糖\n" +
//                    "于是买了一大把糖\n" +
//                    "大费周章得每个人都给一颗\n" +
//                    "只剩最后一颗的时候\n" +
//                    "才漫不经心的冲他摊开手：\n" +
//                    "喂，你吃不吃？",
//                    "T@原来是奤奤啊",
//                    "在 小半-陈粒 后的热评"));
//            commentList.add(new Comment("若生命如过场电影\n让我再一次甜梦里惊醒",
//                    "T@拓海叔叔",
//                    "在 云烟成雨-房东的猫 后的热评"));
//            commentList.add(new Comment("正是我现在的状态\n" +
//                    "浑浑噩噩度过每一天\n" +
//                    "不知今夕何夕\n" +
//                    "不知身在何方\n" +
//                    "后路渐远\n" +
//                    "前路亦遥不可期\n" +
//                    "这种失落会持久吗\n" +
//                    "我的世界大概不会好了",
//                    "T@三岛小丸君", "在 如斯-丢火车 后的热评"));
//            added=!added;
//        }
        if(!added)
        {
            new Thread(new addCommentsThread()).start();
        }
    }

    public class addCommentsThread implements Runnable{
        @Override
        public void run(){
            String tempContent,tempSongId;
            sql="select * from comments";
            dbManager=DBManager.createInstance();
            dbManager.connectDB();
            ResultSet resultSet=dbManager.executeQuery(sql);
            try {
                for (int i = 1; i < 10; i++) {
                    resultSet.absolute(i);
                    tempContent=resultSet.getString("content");
                    tempSongId=resultSet.getString("songid");
                    commentList.add(new Comment(tempContent,"T@",tempSongId));
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            added=!added;
        }
    }

}
