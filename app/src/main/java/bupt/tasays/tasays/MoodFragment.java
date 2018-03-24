package bupt.tasays.tasays;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bupt.tasays.list_adapter.MoodLine;
import bupt.tasays.list_adapter.MoodLineAdapter;
import bupt.tasays.web_sql.GetPrivateCommentsThread;
import bupt.tasays.web_sql.PostCommentThread;


/**
 * Created by root on 17-12-5.
 */

public class MoodFragment extends Fragment {
    private static List<MoodLine> moodLineList=new ArrayList<>(50);
    private static List<MoodLine> moodLineListTemp=new ArrayList<>(50);
    static MoodLineAdapter adapter;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    MainActivity mainActivity;
    private static Handler handler;
    private GetPrivateCommentsThread getPrivateCommentsThread;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.mood_layout, container, false);
        recyclerView=view.findViewById(R.id.mood_recycler);
        floatingActionButton=view.findViewById(R.id.mood_write);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        mainActivity=(MainActivity)getActivity();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MoodLineAdapter(moodLineList);
        recyclerView.setAdapter(adapter);
        handler=new MyHandler();
        try {
            //开始从服务器载入之前清除已存在的
            moodLineList.clear();
            getPrivateCommentsThread = new GetPrivateCommentsThread(handler, mainActivity.getPersonalString("account"));
            getPrivateCommentsThread.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        /*
        加载静态数据用的
        do{
            moodLineList.add(new MoodLine(R.drawable.happy,"12月7日","单曲循环一整天\n"));
            added++;
        }while(added<10);
        */

        return view;
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.mood_write, null);//获取自定义布局
        builder.setView(layout);
        //final AlertDialog dlg = builder.create();
        builder.setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        layout.findViewById(R.id.mood_write_edit);
                    }
                })
                .setPositiveButton("写入", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editText=layout.findViewById(R.id.mood_write_edit);
                        Calendar calendar = Calendar.getInstance();
                        int month = calendar.get(Calendar.MONTH)+1;
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        moodLineListTemp.clear();
                        moodLineListTemp.addAll(moodLineList);
                        moodLineList.clear();
                        moodLineList.add(new MoodLine(R.drawable.happy,month+"月"+day+"日",editText.getText().toString()+"\n"));
                        moodLineList.addAll(moodLineListTemp);
                        //adapter=new MoodLineAdapter(moodLineList);
                        //recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        try {
                            new Thread(new PostCommentThread(mainActivity.getPersonalString("account"),editText.getText().toString(),"好" )).start();
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
        builder.show();
    }

    public static class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    try{
                        ResultSet resultSet=(ResultSet)msg.obj;
                        String tempContent,tempClass2;
                        String tempTime;
                        while(resultSet.next()){
                            tempContent=resultSet.getString("content");
                            tempTime=resultSet.getString("time");
                            tempClass2=resultSet.getString("class2");
                            String str=tempTime.substring(4,8);
                            moodLineList.add(new MoodLine(R.drawable.happy,str.substring(0,2)+"月"+str.substring(2,4)+"日",tempContent));
                            adapter.notifyDataSetChanged();

                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 0:break;
            }
        }
    }
}
