package bupt.tasays.tasays;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bupt.tasays.web_sql.WebService;

/**
 * Created by root on 18-3-11.
 */

public class RegisterActivity extends AppCompatActivity{
    private EditText account,password,phonenum;
    private Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        account=findViewById(R.id.reg_account);
        password=findViewById(R.id.reg_password);
        phonenum=findViewById(R.id.reg_phonenum);
        reg=findViewById(R.id.reg_button);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkRegNull()) {
                    new Thread(new regThread()).start();
                }
                else
                    Toast.makeText(RegisterActivity.this,"请完整填写注册信息",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class regThread implements Runnable{
        String result;
        @Override
        public void run(){
            result=WebService.executeRegister("RegLet",account.getText().toString(),password.getText().toString(),phonenum.getText().toString());
            // 似乎会报错
            Looper.prepare();
            Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    public boolean checkRegNull(){
        if(account.getText().toString().equals(""))
            return true;
        if(password.getText().toString().equals(""))
            return true;
        if(phonenum.getText().toString().equals(""))
            return true;
        return false;
    }
}
