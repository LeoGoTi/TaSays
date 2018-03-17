package bupt.tasays.tasays;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bupt.tasays.DB_Direct.DBManager;
import bupt.tasays.list_adapter.Comment;
import bupt.tasays.list_adapter.CommentAdapter;
import bupt.tasays.web_sql.GetCommentsThread;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-12-11.
 */

public class HomeFragment extends Fragment {
    private static List<Comment> commentList = new ArrayList<>();
    View view;
    private static Boolean added = false;//主页添加完成标志
    private DBManager dbManager;
    private String sql;         //sql语句
    private GetCommentsThread getCommentsThread;
    private static Handler handler;
    static CommentAdapter adapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.main_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);
        handler=new MyHandler();
        getCommentsThread=new GetCommentsThread(handler);
        getCommentsThread.start();
        return view;
    }

    public static class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 1:
                    if(!added){
                        String tempContent, tempCommentInfo;
                        ResultSet resultSet=(ResultSet)msg.obj;
                        try {
                            for (int i = 1; i < 10; i++) {
                                resultSet.absolute(i);
                                tempContent = resultSet.getString("content");
                                tempCommentInfo = "在 "+resultSet.getString("singername")+"-"+resultSet.getString("songname")+" 后的热评";
                                commentList.add(new Comment(tempContent, "T@", tempCommentInfo));
                            }
                            resultSet.absolute(11);
                            tempContent = resultSet.getString("content");
                            tempCommentInfo = "在 "+resultSet.getString("singername")+"-"+resultSet.getString("songname")+" 后的热评";
                            commentList.add(new Comment(tempContent, "T@", tempCommentInfo+"\n\n\n\n\n\n"));
                            adapter.notifyDataSetChanged();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        added = !added;
                    }
                    break;
                case 0:
                    break;
            }
        }
    }


}
