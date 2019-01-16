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
import android.widget.Toast;

import com.apress.gerber.schedule.R;
import com.apress.gerber.schedule.Utlities.FireStoreKeys;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {
    private View viewContainer;
    private Button signUp;
    private EditText name, email, password, mobile, confPassword;
    boolean process = false;
    private FirebaseAuth mAuth;
    private FireStoreKeys keys;
    private FirebaseFirestore fireStore;

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
        keys = new FireStoreKeys();
        fireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        onClickEvents();
        return viewContainer;
    }

    public void onClickEvents() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mName = name.getText().toString();
                final String mEmail = email.getText().toString();
                final String mMobile = mobile.getText().toString();
                if (process)
                    Toast.makeText(getActivity(), "Still In Progress", Toast.LENGTH_SHORT).show();
                else if (mEmail.equals("") || mMobile.equals("") || mName.equals("") || password.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "Please Fill All The Fields", Toast.LENGTH_SHORT).show();
                else if (!password.getText().toString().equals(confPassword.getText().toString()))
                    Toast.makeText(getActivity(), "Password Fields Do Not Match", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getActivity(), "Initializing", Toast.LENGTH_SHORT).show();
                    process = true;
                    mAuth.createUserWithEmailAndPassword(mEmail, password.getText().toString()).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Waiting for Account Update ...", Toast.LENGTH_SHORT).show();
                                        final String s = FirebaseAuth.getInstance().getUid();
                                        final Map<String, Object> map = new HashMap<>();
                                        map.put(keys.getEmail(), mEmail);
                                        map.put(keys.getMobile(), mMobile);
                                        map.put(keys.getName(), mName);
                                        fireStore.collection("Users").document(s).set(map).
                                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getActivity(), "Sign Up Successful ... ", Toast.LENGTH_SHORT).show();
                                                        mAuth.signOut();
                                                    }
                                                }).
                                                addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        process = false;
                                                        Toast.makeText(getActivity(), "Sign Up Failed!!!", Toast.LENGTH_SHORT).show();
                                                        mAuth.signOut();
                                                    }
                                                });
                                    } else {
                                        if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                            Toast.makeText(getActivity(), "Already Registered", Toast.LENGTH_LONG).show();
                                        else
                                            Toast.makeText(getActivity(), "Check E-mail and Password SignUp Failed", Toast.LENGTH_LONG).show();
                                        mAuth.signOut();
                                        process = false;
                                    }
                                }
                            });
                }
            }
        });
    }


}
