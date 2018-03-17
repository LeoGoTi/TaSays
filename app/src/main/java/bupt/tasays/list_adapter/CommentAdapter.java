package bupt.tasays.list_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import bupt.tasays.tasays.R;

/**
 * Created by root on 17-12-12.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> mCommentList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView commentContent;
        TextView commentUser;
        TextView commentSong;

        public ViewHolder(View view)
        {
            super(view);
            commentContent=(TextView)view.findViewById(R.id.main_item_comment);
            commentUser=(TextView)view.findViewById(R.id.comment_id);
            commentSong=(TextView)view.findViewById(R.id.song_name);
        }
    }

    public CommentAdapter(List<Comment> commentList)
    {
        mCommentList=commentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewtype)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_item,parent,false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        Comment comment=mCommentList.get(position);
        holder.commentContent.setText(comment.getComment());
        holder.commentUser.setText(comment.getUser_id());
        holder.commentSong.setText(comment.getSong());
    }

    @Override
    public int getItemCount()
    {
        return mCommentList.size();
    }
}
