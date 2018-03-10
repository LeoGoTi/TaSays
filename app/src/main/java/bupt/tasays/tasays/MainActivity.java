package bupt.tasays.tasays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import org.json.JSONObject;

import bupt.tasays.web_sql.WebService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
   LinearLayout i1,i2,i3,i4;
   ImageView p1,p2,p3,p4;
   HomeFragment homeFragment=new HomeFragment();
   MusicFragment musicFragment=new MusicFragment();
    MoodFragment moodFragment=new MoodFragment();
    PersonalFragment personalFragment=new PersonalFragment();
    private String account;
    private JSONObject jsonObject=new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        p1=findViewById(R.id.main_home_p);
        p2=findViewById(R.id.main_music_p);
        p3=findViewById(R.id.main_mood_p);
        p4=findViewById(R.id.main_personal_p);

        i1=findViewById(R.id.main_home);
        i2=findViewById(R.id.main_music);
        i3=findViewById(R.id.main_mood);
        i4=findViewById(R.id.main_personal);

        i1.setOnClickListener(this);
        i2.setOnClickListener(this);
        i3.setOnClickListener(this);
        i4.setOnClickListener(this);
        replaceFragment(homeFragment);
        account=getIntent().getStringExtra("account");
        String json=WebService.executeGetPersonalInfo("PersonalInfoLet","account");
    }

    @Override
    public void onClick(View v)
    {
        String s=Integer.toString(v.getId());
        Log.d("MainActivity", s);
        switch (v.getId())
        {
            case R.id.main_home:
                p1.setImageResource(R.drawable.zhuye);
                p2.setImageResource(R.drawable.yinyue_1);
                p3.setImageResource(R.drawable.xinqing_1);
                p4.setImageResource(R.drawable.geren_1);
                replaceFragment(homeFragment);
                break;
            case R.id.main_music:
                p1.setImageResource(R.drawable.zhuye_1);
                p2.setImageResource(R.drawable.yinyue);
                p3.setImageResource(R.drawable.xinqing_1);
                p4.setImageResource(R.drawable.geren_1);
                //replaceFragment(musicFragment);
                break;
            case R.id.main_mood:
                p1.setImageResource(R.drawable.zhuye_1);
                p2.setImageResource(R.drawable.yinyue_1);
                p3.setImageResource(R.drawable.xinqing);
                p4.setImageResource(R.drawable.geren_1);
                replaceFragment(moodFragment);
                break;
            case R.id.main_personal:
                p1.setImageResource(R.drawable.zhuye_1);
                p2.setImageResource(R.drawable.yinyue_1);
                p3.setImageResource(R.drawable.xinqing_1);
                p4.setImageResource(R.drawable.geren);
                replaceFragment(personalFragment);
                break;

            default:
                break;

        }
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content,fragment);
        transaction.commit();
    }
}
