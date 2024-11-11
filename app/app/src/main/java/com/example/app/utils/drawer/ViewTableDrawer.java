package com.example.app.utils.drawer;

import android.content.Context;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.app.R;

public class ViewTableDrawer {
    private Context context;

    public ViewTableDrawer(Context context) {
        this.context = context;
    }

    public TextView textView(String text) {
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setBackgroundResource(R.drawable.border);
        tv.setPadding(16, 16, 16, 16);
        tv.setGravity(Gravity.CENTER);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        tv.setLayoutParams(params);

        return tv;
    }
}