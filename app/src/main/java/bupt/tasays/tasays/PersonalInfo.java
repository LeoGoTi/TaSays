package bupt.tasays.tasays;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by root on 17-12-5.
 */

public class PersonalInfo extends Fragment
{

    private TextView contact,clean,about;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.personal_info, container, false);
        contact =view.findViewById(R.id.contact);
        clean=view.findViewById(R.id.clean);
        about=view.findViewById(R.id.about);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"联系我们",Toast.LENGTH_SHORT).show();
            }
        });
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"清除缓存",Toast.LENGTH_SHORT).show();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"项目组长：\n张欣悦\n\n组员：\n郭聚川\n马祁\n刘泳君\n刘国维",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
