package bupt.tasays.tasays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

public class LocalHeadsActivity extends AppCompatActivity implements View.OnClickListener{

    String toReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_heads);
        CircleImageView p1=findViewById(R.id.local_1);
        p1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.local_1:
                toReturn="p1";
                Intent intent=getIntent();
                intent.putExtra("new_head",toReturn);
                setResult(9,intent);
                finish();
                break;
            default:
                break;

        }
    }
}
