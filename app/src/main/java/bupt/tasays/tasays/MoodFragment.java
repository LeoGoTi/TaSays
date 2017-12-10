package bupt.tasays.tasays;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by root on 17-12-5.
 */

public class MoodFragment extends Fragment
{
    CardView cardView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.mood_layout, container, false);
        cardView=view.findViewById(R.id.musicplay);
        if (cardView!=null)
            Log.d("MoodFragment", "onCreate: cardView is not null");
        else
            Log.d("MoodFragment", "onCreate: cardView is null");
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),WebActivity.class);
                String url="http://music.163.com/song/518077040?userid=340961567";
                intent.putExtra("destUrl",url);
                Log.d("MainActivity", "onClick: going to Walk on Water");
                startActivity(intent);
            }
        });
        return view;
    }
}
