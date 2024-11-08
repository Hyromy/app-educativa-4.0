package com.example.app.utils.drawer;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.app.db.models.RespuestaActividadModel;
import com.example.app.db.models.RespuestaExamenModel;

public class ExcerciseDrawer {
    private Context context;

    public ExcerciseDrawer(Context context) {
        this.context = context;
    }

    public TextView[] setTextViews(String text) {
        String[] lines = text.split("\n", -1);
        TextView[] tvs = new TextView[lines.length];
        TextView tv;
        for (int i = 0; i < lines.length; i++) {
            tv = new TextView(context);
            tv.setText(lines[i]);
            tv.setTextSize(20);

            if (i == 0) {
                tv.setPadding(0, 0, 0, 32);
            } else {
                tv.setPadding(32, 0, 0, 0);
            }
            tvs[i] = tv;
        }

        return tvs;
    }

    public RadioGroup setRadioGroup(RadioButton[] rbs, String tag) {
        RadioGroup rg = new RadioGroup(context);
        rg.setOrientation(RadioGroup.VERTICAL);
        rg.setTag("rg_" + tag);
        rg.setPadding(32, 32, 0, 0);

        for (RadioButton rb : rbs) {
            rg.addView(rb);
        }

        return rg;
    }

    public RadioButton[] setRadioButtons(RespuestaExamenModel[] answers) {
        int size = answers.length;
        RadioButton rbs[] = new RadioButton[size];

        RadioButton rb;
        for (int i = 0; i < size; i++) {
            rb = new RadioButton(context);
            rb.setId(View.generateViewId());
            rb.setTag(String.valueOf(answers[i].puntajeValue));
            rb.setText(answers[i].textoValue);

            rbs[i] = rb;
        }

        return rbs;
    }

    public RadioButton[] setRadioButtons(RespuestaActividadModel[] answers) {
        int size = answers.length;
        RadioButton rbs[] = new RadioButton[size];

        RadioButton rb;
        for (int i = 0; i < size; i++) {
            rb = new RadioButton(context);
            rb.setId(View.generateViewId());

            if (answers[i].esCorrectoValue) {
                rb.setTag("1");
            } else {
                rb.setTag("0");
            }
            rb.setText(answers[i].textoValue);

            rbs[i] = rb;
        }

        return rbs;
    }
}