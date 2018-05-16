package org.judocanada.judocanadamobileappandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginActivity extends AppCompatActivity {

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



    }
}
