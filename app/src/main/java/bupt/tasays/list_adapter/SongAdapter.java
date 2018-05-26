package bupt.tasays.list_adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bupt.tasays.tasays.MainActivity;
import bupt.tasays.tasays.R;
import bupt.tasays.tasays.WebActivity;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>{
    private List<Song> songList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView songSongname;
        TextView songSingername;
        TextView songAlbum;
        ImageView listen;
        ImageView share;
        public ViewHolder(View view){
            super(view);
            songSongname=view.findViewById(R.id.song_item_songname);
            songSingername=view.findViewById(R.id.song_item_singername);
            songAlbum=view.findViewById(R.id.song_item_album);
            listen=view.findViewById(R.id.song_item_listen);
            share=view.findViewById(R.id.song_item_share);
        }


    }
    public SongAdapter(List<Song> songList){
        this.songList=songList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Song song = songList.get(position);
        holder.songAlbum.setText(song.album);
        holder.songSingername.setText(song.singername);
        holder.songSongname.setText(song.songname);
        holder.listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.listen.getContext(), WebActivity.class);
                intent.putExtra("destUrl",song.url);
                holder.listen.getContext().startActivity(intent);
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT,song.url);
                holder.share.getContext().startActivity(Intent.createChooser(intent,"TaSays"));
            }
        });
    }

    @Override
    public int getItemCount(){
        return songList.size();
    }
}
