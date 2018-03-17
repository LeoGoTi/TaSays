package bupt.tasays.list_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bupt.tasays.tasays.R;

/**
 * Created by root on 18-3-17.
 */

public class MoodLineAdapter extends RecyclerView.Adapter<MoodLineAdapter.ViewHolder>{
    private List<MoodLine> mMoodLineList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView moodTime;
        TextView moodContent;
        ImageView moodMood;

        public ViewHolder(View view){
            super(view);
            moodTime=view.findViewById(R.id.mood_item_time);
            moodContent=view.findViewById(R.id.mood_item_content);
            moodMood=view.findViewById(R.id.mood_item_mood);
        }
    }

    public MoodLineAdapter(List<MoodLine> moodLineList){
        mMoodLineList=moodLineList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewtype)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mood_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MoodLine moodLine=mMoodLineList.get(position);
        holder.moodMood.setImageResource(R.drawable.happy); //需要适配
        holder.moodContent.setText(moodLine.getContent());
        holder.moodTime.setText(moodLine.getTime());
    }



    @Override
    public int getItemCount(){
        return mMoodLineList.size();
    }
}
