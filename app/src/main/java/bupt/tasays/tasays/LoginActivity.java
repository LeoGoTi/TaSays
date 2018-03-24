package bupt.tasays.tasays;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bupt.tasays.settings.Settings;
import bupt.tasays.web_sql.WebService;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private int backPressed = 0;
    private String info;
    private EditText account, password;
    private static Handler handler = new Handler();
    private ProgressDialog progressDialog;
    private Intent intent;
    private CheckBox remember, auto;
    private Button login;
    private boolean isRemembered = false, isAuto = false;
    private String settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView register, forget;

        register = findViewById(R.id.login_register);
        forget = findViewById(R.id.login_forget);
        login = findViewById(R.id.login_button);
        account = findViewById(R.id.login_account);
        password = findViewById(R.id.login_password);
        remember = findViewById(R.id.login_remember);
        auto = findViewById(R.id.login_auto);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logic of register here;
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logic of forget here;
                intent = new Intent(LoginActivity.this, WebActivity.class);
                String url = "http://music.163.com/song/473817115?userid=340961567";
                intent.putExtra("destUrl", url);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logic of login here;
                if (generateSettings()) {
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    try{
                        Settings.writeSetting(LoginActivity.this,settings);
//                        Toast.makeText(LoginActivity.this,Settings.readSetting(LoginActivity.this),Toast.LENGTH_SHORT).show();
//                        用于toast文件情况
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("登陆中");
                    progressDialog.setMessage("请稍等……");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                /*下面开启新线程*/
                    new Thread(new MyThread()).start();
                }
                else
                    Toast.makeText(LoginActivity.this,"请完整输入用户名和密码",Toast.LENGTH_SHORT).show();
            }
        });
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRemembered = remember.isChecked();
                if(!isRemembered)
                    auto.setChecked(false);
            }
        });
        auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRemembered = isAuto = auto.isChecked();
                remember.setChecked(isRemembered);
            }
        });

        useSettings();//最后再载入设置因为需要前面的控件引用到具体对象


    }

    public class MyThread implements Runnable {
        @Override
        public void run() {
            info = WebService.executeHttpGet("LogLet", account.getText().toString(), password.getText().toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(LoginActivity.this, info, Toast.LENGTH_SHORT);
                    toast.show();
                    progressDialog.dismiss();
                    if (info.equals("登陆成功")) {
                        intent.putExtra("account", account.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

    public boolean generateSettings() {
        JSONObject jsonObject = new JSONObject();
        String account, password;
        boolean remember, auto;
        account = this.account.getText().toString();
        password = this.password.getText().toString();
        remember = this.remember.isChecked();
        auto = this.auto.isChecked();
        if (account.equals("") || password.equals(""))
            return false;//有空项，生成失败
        try {
            jsonObject.put("account", account);
            jsonObject.put("password", password);
            jsonObject.put("remember", remember ? "true" : "false");
            jsonObject.put("auto", auto ? "true" : "false");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        settings = jsonObject.toString();
        return true;
    }

    @Override
    public void onBackPressed() {
        //代码为空不能返回欢迎界面
        backPressed += 1;
        if (backPressed == 1) {
            Toast.makeText(LoginActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
        } else
            finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    account.setText(data.getStringExtra("account"));
                    password.setText(data.getStringExtra("password"));
                    login.performClick();

                }
        }
    }

    public boolean useSettings(){
        try {
            String str=Settings.readSetting(LoginActivity.this),
                    account,password,auto,remember;
            JSONObject jsonObject=new JSONObject(str);
            boolean isR,isA;
            account=jsonObject.getString("account");
            password=jsonObject.getString("password");
            auto=jsonObject.getString("auto");
            remember=jsonObject.getString("remember");
            if(account.equals("")||password.equals(""))
                return false;
            isR=remember.equals("true");
            isA=auto.equals("true");
            this.account.setText(account);
            if(isR) {
                this.password.setText(password);
                this.remember.setChecked(true);
            }
            if(isA) {
                this.auto.setChecked(true);
                login.performClick();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
