package bupt.tasays.tasays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;


import bupt.tasays.web_sql.WebService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout i1, i3, i4;
    ImageView p1, p3, p4;
    HomeFragment homeFragment = new HomeFragment();
    MoodFragment moodFragment = new MoodFragment();
    PersonalFragment personalFragment = new PersonalFragment();
    private String account;
    private JSONObject jsonObject;
    //个人信息
    private String nickname="设置用户名", introduction="无", phonenum="未设置";
    private String gender="1";
    private String constellation = "摩羯座";
    private int birthY=1990, birthM=1, birthD=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        p1 = findViewById(R.id.main_home_p);
        //p2 = findViewById(R.id.main_music_p);
        p3 = findViewById(R.id.main_mood_p);
        p4 = findViewById(R.id.main_personal_p);

        i1 = findViewById(R.id.main_home);
        //i2 = findViewById(R.id.main_music);
        i3 = findViewById(R.id.main_mood);
        i4 = findViewById(R.id.main_personal);

        i1.setOnClickListener(this);
        //i2.setOnClickListener(this);
        i3.setOnClickListener(this);
        i4.setOnClickListener(this);

        setConstellation();
        replaceFragment(homeFragment);

        //子线程更新个人信息


        new Thread(new Runnable() {
            @Override
            public void run() {
                account = getIntent().getStringExtra("account");
                String json = WebService.executeGetPersonalInfo("PersonalInfoLet", account);
                try {
                    jsonObject = new JSONObject(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resolvePersonalInfo(jsonObject);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        String s = Integer.toString(v.getId());
        Log.d("MainActivity", s);
        switch (v.getId()) {
            case R.id.main_home:
                p1.setImageResource(R.drawable.zhuye);
                //p2.setImageResource(R.drawable.yinyue_1);
                p3.setImageResource(R.drawable.xinqing_1);
                p4.setImageResource(R.drawable.geren_1);
                replaceFragment(homeFragment);
                break;
                /*
            case R.id.main_music:
                p1.setImageResource(R.drawable.zhuye_1);
                //p2.setImageResource(R.drawable.yinyue);
                p3.setImageResource(R.drawable.xinqing_1);
                p4.setImageResource(R.drawable.geren_1);
                //replaceFragment(musicFragment);
                break;
                */
            case R.id.main_mood:
                p1.setImageResource(R.drawable.zhuye_1);
                //p2.setImageResource(R.drawable.yinyue_1);
                p3.setImageResource(R.drawable.xinqing);
                p4.setImageResource(R.drawable.geren_1);
                replaceFragment(moodFragment);
                break;
            case R.id.main_personal:
                p1.setImageResource(R.drawable.zhuye_1);
                //p2.setImageResource(R.drawable.yinyue_1);
                p3.setImageResource(R.drawable.xinqing_1);
                p4.setImageResource(R.drawable.geren);
                personalFragment = new PersonalFragment();
                replaceFragment(personalFragment);
                break;

            default:
                break;

        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.commit();
    }

    private void resolvePersonalInfo(JSONObject json) {
        try {
            int temp = json.getInt("birth");
            nickname = json.getString("nickname");
            introduction = json.getString("introduction");
            phonenum = json.getString("phonenum");
            gender = json.getString("gender");
            birthY = temp / 10000;
            birthM = (temp - 10000 * birthY) / 100;
            birthD = temp % 100;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getPersonalString(String infoName) throws Exception {
        switch (infoName) {
            case "nickname":
                return nickname;
            case "introduction":
                return introduction;
            case "phonenum":
                return phonenum;
            case "gender":
                return gender;
            case "constellation":
                return constellation;
            case "account":
                return account;
            default:
                throw new Exception();
        }
    }

    public int getPersonalInt(String infoname) throws Exception {
        switch (infoname) {
            case "birthY":
                return birthY;
            case "birthM":
                return birthM;
            case "birthD":
                return birthD;
            default:
                throw new Exception();
        }
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setBirthY(int birthY) {
        this.birthY = birthY;
    }

    public void setBirthD(int birthD) {
        this.birthD = birthD;
    }

    public void setBirthM(int birthM) {
        this.birthM = birthM;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public void setConstellation() {
        String con = "摩羯座";
        switch (birthM) {
            case 1:
                con = (birthD <= 19) ? "摩羯座" : "水瓶座";
                break;
            case 2:
                con = (birthD <= 18) ? "水瓶座" : "双鱼座";
                break;
            case 3:
                con = (birthD <= 20) ? "双鱼座" : "白羊座";
                break;
            case 4:
                con = (birthD <= 19) ? "白羊座" : "金牛座";
                break;
            case 5:
                con = (birthD <= 20) ? "金牛座" : "双子座";
                break;
            case 6:
                con = (birthD <= 21) ? "双子座" : "巨蟹座";
                break;
            case 7:
                con = (birthD <= 22) ? "巨蟹座" : "狮子座";
                break;
            case 8:
                con = (birthD <= 22) ? "狮子座" : "处女座";
                break;
            case 9:
                con = (birthD <= 22) ? "处女座" : "天秤座";
                break;
            case 10:
                con = (birthD <= 23) ? "天秤座" : "天蝎座";
                break;
            case 11:
                con = (birthD <= 22) ? "天蝎座" : "射手座";
                break;
            case 12:
                con = (birthD <= 21) ? "射手座" : "摩羯座";
                break;
            default:
                break;

        }
        constellation = con;
    }

    public String getInfoString() {
        String tempBirth=Integer.toString(birthY)+
                (birthM>9?Integer.toString(birthM):("0"+Integer.toString(birthM)))+
                (birthD>9?Integer.toString(birthD):("0"+Integer.toString(birthD)));
       return "account="+account+
               "&gender="+gender+
               "&nickname="+nickname+
               "&introduction="+introduction+
               "&birth="+tempBirth+
               "&phonenum="+phonenum;
    }

}
