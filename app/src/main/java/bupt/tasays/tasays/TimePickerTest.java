package bupt.tasays.tasays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class TimePickerTest extends AppCompatActivity implements View.OnClickListener,NumberPickerView.OnValueChangeListener
{

    private NumberPickerView year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_picker);
        year=(NumberPickerView)findViewById(R.id.picker1);
        month=(NumberPickerView) findViewById(R.id.picker2);
        day=(NumberPickerView)findViewById(R.id.picker3);
        /*设置侦听*/
        year.setOnValueChangedListener(this);
        month.setOnValueChangedListener(this);
        day.setOnValueChangedListener(this);
        init();

    }

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal)
    {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            default:
                break;
        }

    }

    public void init()
    {
        year.refreshByNewDisplayedValues(generateNumbers(1900,2017));
        year.setMinValue(0);
        year.setMaxValue(117);
        year.setValue(90);

        month.refreshByNewDisplayedValues(generateNumbers(1,12));
        month.setMinValue(0);
        month.setMaxValue(11);
        month.setValue(0);

        day.refreshByNewDisplayedValues(generateNumbers(1,31));
        day.setMinValue(0);
        day.setMaxValue(30);
        day.setValue(0);
    }

    public String[] generateNumbers(int startNum,int endNum)
    {
        int total=endNum-startNum+1;
        String[] a=new String[total];
        for(int i=0;i<total;i++)
        {
            a[i]=Integer.toString(startNum+i);
        }
        return a;
    }

}