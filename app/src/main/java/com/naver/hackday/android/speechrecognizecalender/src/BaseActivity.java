package com.naver.hackday.android.speechrecognizecalender.src;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.naver.hackday.android.speechrecognizecalender.R;
import com.naver.hackday.android.speechrecognizecalender.src.common.views.SimpleMessageDialog;

import org.jetbrains.annotations.Nullable;

public abstract class BaseActivity extends AppCompatActivity {
    public ProgressDialog mProgressDialog;
    public SimpleMessageDialog mSimpleMessageDialog;

    public ViewModelProvider.AndroidViewModelFactory viewModelFactory;

    public void startNextActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void showToast(final String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
        }
        if (!isFinishing()) {
            mProgressDialog.show();
        }
    }

    public void showSimpleMessageDialog(String message, String btnText, boolean isCancelable, SimpleMessageDialog.OnClickListener onClickListener) {
        mSimpleMessageDialog = new SimpleMessageDialog.Builder(this)
                .setMessage(message)
                .setButtonText(btnText)
                .setCancelable(isCancelable)
                .setOnClickListener(onClickListener)
                .build();
        mSimpleMessageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mSimpleMessageDialog.show();
    }

    public void showSimpleMessageDialog(String message) {
        showSimpleMessageDialog(message, getString(R.string.confirm), true, Dialog::dismiss);
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (viewModelFactory == null) {
            viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        }
        initStartView();
        initDataBinding();
        initAfterBinding();
    }

    /**
     * ??????????????? ?????? ?????? ??????.
     * ?????? ??????????????? ?????? ?????? ?????????.
     * ex) ??????????????????, ??????, ????????????..
     */
    protected abstract void initStartView();

    /**
     * ???????????? ??????.
     * ????????? ????????? ??? observe ??????.
     * ex)databinding observe..
     */
    protected abstract void initDataBinding();

    /**
     * ????????? ????????? ??? ?????? ????????? ??????.
     * ??? ?????? ????????? ?????? ????????? ???????????? ??????.
     * ?????? ???????????? ???????????? ??????.
     */
    protected abstract void initAfterBinding();

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

}
