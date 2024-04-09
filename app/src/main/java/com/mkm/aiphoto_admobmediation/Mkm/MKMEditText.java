package com.mkm.aiphoto_admobmediation.Mkm;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;

import com.mkm.aiphoto_admobmediation.Fragment.TextFragment;

public class MKMEditText extends AppCompatEditText {
    private TextFragment textFragment;

    public MKMEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setTextFragment(TextFragment textFragment) {
        this.textFragment = textFragment;
    }

    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        if (i == 4) {
            ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindowToken(), 0);
            this.textFragment.dismissAndShowSticker();
        }
        return false;
    }
}
