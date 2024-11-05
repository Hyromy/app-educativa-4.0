package com.example.app.preload_data;

import android.content.Context;

import com.example.app.preload_data.info.Admins;
import com.example.app.preload_data.info.Contenidos;
import com.example.app.preload_data.info.Examenes;
import com.example.app.preload_data.info.Temas;

public class Run {
    private Context context;

    public Run(Context context) {
        this.context = context;

        new Admins(this.context);
        new Temas(this.context);
        new Examenes(this.context);
        new Contenidos(this.context);
    }
}