package bupt.tasays.list_adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ldoublem.thumbUplib.ThumbUpView;

import java.util.List;

import bupt.tasays.tasays.MainActivity;
import bupt.tasays.tasays.R;
import bupt.tasays.tasays.WebActivity;
import bupt.tasays.web_sql.CollectionThread;

/**
 * Created by root on 17-12-12.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> mCommentList;
    private MainActivity mainActivity;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView commentContent;
        TextView commentUser;
        TextView commentSong;
        ThumbUpView love;
        ImageView share;
        ImageView listen;

        public ViewHolder(View view)
        {
            super(view);
            commentContent=(TextView)view.findViewById(R.id.comment_item_content);
            commentUser=(TextView)view.findViewById(R.id.comment_item_nickname);
            commentSong=(TextView)view.findViewById(R.id.comment_item_songinfo);
            love=view.findViewById(R.id.comment_item_love);
            share=view.findViewById(R.id.comment_item_share);
            listen=view.findViewById(R.id.comment_item_listen);
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
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        final Comment comment=mCommentList.get(position);
        if(mainActivity==null)
            mainActivity=(MainActivity) holder.love.getContext();
        holder.commentContent.setText(comment.getComment());
        holder.commentUser.setText(comment.getUser_id());
        holder.commentSong.setText(comment.getSong());
        holder.listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.listen.getContext(), WebActivity.class);
                intent.putExtra("destUrl",comment.getUrl());
                holder.listen.getContext().startActivity(intent);
            }
        });
        //读取并设置已收藏的状态
        if(comment.getIsLiked()){
            holder.love.setLike();
        }
        holder.love.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
                    Toast.makeText(holder.love.getContext(),like?"收藏成功":"取消收藏",Toast.LENGTH_SHORT).show();
                    try{
                        int userid=mainActivity.getPersonalInt("userid");
                        new Thread(new CollectionThread(comment.getContentid(),userid,like)).start();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"分享一下",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mCommentList.size();
    }


}
