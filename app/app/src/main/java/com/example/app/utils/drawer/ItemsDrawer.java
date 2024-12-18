package com.example.app.utils.drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.PreguntaActividadModel;
import com.example.app.db.models.PreguntaExamenModel;
import com.example.app.db.models.RecursoModel;
import com.example.app.db.models.RespuestaActividadModel;
import com.example.app.db.models.RespuestaExamenModel;
import com.example.app.db.models.TemaModel;
import com.example.app.db.utils.crud.Contenido;
import com.example.app.db.utils.crud.ExamenDiagnostico;
import com.example.app.db.utils.crud.PreguntaActividad;
import com.example.app.db.utils.crud.PreguntaExamen;
import com.example.app.db.utils.crud.Recurso;
import com.example.app.db.utils.crud.RespuestaActividad;
import com.example.app.db.utils.crud.RespuestaExamen;
import com.example.app.db.utils.crud.Tema;
import com.example.app.ui.admin.query.items.ItemFragment;
import com.example.app.utils.ImageHelper;

import java.io.File;

public class ItemsDrawer {
    private LinearLayout subLayout;

    private LinearLayout subLayout(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setTag("sub_layout");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);

        return layout;
    }

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

    private RadioGroup rGroup(Context context, RadioButton[] radios, String tag, LinearLayout layout, Button btnSave) {
        RadioGroup rg = new RadioGroup(context);
        rg.setTag("rg_" + tag);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 32, 0, 32);
        rg.setLayoutParams(params);

        for (RadioButton radio : radios) {
            rg.addView(radio);
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                String tag = rb.getTag().toString();

                if (layout.findViewWithTag("sub_layout") == null) {
                    subLayout = subLayout(context);
                    layout.addView(subLayout);
                }

                subLayout.removeAllViews();

                if (tag.equals("rb_p_examen")) {
                    ItemFragment.table = "pregunta_examen";
                    loadPreguntaExamen(context, subLayout);
                } else if (tag.equals("rb_p_actividad")) {
                    ItemFragment.table = "pregunta_actividad";
                    loadPreguntaActividad(context, subLayout);

                } else if (tag.equals("rb_r_examen")) {
                    ItemFragment.table = "respuesta_examen";
                    loadRespuestaExamen(context, subLayout);
                } else if (tag.equals("rb_r_actividad")) {
                    ItemFragment.table = "respuesta_actividad";
                    loadRespuestaActividad(context, subLayout);
                }
            }
        });

        return rg;
    }

    private RadioButton setRadio(Context context, String text, String tag) {
        RadioButton rb = new RadioButton(context);
        rb.setId(View.generateViewId());
        rb.setTag("rb_" + tag);
        rb.setText(text);

        return rb;
    }

    private CheckBox setCheckbox(Context context, String tag, String text) {
        CheckBox cb = new CheckBox(context);
        cb.setId(View.generateViewId());
        cb.setTag(tag);
        cb.setText(text);

        return cb;
    }

    private ImageView setImage(Context context, String tag) {
        ImageView img = new ImageView(context);
        img.setId(View.generateViewId());
        img.setTag(tag + "_img");

        return img;
    }

    private Button setButton(Context context, String text, String tag, View.OnClickListener listener) {
        Button btn = new Button(context);
        btn.setId(View.generateViewId());
        btn.setTag(tag + "_btn");
        btn.setText(text);
        btn.setOnClickListener(listener);

        return btn;
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

        textView = setLabel(context, "Nivel del contenido");
        layout.addView(textView);
        String[] strA = new String[10];
        for (int i = 0; i < 10; i++) {
            strA[i] = "(" + i + ")";
        }
        spinner = setSpinner(context, strA, "nivel_contenido");
        layout.addView(spinner);

        textView = setLabel(context, "Número de preguntas");
        layout.addView(textView);
        editText = setEntry(context, 'n', "n_preguntas", 3);
        layout.addView(editText);
    }

    public void loadPregunta(Context context, LinearLayout layout, Button btnSave) {
        TextView textView = setLabel(context, "Tipo de pregunta");
        layout.addView(textView);
        RadioButton[] radios = {
            setRadio(context, "Exámen", "p_examen"),
            setRadio(context, "Actividad", "p_actividad")
        };
        RadioGroup rg = rGroup(context, radios, "tipoPregunta", layout, btnSave);
        layout.addView(rg);
    }

    private void loadPreguntaExamen(Context context, LinearLayout layout) {
        TextView textView = setLabel(context, "Exámen");
        layout.addView(textView);

        ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
        crudExamen.open();
        ExamenDiagnosticoModel[] examenes = crudExamen.readAll();
        crudExamen.close();

        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel[] temas = crudTema.readAll();
        crudTema.close();

        String[] items = new String[examenes.length];
        for (int i = 0; i < examenes.length; i++) {
            items[i] = "(" + examenes[i].idValue + ") " + examenes[i].tituloValue + " [" + temas[examenes[i].idTemaValue - 1].tituloValue + "]";
        }
        Spinner spinner = setSpinner(context, items, "examen");
        layout.addView(spinner);

        textView = setLabel(context, "Texto de la pregunta");
        layout.addView(textView);
        EditText editText = setEntry(context, 'a', "texto", 250);
        layout.addView(editText);
    }

    private void loadPreguntaActividad(Context context, LinearLayout layout) {
        TextView textView = setLabel(context, "Contenido");
        layout.addView(textView);

        Contenido crudContenido = new Contenido(context);
        crudContenido.open();
        ContenidoModel[] contenidos = crudContenido.readAll();
        crudContenido.close();

        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel[] temas = crudTema.readAll();
        crudTema.close();

        String[] items = new String[contenidos.length];
        for (int i = 0; i < contenidos.length; i++) {
            items[i] = "(" + contenidos[i].idValue + ") " + contenidos[i].tituloValue + " [" + temas[contenidos[i].idTemaValue - 1].tituloValue + " - nv." + contenidos[i].nivelValue + "]";
        }
        Spinner spinner = setSpinner(context, items, "contenido");
        layout.addView(spinner);

        textView = setLabel(context, "Texto de la pregunta");
        layout.addView(textView);
        EditText editText = setEntry(context, 'a', "texto", 250);
        layout.addView(editText);
    }

    public void loadRespuesta(Context context, LinearLayout layout, Button btnSave) {
        TextView textView = setLabel(context, "Tipo de respuesta");
        layout.addView(textView);
        RadioButton[] radios = {
                setRadio(context, "Exámen", "r_examen"),
                setRadio(context, "Actividad", "r_actividad")
        };
        RadioGroup rg = rGroup(context, radios, "tipoRespuesta", layout, btnSave);
        layout.addView(rg);
    }

    private void loadRespuestaExamen(Context context, LinearLayout layout) {
        TextView textView = setLabel(context, "Pregunta");
        layout.addView(textView);

        PreguntaExamen crudPreguntaExamen = new PreguntaExamen(context);
        crudPreguntaExamen.open();
        PreguntaExamenModel[] preguntasExamen = crudPreguntaExamen.readAll();
        crudPreguntaExamen.close();

        ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
        crudExamen.open();
        ExamenDiagnosticoModel[] examenes = crudExamen.readAll();
        crudExamen.close();

        String[] items = new String[preguntasExamen.length];
        for (int i = 0; i < preguntasExamen.length; i++) {
            items[i] = "(" + preguntasExamen[i].idValue + ") " + preguntasExamen[i].textoValue + " [" + examenes[preguntasExamen[i].idExamenValue - 1].tituloValue + "]";
        }
        Spinner spinner = setSpinner(context, items, "pregunta_examen");
        layout.addView(spinner);

        textView = setLabel(context, "Texto de la respuesta");
        layout.addView(textView);
        EditText editText = setEntry(context, 'a', "texto", 250);
        layout.addView(editText);

        textView = setLabel(context, "Puntuación de respuesta");
        layout.addView(textView);
        editText = setEntry(context, 'n', "puntaje", 1);
        layout.addView(editText);
    }

    private void loadRespuestaActividad(Context context, LinearLayout layout) {
        TextView textView = setLabel(context, "Pregunta");
        layout.addView(textView);

        PreguntaActividad crudPreguntaActividad = new PreguntaActividad(context);
        crudPreguntaActividad.open();
        PreguntaActividadModel[] preguntasActividad = crudPreguntaActividad.readAll();
        crudPreguntaActividad.close();

        Contenido crudContenido = new Contenido(context);
        crudContenido.open();
        ContenidoModel[] contenidos = crudContenido.readAll();
        crudContenido.close();

        String[] items = new String[preguntasActividad.length];
        for (int i = 0; i < preguntasActividad.length; i++) {
            items[i] = "(" + preguntasActividad[i].idValue + ") " + preguntasActividad[i].textoValue + " [" + contenidos[preguntasActividad[i].idContenidoValue - 1].tituloValue + "]";
        }
        Spinner spinner = setSpinner(context, items, "pregunta_actividad");
        layout.addView(spinner);

        textView = setLabel(context, "Texto de la respuesta");
        layout.addView(textView);
        EditText editText = setEntry(context, 'a', "texto", 250);
        layout.addView(editText);

        textView = setLabel(context, "Respuesta correcta");
        layout.addView(textView);
        CheckBox checkBox = setCheckbox(context, "es_correcto", "Respuesta correcta");
        layout.addView(checkBox);
    }

    public void loadRecurso(Context context, LinearLayout layout) {
        ImageView imageView = setImage(context, "recurso");
        layout.addView(imageView);
        Button btn = setButton(context, "seleccionar imagen", "select_image", null);
        layout.addView(btn);
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
        Spinner nivelContenidoS = layout.findViewWithTag("nivel_contenido_spinner");
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
        nivelContenidoS.setSelection(contenido.nivelValue);
        nPreguntasET.setText(String.valueOf(contenido.nPreguntasValue));
    }

    public void extractPreguntaExamen(Context context, LinearLayout layout, int id) {
        RadioGroup rg = layout.findViewWithTag("rg_tipoPregunta");
        for (int i = 0; i < rg.getChildCount(); i++) {
            View child = rg.getChildAt(i);
            if (child instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) child;
                if (radioButton.getTag().equals("rb_p_examen")) {
                    radioButton.setChecked(true);
                } else {
                    radioButton.setVisibility(View.GONE);
                }
            }
        }

        Spinner spinner = layout.findViewWithTag("examen_spinner");
        EditText textoET = layout.findViewWithTag("texto");

        PreguntaExamen crudPreguntaExamen = new PreguntaExamen(context);
        crudPreguntaExamen.open();
        PreguntaExamenModel preguntaExamen = crudPreguntaExamen.read(id);
        crudPreguntaExamen.close();

        ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
        crudExamen.open();
        ExamenDiagnosticoModel[] examenes = crudExamen.readAll();
        crudExamen.close();
        for (int i = 0; i < examenes.length; i++) {
            if (preguntaExamen.idExamenValue == examenes[i].idValue) {
                spinner.setSelection(i);
                break;
            }
        }
        textoET.setText(preguntaExamen.textoValue);
    }

    public void extractPreguntaActividad(Context context, LinearLayout layout, int id) {
        RadioGroup rg = layout.findViewWithTag("rg_tipoPregunta");
        for (int i = 0; i < rg.getChildCount(); i++) {
            View child = rg.getChildAt(i);
            if (child instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) child;
                if (radioButton.getTag().equals("rb_p_actividad")) {
                    radioButton.setChecked(true);
                } else {
                    radioButton.setVisibility(View.GONE);
                }
            }
        }

        Spinner spinner = layout.findViewWithTag("contenido_spinner");
        EditText textoET = layout.findViewWithTag("texto");

        PreguntaActividad crudPreguntaActividad = new PreguntaActividad(context);
        crudPreguntaActividad.open();
        PreguntaActividadModel preguntaActividad = crudPreguntaActividad.read(id);
        crudPreguntaActividad.close();

        Contenido crudContenido = new Contenido(context);
        crudContenido.open();
        ContenidoModel[] contenidos = crudContenido.readAll();
        crudContenido.close();
        for (int i = 0; i < contenidos.length; i++) {
            if (preguntaActividad.idContenidoValue == contenidos[i].idValue) {
                spinner.setSelection(i);
                break;
            }
        }
        textoET.setText(preguntaActividad.textoValue);
    }

    public void extractRespuestaExamen(Context context, LinearLayout layout, int id) {
        RadioGroup rg = layout.findViewWithTag("rg_tipoRespuesta");
        for (int i = 0; i < rg.getChildCount(); i++) {
            View child = rg.getChildAt(i);
            if (child instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) child;
                if (radioButton.getTag().equals("rb_r_examen")) {
                    radioButton.setChecked(true);
                } else {
                    radioButton.setVisibility(View.GONE);
                }
            }
        }

        Spinner spinner = layout.findViewWithTag("pregunta_examen_spinner");
        EditText textoET = layout.findViewWithTag("texto");
        EditText puntajeET = layout.findViewWithTag("puntaje");

        RespuestaExamen crudRespuestaExamen = new RespuestaExamen(context);
        crudRespuestaExamen.open();
        RespuestaExamenModel respuestaExamen = crudRespuestaExamen.read(id);
        crudRespuestaExamen.close();

        PreguntaExamen crudPreguntaExamen = new PreguntaExamen(context);
        crudPreguntaExamen.open();
        PreguntaExamenModel[] preguntasExamen = crudPreguntaExamen.readAll();
        crudPreguntaExamen.close();

        for (int i = 0; i < preguntasExamen.length; i++) {
            if (respuestaExamen.idPreguntaExamenValue == preguntasExamen[i].idValue) {
                spinner.setSelection(i);
                break;
            }
        }
        textoET.setText(respuestaExamen.textoValue);
        puntajeET.setText(String.valueOf(respuestaExamen.puntajeValue));
    }

    public void extractRespuestaActividad(Context context, LinearLayout layout, int id) {
        RadioGroup rg = layout.findViewWithTag("rg_tipoRespuesta");
        for (int i = 0; i < rg.getChildCount(); i++) {
            View child = rg.getChildAt(i);
            if (child instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) child;
                if (radioButton.getTag().equals("rb_r_actividad")) {
                    radioButton.setChecked(true);
                } else {
                    radioButton.setVisibility(View.GONE);
                }
            }
        }

        Spinner spinner = layout.findViewWithTag("pregunta_actividad_spinner");
        EditText textoET = layout.findViewWithTag("texto");
        CheckBox esCorrectoCB = layout.findViewWithTag("es_correcto");

        RespuestaActividad crudRespuestaActividad = new RespuestaActividad(context);
        crudRespuestaActividad.open();
        RespuestaActividadModel respuestaActividad = crudRespuestaActividad.read(id);
        crudRespuestaActividad.close();

        PreguntaActividad crudPreguntaActividad = new PreguntaActividad(context);
        crudPreguntaActividad.open();
        PreguntaActividadModel[] preguntasActividad = crudPreguntaActividad.readAll();
        crudPreguntaActividad.close();

        for (int i = 0; i < preguntasActividad.length; i++) {
            if (respuestaActividad.idPreguntaActividadValue == preguntasActividad[i].idValue) {
                spinner.setSelection(i);
                break;
            }
        }
        textoET.setText(respuestaActividad.textoValue);
        esCorrectoCB.setChecked(respuestaActividad.esCorrectoValue);
    }

    public void extractRecurso(Context context, LinearLayout layout, int id) {
        Recurso crudRecurso = new Recurso(context);
        crudRecurso.open();
        RecursoModel recurso = crudRecurso.read(id);
        crudRecurso.close();

        String fileName = recurso.nombreValue + "." + recurso.extensionValue;
        File storageDir = context.getFilesDir();
        File imageFile = new File(storageDir, fileName);

        ImageView imageView = layout.findViewWithTag("recurso_img");

        if (imageFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            imageView.setImageBitmap(bitmap);
        } else {
            int resId = context.getResources().getIdentifier(recurso.nombreValue, "drawable", context.getPackageName());
            if (resId != 0) {
                imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), resId, null));
            } else {
                Toast.makeText(context, "No se encontró la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }
}