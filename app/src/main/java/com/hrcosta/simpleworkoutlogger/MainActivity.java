package com.hrcosta.simpleworkoutlogger;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String emailInput;
    private String passwordInput;


   // @BindView(R.id.appbar_login) Toolbar mToolbar;
    @BindView(R.id.textinput_email) TextInputLayout textInputEmail;
    @BindView(R.id.textinput_password) TextInputLayout textInputPassword;
    @BindView(R.id.btn_confirm) Button btnConfirm;
    @BindView(R.id.btn_register) Button btnRegister;


    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  mAuth.getInstance().signOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("User Login");


        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    //user loggedin , goes to calendar.
                    startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                }

            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @OnClick({R.id.btn_confirm})
    public void confirmClicked() {
        if ( !validateEmail() | !validatePassword()) {
            return;
        } else {
            String email = textInputEmail.getEditText().getText().toString();
            String password = textInputPassword.getEditText().getText().toString();

          //  mAuth = FirebaseAuth.getInstance();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "successfull", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                        //todo start calendar activity
                        //todo implement mauth.signout() on calendar menu.
                    }else{
                        Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

//            String input = "Email: " + textInputEmail.getEditText().getText().toString();
//            input += "\n";
//            input += "Username: " + textInputName.getEditText().getText().toString();
//            input += "\n";
//            input += "Password: " + textInputPassword.getEditText().getText().toString();
//
//            Toast.makeText(this, input, Toast.LENGTH_SHORT).show();

    }

    @OnClick({R.id.btn_register})
    public void registerClicked() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    private boolean validateEmail () {
        emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()){
            textInputEmail.setError("Field can't be empty");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }



    private boolean validatePassword () {
        passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()){
            textInputPassword.setError("Field can't be empty");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

}