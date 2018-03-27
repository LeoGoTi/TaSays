package bupt.tasays.tasays;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import bupt.tasays.settings.Settings;

/**
 * Created by root on 17-12-5.
 */

public class PersonalInfo extends Fragment
{

    private TextView contact,clean,about;
    private TextView nickname,signature,userId,birth,constellation,gender;
    CardView logout;
    private View view;
    private MainActivity mainActivity;
    private int birthY,birthM,birthD;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.personal_info, container, false);
        contact =view.findViewById(R.id.contact);
        clean=view.findViewById(R.id.clean);
        about=view.findViewById(R.id.about);
        logout=view.findViewById(R.id.logout);
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
                Toast.makeText(getActivity(),"项目组长：\n    张欣悦\n组员：\n    郭聚川\n    马祁\n    刘泳君\n    刘国维",Toast.LENGTH_SHORT).show();
            }
        });
        mainActivity=(MainActivity)getActivity();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mainActivity);
                dialog.setTitle("登出");
                dialog.setMessage("确定要退出登录吗？");
                dialog.setCancelable(true);
                dialog.setPositiveButton("退出登陆", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            JSONObject jsonObject=new JSONObject();
                            String account=mainActivity.getPersonalString("account");
                            jsonObject.put("account", account);
                            jsonObject.put("password","");
                            jsonObject.put("remember", "false");
                            jsonObject.put("auto","false");
                            Settings.writeSetting(mainActivity,jsonObject.toString());
                            Intent intent=new Intent(mainActivity,LoginActivity.class);
                            startActivity(intent);
                            mainActivity.finish();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        nickname=       view.findViewById(R.id.nickname_);
        gender=         view.findViewById(R.id.gender_);
        constellation=  view.findViewById(R.id.constellation_);
        birth=          view.findViewById(R.id.birth_);
        //signature=      view1.findViewById(R.id.signature);
        //userId=         view1.findViewById(R.id.user_id);
        refreshPersonalInfo();
    }

    public void refreshPersonalInfo() {
        try{
            nickname.setText(("  昵称      "+mainActivity.getPersonalString("nickname")));
            //signature.setText(mainActivity.getPersonalString("introduction"));
            gender.setText((mainActivity.getPersonalString("gender").equals("1"))?"  性别      男":"  性别      女");
            //userId.setText(mainActivity.getPersonalString("phonenum"));
            birthY=mainActivity.getPersonalInt("birthY");
            birthM=mainActivity.getPersonalInt("birthM");
            birthD=mainActivity.getPersonalInt("birthD");
            birth.setText(
                    ("  生日      "+
                            Integer.toString(birthY)+"年"+
                            Integer.toString(birthM)+"月"+
                            Integer.toString(birthD)+"日"
                    )
            );
            setConstellation();
        }
        catch (Exception e){

        }
    }

    public void setConstellation()
    {
        String con="摩羯座";
        switch(birthM)
        {
            case 1:
                con=(birthD<=19)?"摩羯座":"水瓶座";
                break;
            case 2:
                con=(birthD<=18)?"水瓶座":"双鱼座";
                break;
            case 3:
                con=(birthD<=20)?"双鱼座":"白羊座";
                break;
            case 4:
                con=(birthD<=19)?"白羊座":"金牛座";
                break;
            case 5:
                con=(birthD<=20)?"金牛座":"双子座";
                break;
            case 6:
                con=(birthD<=21)?"双子座":"巨蟹座";
                break;
            case 7:
                con=(birthD<=22)?"巨蟹座":"狮子座";
                break;
            case 8:
                con=(birthD<=22)?"狮子座":"处女座";
                break;
            case 9:
                con=(birthD<=22)?"处女座":"天秤座";
                break;
            case 10:
                con=(birthD<=23)?"天秤座":"天蝎座";
                break;
            case 11:
                con=(birthD<=22)?"天蝎座":"射手座";
                break;
            case 12:
                con=(birthD<=21)?"射手座":"摩羯座";
                break;
            default:
                break;

        }

        constellation.setText(("  星座      "+con));
    }
}
