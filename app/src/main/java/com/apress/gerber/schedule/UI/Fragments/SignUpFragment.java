package com.apress.gerber.schedule.UI.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apress.gerber.schedule.R;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SignUpFragment extends Fragment {
    View viewContainer;
    private Button signUp;
    private EditText name, email, password, mobile, confPassword;
    boolean process = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.signup_fragment, container, false);
        name = viewContainer.findViewById(R.id.sign_up_name);
        email = viewContainer.findViewById(R.id.sign_up_email);
        password = viewContainer.findViewById(R.id.sign_up_password);
        confPassword = viewContainer.findViewById(R.id.sign_up_conf_password);
        mobile = viewContainer.findViewById(R.id.sign_up_mobile);
        signUp = viewContainer.findViewById(R.id.sign_up_submit_button);
        onClickEvents();
        return viewContainer;
    }

    public void onClickEvents() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName = name.getText().toString();
                String mEmail = email.getText().toString();
                String mMobile = mobile.getText().toString();
                if (process)
                    Toast.makeText(getActivity(), "Still In Progress", Toast.LENGTH_SHORT).show();
                else if (mEmail.equals("") || mMobile.equals("") || mName.equals("") || password.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "Please Fill All The Fields", Toast.LENGTH_SHORT).show();
                else if (!password.getText().toString().equals(confPassword.getText().toString()))
                    Toast.makeText(getActivity(), "Password Fields Do Not Match", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getActivity(), "Initializing", Toast.LENGTH_SHORT).show();
                    process = true;
                }
            }
        });
    }


}
