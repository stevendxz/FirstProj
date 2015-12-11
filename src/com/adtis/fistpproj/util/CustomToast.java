package com.adtis.fistpproj.util;

import android.content.Context;
import android.widget.Toast;

public class CustomToast {
    public static void DisplayToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }
}
