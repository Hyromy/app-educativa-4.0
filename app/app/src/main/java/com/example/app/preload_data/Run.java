package com.example.app.preload_data;

import android.content.Context;
import com.example.app.preload_data.info.*;

public class Run {
    public Run(Context context) {
        new Usuarios(context);
        new Temas(context);
        new Recursos(context);

        new Contenidos(context);
        new Examenes(context);

        new PreguntasExamen(context);
        new PreguntasActividad(context);

        new RespuestasExamen(context);
        new RespuestasActividad(context);
    }
}