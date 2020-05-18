package com.naver.hackday.android.speechrecognizecalender.src.ui.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.naver.hackday.android.speechrecognizecalender.R;
import com.naver.hackday.android.speechrecognizecalender.databinding.ActivityMainBinding;
import com.naver.hackday.android.speechrecognizecalender.src.ApplicationClass;
import com.naver.hackday.android.speechrecognizecalender.src.BaseActivity;
import com.naver.hackday.android.speechrecognizecalender.src.common.util.SharedPreferenceManager;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.MonthlyEventsActivity;
import com.naver.hackday.android.speechrecognizecalender.src.ui.login.viewModels.LogInViewModel;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.EMAIL;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.GOOGLE_CLIENT_ID;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.GOOGLE_CLIENT_SECRET;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.SCOPE_URL;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.RC_SIGN_IN;

public class MainActivity extends BaseActivity implements AuthListener {

    private ActivityMainBinding mBinding;
    private LogInViewModel mLogInViewModel;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initStartView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mLogInViewModel = new ViewModelProvider(getViewModelStore(), viewModelFactory).get(LogInViewModel.class);
        mBinding.setViewModel(mLogInViewModel);
    }

    @Override
    protected void initDataBinding() { }

    @Override
    protected void initAfterBinding() {
        buildClient();
        mLogInViewModel.authListener = this;
        setSignInButton();
    }

    private void buildClient() {
        // Since the signIn() method and onActivityResult() method must be called within
        // activity, build a GoogleSignInClient in activity, rather than in LogInViewModel

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(SCOPE_URL))
                .requestIdToken(GOOGLE_CLIENT_ID)
                .requestServerAuthCode(GOOGLE_CLIENT_ID)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void setSignInButton() {
        SignInButton buttonSignIn = findViewById(R.id.activity_main_bt_signin);
        findViewById(R.id.activity_main_bt_signin).setOnClickListener(v -> {
            if (v.getId() == R.id.activity_main_bt_signin) {
                signIn();
            }
        });
    }

    private void signIn() {
        // Entry point when sign in button is clicked
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String email = account.getEmail();
            SharedPreferenceManager.setString(ApplicationClass.getApplicationClassContext(), EMAIL, email);
            System.out.println(email);
            // pass authorization code to LogInViewModel to request for access token.
            String authCode = account.getServerAuthCode();
            mLogInViewModel.requestAccessToken(authCode, GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET);

        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            showToast("로그인 실패");
        }
    }

    @Override
    public void onSuccessGetAccessToken() {
        Intent intent = new Intent(MainActivity.this, MonthlyEventsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailureGetAccessToken() {
        showToast("Access Token 받아오기 실패!");
    }

}
