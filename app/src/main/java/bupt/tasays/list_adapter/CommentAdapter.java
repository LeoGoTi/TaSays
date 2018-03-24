package bupt.tasays.list_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


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
        ImageView love;
        ImageView share;
        boolean loved=false;

        public ViewHolder(View view)
        {
            super(view);
            commentContent=(TextView)view.findViewById(R.id.comment_item_content);
            commentUser=(TextView)view.findViewById(R.id.comment_item_nickname);
            commentSong=(TextView)view.findViewById(R.id.comment_item_songinfo);
            love=view.findViewById(R.id.comment_item_love);
            share=view.findViewById(R.id.comment_item_share);

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
                .inflate(R.layout.comment_item,parent,false);
        final ViewHolder holder= new ViewHolder(view);
        holder.love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!holder.loved){
                    holder.love.setImageResource(R.drawable.loved);
                    holder.loved=true;
                }
                else {
                    holder.love.setImageResource(R.drawable.love);
                    holder.loved=false;
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
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
