package com.apress.gerber.schedule.UI.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.apress.gerber.schedule.R;

import org.w3c.dom.Text;

public class LoginFragment extends Fragment {
    private View viewContainer;
    private EditText email, password;
    private TextView signup;
    private Button login;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.login_fragment, container, false);
        email = viewContainer.findViewById(R.id.login_email);
        password = viewContainer.findViewById(R.id.login_password);
        login = viewContainer.findViewById(R.id.login_button);
        signup = viewContainer.findViewById(R.id.login_sign_up_button);
        onClickEvents();
        return viewContainer;

    }


    public void onClickEvents() {
        
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_container, new SignUpFragment()).addToBackStack(null).commit();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
