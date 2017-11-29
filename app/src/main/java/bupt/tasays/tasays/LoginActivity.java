package bupt.tasays.tasays;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private int backPressed=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ActionBar actionBar=getSupportActionBar();//隐藏标题栏和状态栏
        if(actionBar!=null)
        {
            actionBar.hide();
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView register,forget;
        Button login;
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
                Toast.makeText(LoginActivity.this,"You Clicked Login.",Toast.LENGTH_SHORT).show();
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
