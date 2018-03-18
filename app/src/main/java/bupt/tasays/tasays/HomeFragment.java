package bupt.tasays.tasays;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import bupt.tasays.DB_Direct.DBManager;
import bupt.tasays.list_adapter.AdapterViewpager;
import bupt.tasays.list_adapter.Comment;
import bupt.tasays.list_adapter.CommentAdapter;
import bupt.tasays.web_sql.GetCommentsThread;
import me.relex.circleindicator.CircleIndicator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 17-12-11.
 */

public class HomeFragment extends Fragment {
    private static List<Comment> commentList = new ArrayList<>();
    private static List<View> viewList=new ArrayList<>();
    View view;
    private static Boolean added = false;//主页添加完成标志
    private GetCommentsThread getCommentsThread;
    private static Handler handler;
    static CommentAdapter adapter;
    static AdapterViewpager adapterViewpager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.main_recycler);
        ViewPager viewPager=view.findViewById(R.id.view_pager);
        CircleIndicator circleIndicator=view.findViewById(R.id.indicator);
        adapterViewpager=new AdapterViewpager(viewList);

        if(!added)addAd();
        viewPager.setAdapter(adapterViewpager);
        circleIndicator.setViewPager(viewPager);

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

    public void addAd(){
        View item1=getLayoutInflater().from(getActivity()).inflate(R.layout.image_item,null);
        viewList.add(item1);
        ImageView imageView1=item1.findViewById(R.id.image_item1);
        imageView1.setImageResource(R.drawable.ada);
        View item2=getLayoutInflater().from(getActivity()).inflate(R.layout.image_item,null);
        viewList.add(item2);
        ImageView imageView2=item2.findViewById(R.id.image_item1);
        imageView2.setImageResource(R.drawable.adb);
        View item3=getLayoutInflater().from(getActivity()).inflate(R.layout.image_item,null);
        viewList.add(item3);
        ImageView imageView3=item3.findViewById(R.id.image_item1);
        imageView3.setImageResource(R.drawable.adc);
        View item4=getLayoutInflater().from(getActivity()).inflate(R.layout.image_item,null);
        viewList.add(item4);
        ImageView imageView4=item4.findViewById(R.id.image_item1);
        imageView4.setImageResource(R.drawable.add);
    }

}
