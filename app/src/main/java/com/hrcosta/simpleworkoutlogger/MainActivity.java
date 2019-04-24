package com.hrcosta.simpleworkoutlogger;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;


   // @BindView(R.id.appbar_login) Toolbar mToolbar;
    @BindView(R.id.textinput_email) TextInputLayout textInputEmail;
    @BindView(R.id.textinput_username) TextInputLayout textInputName;
    @BindView(R.id.textinput_password) TextInputLayout textInputPassword;
    @BindView(R.id.btn_confirm) Button btnConfirm;
    @BindView(R.id.btn_register) Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        ButterKnife.bind(this);

        //setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("User Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private boolean validateEmail () {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()){
            textInputEmail.setError("Field can't be empty");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateUserName () {
        String usernameInput = textInputName.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()){
            textInputName.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15 ) {
            textInputEmail.setError("Username too long");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;

        }
    }

    private boolean validatePassword () {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()){
            textInputPassword.setError("Field can't be empty");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

    public void confirmClicked(View v) {
        if ( !validateEmail() | !validatePassword() | !validateUserName()) {
            return;
        }

       // mAuth = FirebaseAuth.getInstance();


        String input =  "Email: " + textInputEmail.getEditText().getText().toString();
        input += "\n";
        input += "Username: " + textInputName.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + textInputPassword.getEditText().getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();


    }

}
