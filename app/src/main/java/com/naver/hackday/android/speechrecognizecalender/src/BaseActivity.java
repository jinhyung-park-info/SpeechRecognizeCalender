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
     * 레이아웃을 띄운 직후 호출.
     * 뷰나 액티비티의 속성 등을 초기화.
     * ex) 리사이클러뷰, 툴바, 드로어뷰..
     */
    protected abstract void initStartView();

    /**
     * 두번째로 호출.
     * 데이터 바인딩 및 observe 설정.
     * ex)databinding observe..
     */
    protected abstract void initDataBinding();

    /**
     * 바인딩 이후에 할 일을 여기에 구현.
     * 그 외에 설정할 것이 있으면 이곳에서 설정.
     * 클릭 리스너도 이곳에서 설정.
     */
    protected abstract void initAfterBinding();

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View view = getCurrentFocus();
//            if (view instanceof EditText) {
//                Rect outRect = new Rect();
//                view.getGlobalVisibleRect(outRect);
//                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
//                    view.clearFocus();
//                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }

}
