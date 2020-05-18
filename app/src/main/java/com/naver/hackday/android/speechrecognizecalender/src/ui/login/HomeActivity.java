package com.naver.hackday.android.speechrecognizecalender.src.ui.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.naver.hackday.android.speechrecognizecalender.R;

public class HomeActivity extends AppCompatActivity {

    // TODO : After event-related activity is implemented, this activity will be deleted

    private Button buttonSignOut;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        buttonSignOut = findViewById(R.id.activity_home_bt_sign_out);
        buttonSignOut.setOnClickListener(v -> {
            if (v.getId() == R.id.activity_home_bt_sign_out) {
                signOut();
            }
        });

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(HomeActivity.this, "정상적으로 로그아웃되었습니다", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}
