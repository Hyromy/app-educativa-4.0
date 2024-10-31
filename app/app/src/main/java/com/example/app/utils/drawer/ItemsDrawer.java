package com.example.app.utils.drawer;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.TemaModel;
import com.example.app.db.utils.crud.Contenido;
import com.example.app.db.utils.crud.ExamenDiagnostico;
import com.example.app.db.utils.crud.Tema;

public class ItemsDrawer {
    private TextView setLabel(Context context, String text) {
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(params);
        tv.setText(text);

        return tv;
    }

    private EditText setEntry(Context context, char type, String tag, int max) {
        EditText et = new EditText(context);
        et.setId(View.generateViewId());
        et.setTag(tag);
        et.setFilters(new InputFilter[] {new InputFilter.LengthFilter(max)});

        if (type == 't') {
            et.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (type == 'n') {
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (type == 'a') {
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            et.setMinLines(3);
            et.setMaxLines(5);
        }

        return et;
    }

    private Spinner setSpinner(Context context, String[] items, String tag) {
        Spinner spinner = new Spinner(context);
        spinner.setId(View.generateViewId());
        spinner.setTag(tag + "_spinner");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 32);
        spinner.setLayoutParams(params);

        return spinner;
    }

    public void loadTema(Context context, LinearLayout layout) {
        TextView textView = setLabel(context, "Título");
        layout.addView(textView);
        EditText editText = setEntry(context, 't', "titulo", 100);
        layout.addView(editText);

        textView = setLabel(context, "Descripción");
        layout.addView(textView);
        editText = setEntry(context, 'a', "descripcion", 250);
        layout.addView(editText);
    }

    public void loadExamen(Context context, LinearLayout layout) {
        TextView textView = setLabel(context, "Tema");
        layout.addView(textView);
        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel[] temas = crudTema.readAll();
        String[] items = new String[temas.length];
        for (int i = 0; i < temas.length; i++) {
            items[i] = "(" + temas[i].idValue + ") " + temas[i].tituloValue;
        }
        crudTema.close();
        Spinner spinner = setSpinner(context, items, "tema");
        layout.addView(spinner);

        textView = setLabel(context, "Título");
        layout.addView(textView);
        EditText editText = setEntry(context, 't', "titulo", 100);
        layout.addView(editText);

        textView = setLabel(context, "Número de preguntas");
        layout.addView(textView);
        editText = setEntry(context, 'n', "n_preguntas", 3);
        layout.addView(editText);

        textView = setLabel(context, "Nivel máximo");
        layout.addView(textView);
        editText = setEntry(context, 'n', "nivel_maximo", 1);
        layout.addView(editText);
    }

    public void loadContenido(Context context, LinearLayout layout) {
        TextView textView = setLabel(context, "Tema");
        layout.addView(textView);
        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel[] temas = crudTema.readAll();
        String[] items = new String[temas.length];
        for (int i = 0; i < temas.length; i++) {
            items[i] = "(" + temas[i].idValue + ") " + temas[i].tituloValue;
        }
        crudTema.close();
        Spinner spinner = setSpinner(context, items, "tema");
        layout.addView(spinner);

        textView = setLabel(context, "Título");
        layout.addView(textView);
        EditText editText = setEntry(context, 't', "titulo", 50);
        layout.addView(editText);

        textView = setLabel(context, "Descripción");
        layout.addView(textView);
        editText = setEntry(context, 'a', "descripcion", 100);
        layout.addView(editText);

        textView = setLabel(context, "Nivel del contenido [reemplazar por un spinner]");
        layout.addView(textView);
        editText = setEntry(context, 'n', "nivel_contenido", 1);
        layout.addView(editText);

        textView = setLabel(context, "Número de preguntas");
        layout.addView(textView);
        editText = setEntry(context, 'n', "n_preguntas", 3);
        layout.addView(editText);
    }

    public void loadPregunta(Context context, LinearLayout layout) {
        Toast.makeText(context, "Cargando pregunta", Toast.LENGTH_SHORT).show();
    }

    public void loadActividad(Context context, LinearLayout layout) {
        Toast.makeText(context, "Cargando actividad", Toast.LENGTH_SHORT).show();
    }

    public void extractTema(Context context, LinearLayout layout, int id) {
        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel tema = crudTema.read(id);
        crudTema.close();

        EditText tituloET = layout.findViewWithTag("titulo");
        EditText descripcionET = layout.findViewWithTag("descripcion");

        tituloET.setText(tema.tituloValue);
        descripcionET.setText(tema.descripcionValue);
    }

    public void extractExamenDiagnostico(Context context, LinearLayout layout, int id) {
        Spinner spinner = layout.findViewWithTag("tema_spinner");
        EditText tituloET = layout.findViewWithTag("titulo");
        EditText nPreguntasET = layout.findViewWithTag("n_preguntas");
        EditText nivelMaximoET = layout.findViewWithTag("nivel_maximo");

        ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
        crudExamen.open();
        ExamenDiagnosticoModel examen = crudExamen.read(id);
        crudExamen.close();

        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel[] temas = crudTema.readAll();
        crudTema.close();
        for (int i = 0; i < temas.length; i++) {
            if (examen.idTemaValue == temas[i].idValue) {
                spinner.setSelection(i);
                break;
            }
        }
        tituloET.setText(examen.tituloValue);
        nPreguntasET.setText(String.valueOf(examen.nPreguntasValue));
        nivelMaximoET.setText(String.valueOf(examen.nivelMaximoValue));
    }

    public void extractContenido(Context context, LinearLayout layout, int id) {
        Spinner spinner = layout.findViewWithTag("tema_spinner");
        EditText tituloET = layout.findViewWithTag("titulo");
        EditText descripcionET = layout.findViewWithTag("descripcion");
        EditText nivelContenidoET = layout.findViewWithTag("nivel_contenido");
        EditText nPreguntasET = layout.findViewWithTag("n_preguntas");

        Contenido crudContenido = new Contenido(context);
        crudContenido.open();
        ContenidoModel contenido = crudContenido.read(id);
        crudContenido.close();

        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel[] temas = crudTema.readAll();
        crudTema.close();
        for (int i = 0; i < temas.length; i++) {
            if (contenido.idTemaValue == temas[i].idValue) {
                spinner.setSelection(i);
                break;
            }
        }
        tituloET.setText(contenido.tituloValue);
        descripcionET.setText(contenido.descripcionValue);
        nivelContenidoET.setText(String.valueOf(contenido.nivelValue));
        nPreguntasET.setText(String.valueOf(contenido.nPreguntasValue));
    }

    public void extractPreguntaExamen(Context context, LinearLayout layout, int id) {
        Toast.makeText(context, "Extrayendo pregunta", Toast.LENGTH_SHORT).show();
    }

    public void extractPreguntaActividad(Context context, LinearLayout layout, int id) {
        Toast.makeText(context, "Extrayendo actividad", Toast.LENGTH_SHORT).show();
    }
}