package bupt.tasays.tasays;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by root on 17-12-5.
 */

public class PersonalFragment extends Fragment
{
    private PersonalInfo personalInfo=new PersonalInfo();
    private PersonalEdit personaledit=new PersonalEdit();
    private boolean editFlag=false;
    private FloatingActionButton floatingActionButton;
    private TextView signature,userId;//此处仅需要更新这两个信息
    private MainActivity mainActivity=(MainActivity) getActivity();
    private int birthY=1990,birthM=1,birthD=1;
    private View fragmentView,view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.personal_layout, container, false);
        fragmentView=inflater.inflate(R.layout.personal_info, container, false);
        replaceFragment(personalInfo);
        floatingActionButton=view.findViewById(R.id.edit_float_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!editFlag)
                {
                    replaceFragment(personaledit);
                    floatingActionButton.setImageResource(R.drawable.check);
                    editFlag=!editFlag;
                }
                else
                {
                    personaledit.refreshToMain();
                    replaceFragment(personalInfo);
                    floatingActionButton.setImageResource(R.drawable.signatureright);
                    updatePersonalInfo();
                    editFlag = !editFlag;
                }
                refresh2();
            }
        });
        refresh2();
        return view;
    }


    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.personal_content,fragment);
        transaction.commit();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        replaceFragment(personalInfo);
    }

    private void refresh2(){
        try {
            signature = view.findViewById(R.id.signature);
            userId = view.findViewById(R.id.user_id);
            mainActivity = (MainActivity) getActivity();
            signature.setText(mainActivity.getPersonalString("introduction"));
            userId.setText(mainActivity.getPersonalString("phonenum"));
        }
        catch(Exception e)
        {

        }
    }

    public void updatePersonalInfo(){

    }
}
