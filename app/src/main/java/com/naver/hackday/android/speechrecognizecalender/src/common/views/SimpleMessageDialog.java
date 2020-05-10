package com.naver.hackday.android.speechrecognizecalender.src.common.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.naver.hackday.android.speechrecognizecalender.R;


public class SimpleMessageDialog extends Dialog implements View.OnClickListener {


    private String mMessage;
    private String mBtnText;
    private Context mContext;
    private TextView tvDesc;
    private TextView tvButton;

    private OnClickListener onClickListener;

    public SimpleMessageDialog(@NonNull Context context, String message, String btnText, boolean isCancelable, OnClickListener onClickListener) {
        super(context);
        mBtnText = btnText;
        mMessage = message;
        mContext = context;
        setCancelable(isCancelable);
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(Dialog dialog);
    }

    public SimpleMessageDialog(Builder builder) {
        this(builder.mContext, builder.mMessage, builder.mBtnText, builder.isCancelable, builder.onClickListener);
    }

    public static class Builder {
        private String mMessage = "";
        private String mBtnText = "";
        private Context mContext;
        private boolean isCancelable = true;
        private OnClickListener onClickListener;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        public Builder setButtonText(String btnText) {
            mBtnText = btnText;
            return this;
        }


        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        public Builder setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public SimpleMessageDialog build() {
            return new SimpleMessageDialog(this);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_simple_message);

        /* findViewByID */
        tvDesc = findViewById(R.id.tv_desc_dialog_simple_message);
        tvButton = findViewById(R.id.tv_positive_dialog_simple_message);

        /* Set View */
        tvDesc.setText(mMessage);
        tvButton.setText(mBtnText);

        /* Set On Click Listener */
        tvButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_positive_dialog_simple_message:
                if (onClickListener != null) {
                    onClickListener.onClick(this);
                } else {
                    dismiss();
                }
                break;
        }
    }
}
