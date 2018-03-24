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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import bupt.tasays.web_sql.WebService;

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

    private ImageView portrait;
    private Bitmap head;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径

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
        Bitmap bt = BitmapFactory.decodeFile(path+"head.jpg");
        if(bt!=null){
            Drawable drawable = new BitmapDrawable(bt);
        }
        else
        {
            //Logic of downloading portrait from server.
        }
        portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTypeDialog();
            }
        });
    }

    public void showTypeDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        final AlertDialog dialog=builder.create();
        View v = View.inflate(getActivity(),R.layout.type_select_dialog,null);
        CardView upload=v.findViewById(R.id.type_upload),
                 camera=v.findViewById(R.id.type_camera);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(Intent.ACTION_PICK,null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent1,1);
                dialog.dismiss();
            }
        });
        //
        //  需要解决安卓7.0拍照问题
        //
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(v);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中
                        portrait.setImageBitmap(head);// 用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
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
}


