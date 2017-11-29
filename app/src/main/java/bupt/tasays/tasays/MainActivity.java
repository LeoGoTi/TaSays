package bupt.tasays.tasays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
   ImageView i1,i2,i3;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        i1=findViewById(R.id.main_cd);
        i1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                i1.setImageResource(R.drawable.cd);
                i2.setImageResource(R.drawable.laba_1);
                i3.setImageResource(R.drawable.fangzi_1);
            }
        });
        i2=findViewById(R.id.main_laba);
        i2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                i1.setImageResource(R.drawable.cd_1);
                i2.setImageResource(R.drawable.laba);
                i3.setImageResource(R.drawable.fangzi_1);
            }
        });
        i3=findViewById(R.id.main_house);
        i3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                i1.setImageResource(R.drawable.cd_1);
                i2.setImageResource(R.drawable.laba_1);
                i3.setImageResource(R.drawable.fangzi);
            }
        });
    }
}
