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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.personal_layout, container, false);
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
                    replaceFragment(personalInfo);
                    floatingActionButton.setImageResource(R.drawable.signatureright);
                    editFlag = !editFlag;
                }
            }
        });
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        replaceFragment(personalInfo);
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.personal_content,fragment);
        transaction.commit();
    }
}
