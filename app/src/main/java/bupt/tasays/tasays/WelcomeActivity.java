package bupt.tasays.tasays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {
    boolean ok=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_layout);
        ImageView imageView=findViewById(R.id.id_welcome);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ok=!ok;
                Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        new Thread(new Thread(){
            @Override
            public void run(){
                try{
                    sleep(3000);
                    if(!ok){
                        Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
