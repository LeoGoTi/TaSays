package bupt.tasays.tasays;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by root on 17-12-5.
 */

public class PersonalEdit extends Fragment
{
    private TextView constellation;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.personal_edit, container, false);
        constellation=view.findViewById(R.id.constellation);
        constellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),TimePickerTest.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
