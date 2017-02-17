package ashutosh.bdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

/**
 * Created by ashutosh on 16/2/17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String PASSWORD = "pass";

    MEditText userName, password;
    AppCompatButton loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (MEditText) findViewById(R.id.et_login_username);
        password = (MEditText) findViewById(R.id.et_login_password);
        loginButton = (AppCompatButton) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (userName.getText().toString().trim().length() < 1 || password.getText().toString().trim().length() < 1) {
                    Toast.makeText(LoginActivity.this, "Username or Password can't be empty!", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().trim().equals(PASSWORD)) {
                    AccomplishmentBox.saveUsername(LoginActivity.this, userName.getText().toString().trim());
                    startActivity(new Intent(LoginActivity.this, CouponActivity.class));
                } else
                    Toast.makeText(LoginActivity.this, "Your Username or Password is incorrect!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
