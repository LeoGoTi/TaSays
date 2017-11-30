package bupt.tasays.tasays;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class LoginActivity extends AppCompatActivity {
    private int backPressed=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView register,forget;
        final Button login;
        register = findViewById(R.id.login_register);
        forget   = findViewById(R.id.login_forget);
        login    = findViewById(R.id.login_button);
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Logic of register here;
                Toast.makeText(LoginActivity.this,"You Clicked Register.",Toast.LENGTH_SHORT).show();
            }
        });
        forget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Logic of forget here;
                Intent intent=new Intent(LoginActivity.this,WebActivity.class);
                String url="http://music.163.com/song/473817115?userid=340961567";
                intent.putExtra("destUrl",url);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Logic of login here;
                final Intent intent=new Intent(LoginActivity.this,MainActivity.class);

                final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("登陆中");
                progressDialog.setMessage("请稍等……");
                progressDialog.setCancelable(false);
                progressDialog.show();
                /*下面开启新线程*/
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        int i = 0;

                        while (i<100) {
                            try
                            {
                                Log.d("LoginActivity", "run: ");
                                i++;
                                sleep(10);
                            }
                            catch (Exception e) {
                                // TODO: handle exception
                            }
                        }
                        // 在进度条走完时删除Dialog,进入下一个活动
                        progressDialog.dismiss();
                        startActivity(intent);
                        finish();

                    }
                }).start();

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        //代码为空不能返回欢迎界面
        backPressed+=1;
        if (backPressed==1)
        {
            Toast.makeText(LoginActivity.this,"再次点击退出",Toast.LENGTH_SHORT).show();
        }
        else
            finish();
    }
}
