package bupt.tasays.tasays;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * Created by root on 17-12-5.
 */

public class PersonalEdit extends Fragment implements NumberPickerView.OnValueChangeListener
{
    private TextView constellation;
    private TextView birth;
    private NumberPickerView year,month,day;
    private RadioButton isMale,isFamale;
    private EditText nickname,signature;
    private int birthY=1990,birthM=1,birthD=1;
    private boolean is_male=true;
    MainActivity mainActivity=(MainActivity)getActivity();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.personal_edit, container, false);
        constellation=view.findViewById(R.id.constellation);
        nickname=view.findViewById(R.id.nicknameE);
        signature=view.findViewById(R.id.signatureE);
        birth=view.findViewById(R.id.birth);
        isMale=view.findViewById(R.id.is_male);
        isFamale=view.findViewById(R.id.is_famale);
        constellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),TimePickerTest.class);
                startActivity(intent);
            }
        });
        birth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showDialog();
            }
        });
        isMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                is_male=true;
            }
        });
        isFamale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                is_male=false;
            }
        });
        refreshInfo();
        return view;
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.time_picker, null);//获取自定义布局
        year=(NumberPickerView)layout.findViewById(R.id.picker1);
        month=(NumberPickerView)layout.findViewById(R.id.picker2);
        day=(NumberPickerView)layout.findViewById(R.id.picker3);
        init();
        builder.setView(layout);
        //final AlertDialog dlg = builder.create();
        builder.setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                })
                .setPositiveButton("更改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        birth.setText(Integer.toString(birthY)+"年 "+Integer.toString(birthM)+"月 "+Integer.toString(birthD)+"日");
                        setConstellation();
                    }
                });
        builder.show();
        year.setOnValueChangedListener(this);
        month.setOnValueChangedListener(this);
        day.setOnValueChangedListener(this);
    }

    public void init()
    {
        year.refreshByNewDisplayedValues(generateNumbers(1900,2017));
        year.setMinValue(0);
        year.setMaxValue(117);
        year.setValue(90);

        month.refreshByNewDisplayedValues(generateNumbers(1,12));
        month.setMinValue(0);
        month.setMaxValue(11);
        month.setValue(0);

        day.refreshByNewDisplayedValues(generateNumbers(1,31));
        day.setMinValue(0);
        day.setMaxValue(30);
        day.setValue(0);
    }

    public String[] generateNumbers(int startNum,int endNum)
    {
        int total=endNum-startNum+1;
        String[] a=new String[total];
        for(int i=0;i<total;i++)
        {
            a[i]=Integer.toString(startNum+i);
        }
        return a;
    }

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal)
    {
        //Toast.makeText(getActivity(),"valuechanged to "+Integer.toString(newVal),Toast.LENGTH_SHORT).show();
        switch(picker.getId())
        {
            case R.id.picker1:
                birthY=1900+newVal;
                setDayRange();
                break;
            case R.id.picker2:              //月份更新时，更新日期的数量
                birthM=1+newVal;
                setDayRange();
                break;
            case R.id.picker3:
                birthD=1+newVal;
                break;
            default:
                break;
        }
    }

    public boolean isRunYear(int year)
    {
        return(year%400==0)||(year%100!=0&&year%4==0);
    }

    public void setDayRange()
    {
        switch(birthM)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day.refreshByNewDisplayedValues(generateNumbers(1,31));
                day.setValue(birthD-1);
                break;
            case 2:
                if(isRunYear(birthY)) {
                    day.refreshByNewDisplayedValues(generateNumbers(1, 29));
                    if(birthD==31||birthD==30)
                        day.setValue(28);
                    else
                        day.setValue(birthD-1);
                }
                else{
                    day.refreshByNewDisplayedValues(generateNumbers(1,28));
                    if(birthD==31||birthD==30||birthD==29)
                        day.setValue(27);
                    else
                        day.setValue(birthD-1);
                }
                break;
            default:
                day.refreshByNewDisplayedValues(generateNumbers(1,30));
                if(birthD==31)
                    day.setValue(29);
                else
                    day.setValue(birthD-1);
                break;
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

        constellation.setText(con);
    }

    public void refreshInfo(){
        try {
            mainActivity = (MainActivity) getActivity();
            signature.setText(mainActivity.getPersonalString("introduction"));
            nickname.setText(mainActivity.getPersonalString("nickname"));
            isMale.setChecked((mainActivity.getPersonalString("gender").equals("1")));
            isFamale.setChecked(!(mainActivity.getPersonalString("gender").equals("1")));
            birthD=mainActivity.getPersonalInt("birthD");
            birthM=mainActivity.getPersonalInt("birthM");
            birthY=mainActivity.getPersonalInt("birthY");
            birth.setText(Integer.toString(birthY)+"年"+Integer.toString(birthM)+"月"+Integer.toString(birthD)+"日");
            setConstellation();
        }
        catch (Exception e){

        }
    }

    //更新信息到主活动
    public void refreshToMain(){
        mainActivity.setBirthD(birthD);
        mainActivity.setBirthM(birthM);
        mainActivity.setBirthY(birthY);
        if(!nickname.getText().toString().equals(""))
            mainActivity.setNickname(nickname.getText().toString());
        if(!signature.getText().toString().equals(""))
            mainActivity.setIntroduction(signature.getText().toString());
        mainActivity.setGender(isMale.isChecked()?"1":"0");
        mainActivity.setConstellation(constellation.getText().toString());
    }
}
