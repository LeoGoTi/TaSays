package bupt.tasays.tasays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
   LinearLayout i1,i2,i3;
   ImageView p1,p2,p3;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        p1=findViewById(R.id.main_music_p);
        p2=findViewById(R.id.main_home_p);
        p3=findViewById(R.id.main_personal_p);

        i1=findViewById(R.id.main_music);
        i1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                p1.setImageResource(R.drawable.yinyue);
                p2.setImageResource(R.drawable.zhuye_1);
                p3.setImageResource(R.drawable.geren_1);
            }
        });
        i2=findViewById(R.id.main_home);
        i2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                p1.setImageResource(R.drawable.yinyue_1);
                p2.setImageResource(R.drawable.zhuye);
                p3.setImageResource(R.drawable.geren_1);
            }
        });
        i3=findViewById(R.id.main_personal);
        i3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                p1.setImageResource(R.drawable.yinyue_1);
                p2.setImageResource(R.drawable.zhuye_1);
                p3.setImageResource(R.drawable.geren);
            }
        });
    }
}
