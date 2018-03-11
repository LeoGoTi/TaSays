package bupt.tasays.tasays;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by root on 18-3-11.
 */

public class RegisterActivity extends AppCompatActivity{
    private EditText account,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        account=findViewById(R.id.reg_account);
        password=findViewById(R.id.reg_password);
    }
}
