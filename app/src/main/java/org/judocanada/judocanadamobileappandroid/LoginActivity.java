package org.judocanada.judocanadamobileappandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.judocanada.judocanadamobileappandroid.Api.ApiHelper;
import org.judocanada.judocanadamobileappandroid.Api.Callback;
import org.judocanada.judocanadamobileappandroid.Model.User;
import org.judocanada.judocanadamobileappandroid.Model.UserManager;

public class LoginActivity extends AppCompatActivity {
    private final static String VALID_USERNAME = "/^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$/";

    private EditText name, firstname, email, password, confirm, dateofBirth, judoid, loginemail, loginpassword;
    private Button btnConfirm, btnLoginConfirm;
    private LinearLayout userCreate, userLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userCreate = (LinearLayout) findViewById(R.id.userCreate);
        name = (EditText) findViewById(R.id.editName);
        firstname = (EditText) findViewById(R.id.editFirstName);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);
        confirm = (EditText) findViewById(R.id.editConfirm);
        dateofBirth = (EditText) findViewById(R.id.editDateofBirth);
        judoid = (EditText) findViewById(R.id.editJudoCanadaNumber);
        btnConfirm = (Button) findViewById(R.id.buttonConfirm);

        userLogin = (LinearLayout) findViewById(R.id.userLogin);
        loginemail = (EditText) findViewById(R.id.editLoginEmail);
        loginpassword = (EditText) findViewById(R.id.editLoginPassword);
        btnLoginConfirm = (Button) findViewById(R.id.buttonLoginConfirm);

        Button btnToLogin = (Button) findViewById(R.id.buttonToLogin);
        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCreate.setVisibility(View.GONE);
                userLogin.setVisibility(View.VISIBLE);
            }
        });

        Button btnSignup = (Button) findViewById(R.id.buttonSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCreate.setVisibility(View.VISIBLE);
                userLogin.setVisibility(View.GONE);
            }
        });

        name.addTextChangedListener(new TextValidator(name) {
            @Override
            public void validate(TextView textView, String text) {

            }
        });

        password.addTextChangedListener(new TextValidator(password) {
            @Override
            public void validate(TextView textView, String text) {

            }
        });

        confirm.addTextChangedListener(new TextValidator(confirm) {
            @Override
            public void validate(TextView textView, String text) {

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User user = new User();
                user.setName(name.getText().toString());
                user.setFirstname(firstname.getText().toString());
                user.setPassword(password.getText().toString());
                user.setEmail(email.getText().toString());
                user.setDateofbirth(dateofBirth.getText().toString());
                user.setJudoCanadaId(Integer.parseInt(judoid.getText().toString()));
                UserManager.getInstance().setUser(user);
                UserManager.getInstance().saveUserToPreferences(getApplicationContext());
                ApiHelper api = new ApiHelper(getApplicationContext());
                api.postUser(user, getApplicationContext(), new Callback() {
                    @Override
                    public void methodToCallBack(Object object) {
                        if (object!=null){
                            int id = Integer.parseInt(object.toString());
                            user.setId(id);
                        }
                    }
                });

                Intent result;
                result= new Intent(LoginActivity.this, MainActivity.class);
                startActivity(result);
                finish();
            }
        });

        btnLoginConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                UserManager.getInstance().setUser(user);
                UserManager.getInstance().saveUserToPreferences(getApplicationContext());
                Intent result;
                result= new Intent(LoginActivity.this, MainActivity.class);
                startActivity(result);
                finish();
            }
        });


    }

    public abstract class TextValidator implements TextWatcher {
        private final TextView textView;

        public TextValidator(TextView textView) {
            this.textView = textView;
        }

        public abstract void validate(TextView textView, String text);

        @Override
        final public void afterTextChanged(Editable s) {
            String text = textView.getText().toString();
            validate(textView, text);
        }

        @Override
        final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

        @Override
        final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
    }

}
