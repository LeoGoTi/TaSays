package bupt.tasays.tasays;

import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bupt.tasays.list_adapter.MoodLine;
import bupt.tasays.list_adapter.MoodLineAdapter;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;


/**
 * Created by root on 17-12-5.
 */

public class MoodFragment extends Fragment {
    private static List<MoodLine> moodLineList=new ArrayList<>();
    static MoodLineAdapter adapter;
    private int added=0;
    FloatingActionButton floatingActionButton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.mood_layout, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.mood_recycler);
        floatingActionButton=view.findViewById(R.id.mood_write);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MoodLineAdapter(moodLineList);
        recyclerView.setAdapter(adapter);
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
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        moodLineList.add(new MoodLine(R.drawable.happy,month+"月"+day+"日",editText.getText().toString()+"\n"));
                    }
                });
        builder.show();
    }
}
