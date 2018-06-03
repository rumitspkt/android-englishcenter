package com.example.mobileda.englishcenter.utility;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class Utilities {
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static boolean haveBlank(Context context ,EditText[] items){
        for (EditText item: items
             ) {
            if(item.getText().toString().equals("")){
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!!", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }
}
