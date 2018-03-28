package bupt.tasays.tasays;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import bupt.tasays.list_adapter.AdapterViewpager;
import bupt.tasays.list_adapter.Comment;
import bupt.tasays.list_adapter.CommentAdapter;
import bupt.tasays.web_sql.GetCommentsThread;
import me.relex.circleindicator.CircleIndicator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by root on 17-12-11.
 */

public class HomeFragment extends Fragment{
    private static List<Comment> commentList = new ArrayList<>();
    private static List<View> viewList=new ArrayList<>();
    View view;
    private static Boolean added = false;//主页添加完成标志
    private GetCommentsThread getCommentsThread;
    private static Handler handler;
    static CommentAdapter adapter;
    static AdapterViewpager adapterViewpager;
    private FrameLayout search;
    MainActivity mainActivity;
    private static String type=null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_layout, container, false);
        type=null;
        mainActivity=(MainActivity)getActivity();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.main_recycler);
        FrameLayout frameLayout=view.findViewById(R.id.main_home_frame);
        final EditText editText=view.findViewById(R.id.search_home);
        search=view.findViewById(R.id.search_home_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"搜索一哈",Toast.LENGTH_SHORT).show();
            }
        });
        frameLayout.setFocusable(true);
        frameLayout.setFocusableInTouchMode(true);
        final ViewPager viewPager=view.findViewById(R.id.view_pager);
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
        adapterViewpager.setHandler(handler);
        try {
            getCommentsThread = new GetCommentsThread(handler, mainActivity.getPersonalInt("userid"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        getCommentsThread.start();

        return view;
    }

    public static class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 1:
                    if(!added){
                        String tempContent, tempCommentInfo,tempUrl;
                        int tempContentid;
                        boolean tempIsLiked;
                        ResultSet resultSet=(ResultSet)msg.obj;
                        try {
                            for (int i = 1; i < 20; i++) {
                                resultSet.absolute(i);
                                tempContent = resultSet.getString("content");
                                tempUrl = resultSet.getString("url");
                                tempContentid=resultSet.getInt("contentid");
                                tempIsLiked=resultSet.getBoolean("isliked");
                                tempCommentInfo = "在 "+resultSet.getString("singername")+"-"+resultSet.getString("songname")+" 后的热评";
                                commentList.add(new Comment(tempContent, "T@", tempCommentInfo,tempUrl,tempContentid,tempIsLiked));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        added = !added;
                    }
                    break;
                case 0:
                    break;
                case 4:
                    type=(String)msg.obj;
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

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(type==null);
                while(mainActivity==null);
                Intent intent=new Intent(mainActivity,SpecialActivity.class);
                intent.putExtra("type",type);
                try {
                    intent.putExtra("userid", mainActivity.getPersonalInt("userid"));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                type=null;
                startActivity(intent);
            }
        }).start();
    }
}
