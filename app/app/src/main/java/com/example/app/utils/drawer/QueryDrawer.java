package com.example.app.utils.drawer;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.app.R;

public class QueryDrawer {
    public LinearLayout linearContainer(Context context, int paddingDP, int marginDP, String tag) {
        LinearLayout linearContainer = new LinearLayout(context);
        linearContainer.setId(View.generateViewId());
        linearContainer.setTag(tag);
        linearContainer.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(marginDP, marginDP, marginDP, marginDP);
        linearContainer.setLayoutParams(params);
        linearContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_box));
        linearContainer.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.Greeuttec));
        linearContainer.setPadding(paddingDP, paddingDP, paddingDP, paddingDP);

        return linearContainer;
    }

    public LinearLayout linearInfo(Context context) {
        LinearLayout linearInfo = new LinearLayout(context);
        linearInfo.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.weight = 1;
        linearInfo.setLayoutParams(params);

        return linearInfo;
    }

    public TextView textView(Context context, String type, String text) {
        TextView textView = new TextView(context);
        textView.setText(text);
        if (type.toLowerCase().equals("id")) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            textView.setTextSize(36);
            textView.setPadding(0, 0, 24, 0);

        } else if (type.toLowerCase().equals("tx")) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER_VERTICAL);
        }

        return textView;
    }

    public ImageView imageView(Context context, String tag, int resID) {
        ImageView imageView = new ImageView(context);
        imageView.setId(View.generateViewId());
        imageView.setTag("delete_" + tag);
        imageView.setImageResource(resID);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        imageView.setLayoutParams(params);
        return imageView;
    }
}