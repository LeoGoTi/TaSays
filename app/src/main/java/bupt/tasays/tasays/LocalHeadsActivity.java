package bupt.tasays.tasays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class LocalHeadsActivity extends AppCompatActivity implements View.OnClickListener{

    int toReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_heads);
        setClicks();
    }

    @Override
    public void onClick(View view) {
                toReturn=(Integer) view.getTag();
                Intent intent=getIntent();
                intent.putExtra("new_head",toReturn);
                setResult(9,intent);
                finish();
    }
    
    //设定图片的按键和tag
    public void setClicks(){
        CircleImageView p1=findViewById(R.id.local_1);
        p1.setOnClickListener(this);
        p1.setTag(R.drawable.p1);
        CircleImageView p2=findViewById(R.id.local_2);
        p2.setOnClickListener(this);
        p2.setTag(R.drawable.p2);
        CircleImageView p3=findViewById(R.id.local_3);
        p3.setOnClickListener(this);
        p3.setTag(R.drawable.p3);
        CircleImageView p4=findViewById(R.id.local_4);
        p4.setOnClickListener(this);
        p4.setTag(R.drawable.p4);
        CircleImageView p5=findViewById(R.id.local_5);
        p5.setOnClickListener(this);
        p5.setTag(R.drawable.p5);
        CircleImageView p6=findViewById(R.id.local_6);
        p6.setOnClickListener(this);
        p6.setTag(R.drawable.p6);
        CircleImageView p7=findViewById(R.id.local_7);
        p7.setOnClickListener(this);
        p7.setTag(R.drawable.p7);
        CircleImageView p8=findViewById(R.id.local_8);
        p8.setOnClickListener(this);
        p8.setTag(R.drawable.p8);
        CircleImageView p9=findViewById(R.id.local_9);
        p9.setOnClickListener(this);
        p9.setTag(R.drawable.p9);
        CircleImageView p10=findViewById(R.id.local_10);
        p10.setOnClickListener(this);
        p10.setTag(R.drawable.p10);
        CircleImageView p11=findViewById(R.id.local_11);
        p11.setOnClickListener(this);
        p11.setTag(R.drawable.p11);
        CircleImageView p12=findViewById(R.id.local_12);
        p12.setOnClickListener(this);
        p12.setTag(R.drawable.p12);
        CircleImageView p13=findViewById(R.id.local_13);
        p13.setOnClickListener(this);
        p13.setTag(R.drawable.p13);
        CircleImageView p14=findViewById(R.id.local_14);
        p14.setOnClickListener(this);
        p14.setTag(R.drawable.p14);
        CircleImageView p15=findViewById(R.id.local_15);
        p15.setOnClickListener(this);
        p15.setTag(R.drawable.p15);
        CircleImageView p16=findViewById(R.id.local_16);
        p16.setOnClickListener(this);
        p16.setTag(R.drawable.p16);
        CircleImageView p17=findViewById(R.id.local_17);
        p17.setOnClickListener(this);
        p17.setTag(R.drawable.p17);
        CircleImageView p18=findViewById(R.id.local_18);
        p18.setOnClickListener(this);
        p18.setTag(R.drawable.p18);
        CircleImageView p19=findViewById(R.id.local_19);
        p19.setOnClickListener(this);
        p19.setTag(R.drawable.p19);
        CircleImageView p20=findViewById(R.id.local_20);
        p20.setOnClickListener(this);
        p20.setTag(R.drawable.p20);
        CircleImageView p21=findViewById(R.id.local_21);
        p21.setOnClickListener(this);
        p21.setTag(R.drawable.p21);
    }
}
