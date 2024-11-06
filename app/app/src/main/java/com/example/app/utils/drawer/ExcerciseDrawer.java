package com.example.app.utils.drawer;

import android.content.Context;
import android.widget.TextView;

public class ExcerciseDrawer {
    private Context context;

    public ExcerciseDrawer(Context context) {
        this.context = context;
    }

    public TextView setTextView(String text) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setTextSize(20);

        return tv;
    }
}