package bupt.tasays.tasays;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import bupt.tasays.web_sql.WebService;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by root on 17-12-5.
 */

public class PersonalFragment extends Fragment {
    private PersonalInfo personalInfo = new PersonalInfo();
    private PersonalEdit personaledit = new PersonalEdit();
    private boolean editFlag = false;
    private FloatingActionButton floatingActionButton;
    private TextView signature, phone;//此处仅需要更新这两个信息
    private MainActivity mainActivity = (MainActivity) getActivity();
    private int birthY = 1990, birthM = 1, birthD = 1;
    private View fragmentView, view;
    private CircleImageView portrait;
    private Bitmap head;// 头像Bitmap
    private int headid;
    private static String path = "/sdcard/myHead/";// sd路径

    int [] heads={
            R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10,R.drawable.p11,R.drawable.p12,R.drawable.p13,R.drawable.p14,
            R.drawable.p15,R.drawable.p16,R.drawable.p17,R.drawable.p18,R.drawable.p19,R.drawable.p20,R.drawable.p21
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.personal_layout, container, false);
        fragmentView = inflater.inflate(R.layout.personal_info, container, false);
        replaceFragment(personalInfo);
        floatingActionButton = view.findViewById(R.id.edit_float_button);
        portrait=view.findViewById(R.id.portrait);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editFlag) {
                    personaledit=new PersonalEdit();
                    replaceFragment(personaledit);
                    floatingActionButton.setImageResource(R.drawable.check);
                    editFlag = !editFlag;
                } else {
                    personaledit.refreshToMain();
                    replaceFragment(personalInfo);
                    floatingActionButton.setImageResource(R.drawable.signatureright);
                    new Thread(new updateThread()).start();
                    editFlag = !editFlag;
                }
                refresh2();
            }
        });
        initPortrait();
        refresh2();
        return view;
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.personal_content, fragment);
        transaction.commit();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        replaceFragment(personalInfo);
    }

    private void refresh2() {
        try {
            if(mainActivity==null)
                mainActivity=(MainActivity)getActivity();
            headid=mainActivity.getPersonalInt("headid");
            signature = view.findViewById(R.id.signature);
            phone = view.findViewById(R.id.user_id);
            mainActivity = (MainActivity) getActivity();
            signature.setText(mainActivity.getPersonalString("introduction"));
            phone.setText(mainActivity.getPersonalString("phonenum"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class updateThread implements Runnable {
        String tempString;

        @Override
        public void run() {

            try {
                tempString = WebService.executePostPersonalInfo("EditInfoLet", mainActivity.getPersonalString("account"), mainActivity.getInfoString());
            } catch (Exception e) {
            }


        }
    }

    public void initPortrait(){
        try {
            if(mainActivity==null)
                mainActivity=(MainActivity)getActivity();
            portrait.setImageResource(getPortrait(mainActivity.getPersonalInt("headid")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mainActivity,LocalHeadsActivity.class);
                startActivityForResult(intent,9);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 9://头像选择完成
                if(data==null)
                    return;
                int toReturn=data.getIntExtra("new_head",0);
                portrait.setImageResource(toReturn);
                int newhead=22;
                for(int i=0;i<21;i++)
                    if(heads[i]==toReturn)
                        newhead=i+1;
                headid=newhead;
                if(mainActivity==null)
                    mainActivity=(MainActivity)mainActivity;
                mainActivity.setHeadId(headid);
                PersonalFragment personalFragment=new PersonalFragment();
                mainActivity.personalFragment=personalFragment;
                mainActivity.replaceFragment(personalFragment);
                new Thread(new updateThread()).start();
                break;
            default:
                break;

        }
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPortrait(int headid){

        try {
            if(mainActivity==null)
                mainActivity=(MainActivity)getActivity();
            if(mainActivity.getPersonalInt("headid")!=22)
            return heads[mainActivity.getPersonalInt("headid")-1];
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return R.drawable.user_fill;
    }
}


