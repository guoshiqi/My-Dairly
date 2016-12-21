package com.name.cn.mydiary.function.home;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.name.cn.mydiary.R;

/**
 * 包含输入的dialog view
 * Created by guoshiqi on 2016/12/21.
 */

public class CustomDialogInputView extends RelativeLayout {

    private AppCompatEditText editText;

    public CustomDialogInputView(Context context) {
        super(context);
        init(context);
    }

    public CustomDialogInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomDialogInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.dialog_addbook_layout, this);
        editText = (AppCompatEditText) root.findViewById(R.id.input_text);
    }

    public String getInput() {
        return editText.getText().toString().trim();
    }
}
