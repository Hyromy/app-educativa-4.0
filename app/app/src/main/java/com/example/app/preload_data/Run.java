package com.example.app.preload_data;

import android.content.Context;
import com.example.app.preload_data.info.*;

public class Run {
    public Run(Context context) {
        new Usuarios(context);
        new Temas(context);
        new Examenes(context);
        new Contenidos(context);
        new PreguntasExamen(context);
        new RespuestasExamen(context);
        new PreguntasActividad(context);
        new RespuestasActividad(context);
    }
}