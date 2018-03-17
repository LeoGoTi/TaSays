package bupt.tasays.tasays;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bupt.tasays.list_adapter.MoodLine;
import bupt.tasays.list_adapter.MoodLineAdapter;


/**
 * Created by root on 17-12-5.
 */

public class MoodFragment extends Fragment {
    private static List<MoodLine> moodLineList=new ArrayList<>();
    static MoodLineAdapter adapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.mood_layout, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.mood_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MoodLineAdapter(moodLineList);
        recyclerView.setAdapter(adapter);
        moodLineList.add(new MoodLine(R.drawable.happy,"12月7日","单曲循环一整天\n"));
        moodLineList.add(new MoodLine(R.drawable.happy,"12月7日","单曲循环一整天\n"));
        moodLineList.add(new MoodLine(R.drawable.happy,"12月7日","单曲循环一整天\n"));
        moodLineList.add(new MoodLine(R.drawable.happy,"12月7日","单曲循环一整天\n"));
        return view;
    }

}
