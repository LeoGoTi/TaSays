package bupt.tasays.tasays;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bupt.tasays.web_sql.WebService;


public class LoginActivity extends AppCompatActivity {
    private int backPressed = 0;
    private String info;
    private EditText account, password;
    private static Handler handler = new Handler();
    private ProgressDialog progressDialog;
    private Intent intent;
    private CheckBox remember,auto;
    private boolean isRemembered=false,isAuto=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView register, forget;
        final Button login;
        register = findViewById(R.id.login_register);
        forget = findViewById(R.id.login_forget);
        login = findViewById(R.id.login_button);
        account = findViewById(R.id.login_account);
        password = findViewById(R.id.login_password);
        remember=findViewById(R.id.login_remember);
        auto=findViewById(R.id.login_auto);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logic of register here;
                intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
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
                intent = new Intent(LoginActivity.this, MainActivity.class);

                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("登陆中");
                progressDialog.setMessage("请稍等……");
                progressDialog.setCancelable(false);
                progressDialog.show();
                /*下面开启新线程*/
                new Thread(new MyThread()).start();
            }
        });
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRemembered=remember.isChecked();
            }
        });
        auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRemembered=isAuto=auto.isChecked();
                remember.setChecked(isRemembered);
            }
        });

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
                    if(info.equals("登陆成功")){
                        intent.putExtra("account",account.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
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
}
