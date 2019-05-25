package com.hrcosta.simpleworkoutlogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {


    private static final String TAG = RegisterActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    String emailInput;
    String usernameInput;
    String passwordInput;
    String passwordInput2;
    private ProgressDialog mProgressDialog;
    private FirebaseAnalytics mFirebaseAnalytics;

    @BindView(R.id.textinput_reg_email) TextInputLayout textInputEmail;
    @BindView(R.id.textinput_username) TextInputLayout textInputName;
    @BindView(R.id.textinput_reg_password) TextInputLayout textInputPass;
    @BindView(R.id.textinput_reg_password2) TextInputLayout textInputPass2;
    @BindView(R.id.btn_register) Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        getSupportActionBar().setTitle("New Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mProgressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });

    }

    private void validateFields(){
        if ( !validateEmail() | !validatePassword() | !validateUserName()) {
            return;
        } else {
            Toast.makeText(this, R.string.validation_ok, Toast.LENGTH_LONG).show();
            mProgressDialog.setMessage("Creating account...");
            mProgressDialog.show();
            RegisterAccount(emailInput,passwordInput);
        }
    }

    private void RegisterAccount(String emailInput, String passwordInput) {
        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            mProgressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, user + getString(R.string.successfully_redirect), Toast.LENGTH_LONG).show();


                            //Firebase Analytics event:
                            Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, emailInput);
                            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Email");
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);

                        } else {
                            mProgressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, getString(R.string.register_email_failed), task.getException());
                            Toast.makeText(RegisterActivity.this, getString(R.string.error)+ task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    private boolean validateEmail () {
        emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()){
            textInputEmail.setError(getString(R.string.cant_be_empty));
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateUserName () {
        usernameInput = textInputName.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()){
            textInputName.setError(getString(R.string.cant_be_empty));
            return false;
        } else if (usernameInput.length() > 15 ) {
            textInputEmail.setError(getString(R.string.username_too_long));
            return false;
        } else {
            textInputEmail.setError(null);
            return true;

        }
    }

    private boolean validatePassword () {
        passwordInput = textInputPass.getEditText().getText().toString().trim();
        passwordInput2 = textInputPass2.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()){
            textInputPass.setError(getString(R.string.cant_be_empty));
            return false;
        } else if (passwordInput2.isEmpty()) {
            textInputPass2.setError(getString(R.string.cant_be_empty));
            return false;
        } else if (!passwordInput.equals(passwordInput2)) {
            textInputPass2.setError(getString(R.string.retype_password));
            return false;
        } else {
            textInputPass.setError(null);
            textInputPass2.setError(null);
            return true;
        }

    }


}
