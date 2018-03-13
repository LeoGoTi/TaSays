package bupt.tasays.tasays;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                account.setHint("请输入好吗别搞我！！！");
            }
        });
    }
}
