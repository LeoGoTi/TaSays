package bupt.tasays.list_adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bupt.tasays.tasays.R;
import bupt.tasays.web_sql.DeletePersonalCommentThread;

/**
 * Created by root on 18-3-17.
 */

public class MoodLineAdapter extends RecyclerView.Adapter<MoodLineAdapter.ViewHolder>{
    private List<MoodLine> mMoodLineList;
    private Handler handler;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView moodTime;
        TextView moodContent;
        ImageView moodMood;
        ImageView moodTrash;

        public ViewHolder(View view){
            super(view);
            moodTime=view.findViewById(R.id.mood_item_time);
            moodContent=view.findViewById(R.id.mood_item_content);
            moodMood=view.findViewById(R.id.mood_item_mood);
            moodTrash=view.findViewById(R.id.mood_item_trash);
        }
    }

    public MoodLineAdapter(List<MoodLine> moodLineList,Handler handler){
        mMoodLineList=moodLineList;
        this.handler=handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewtype)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mood_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MoodLine moodLine=mMoodLineList.get(position);
        holder.moodMood.setImageResource(moodLine.getMoodSrc()); //需要适配
        holder.moodContent.setText(moodLine.getContent());
        holder.moodTime.setText(moodLine.getTime());
        holder.moodTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(holder.moodTrash.getContext());
                dialog.setTitle("删除心情记录")
                        .setMessage("确定要删除 \""+moodLine.getContent()+"\" 这条心情吗?")
                        .setCancelable(true)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Thread thread=new DeletePersonalCommentThread(handler,moodLine.getContentid());
                                thread.start();
                            }
                        })
                        .show();
            }
        });
    }



    @Override
    public int getItemCount(){
        return mMoodLineList.size();
    }
}
