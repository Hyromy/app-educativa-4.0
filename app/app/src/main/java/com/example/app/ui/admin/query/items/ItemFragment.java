package com.example.app.ui.admin.query.items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.TemaModel;
import com.example.app.db.utils.crud.ExamenDiagnostico;
import com.example.app.db.utils.crud.Tema;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemFragment extends Fragment {
    private String table = null;
    private int id;
    private LinearLayout layout;
    private Button btnSave;

    private ItemViewModel mViewModel;

    public static ItemFragment newInstance() {
        return new ItemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = view.findViewById(R.id.linear_layout);
        btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(saveItem);

        Bundle bundle = getArguments();
        if (bundle != null) {
            table = bundle.getString("table");
            loadFormOf(table, getContext());

            id = bundle.getInt("id");
            if (id > 0) {
                extractData(table);
                setToolbarTitle("Editar registro");
            } else {
                setToolbarTitle("Crear registro");
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setToolbarTitle(String title) {
        if (requireActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) requireActivity();
            activity.getSupportActionBar().setTitle(title);
        }
    }

    // -----------------------------------
    // ---- CARGAR FORMUARIO DESDE... ----
    // -----------------------------------
    private void loadFormOf(String table, Context context) {
        if (table.equals("tema")) {
            loadTema(context);
        } else if (table.equals("examen_diagnostico")) {
            loadExamen(context);
        } else if (table.equals("pregunta_examen")) {
            loadPregunta();
        } else if (table.equals("pregunta_actividad")) {
            loadActividad();
        } else if (table.equals("contenido")) {
            loadContenido();
        }
    }

    private void loadTema(Context context) {
        TextView textView = setLabel(context, "Título");
        layout.addView(textView);
        EditText editText = setEntry(context, 't', "titulo", 100);
        layout.addView(editText);

        textView = setLabel(context, "Descripción");
        layout.addView(textView);
        editText = setEntry(context, 'a', "descripcion", 250);
        layout.addView(editText);
    }

    private void loadExamen(Context context) {
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

    private void loadPregunta() {
        Toast.makeText(getContext(), "Cargando pregunta", Toast.LENGTH_SHORT).show();
    }

    private void loadActividad() {
        Toast.makeText(getContext(), "Cargando actividad", Toast.LENGTH_SHORT).show();
    }

    private void loadContenido() {
        Toast.makeText(getContext(), "Cargando contenido", Toast.LENGTH_SHORT).show();
    }

    // --------------------------------------
    // ---- EXTRAER INFORMACION DESDE... ----
    // --------------------------------------
    private void extractData(String table) {
        if (table.equals("tema")) {
            extractTema();
        } else if (table.equals("examen_diagnostico")) {
            extractExamenDiagnostico();
        } else if (table.equals("pregunta_examen")) {

        } else if (table.equals("pregunta_actividad")) {

        } else if (table.equals("contenido")) {

        }
    }

    private void extractTema() {
        Tema crudTema = new Tema(getContext());
        crudTema.open();
        TemaModel tema = crudTema.read(id);
        crudTema.close();

        EditText tituloET = layout.findViewWithTag("titulo");
        EditText descripcionET = layout.findViewWithTag("descripcion");

        tituloET.setText(tema.tituloValue);
        descripcionET.setText(tema.descripcionValue);
    }

    private void extractExamenDiagnostico() {
        Spinner spinner = layout.findViewWithTag("tema_spinner");
        EditText tituloET = layout.findViewWithTag("titulo");
        EditText nPreguntasET = layout.findViewWithTag("n_preguntas");
        EditText nivelMaximoET = layout.findViewWithTag("nivel_maximo");

        ExamenDiagnostico crudExamen = new ExamenDiagnostico(getContext());
        crudExamen.open();
        ExamenDiagnosticoModel examen = crudExamen.read(id);
        crudExamen.close();

        Tema crudTema = new Tema(getContext());
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

    // ----------------------------
    // ---- ESTABLECER WIDGETS ----
    // ----------------------------
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

    // -----------------------
    // ---- BOTON GUARDAR ----
    // -----------------------
    private View.OnClickListener saveItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = getContext();
            if (table.equals("tema")) {
                if (id > 0) {
                    updateTema(context);
                } else {
                    insertTema(context);
                }
            } else if (table.equals("examen_diagnostico")) {
                if (id > 0) {
                    updateExamenDiagnostico(context);
                } else {
                    insertExamenDiagnostico(context);
                }
            } else if (table.equals("pregunta_examen")) {
                notImplemented(context);
                if (id > 0) {
                    // update
                } else {
                    // insert
                }
            } else if (table.equals("pregunta_actividad")) {
                notImplemented(context);
                if (id > 0) {
                    // update
                } else {
                    // insert
                }
            } else if (table.equals("contenido")) {
                notImplemented(context);
                if (id > 0) {
                    // update
                } else {
                    // insert
                }
            }
        }
    };

    private void insertTema(Context context) {
        EditText tituloET = layout.findViewWithTag("titulo");
        EditText descripcionET = layout.findViewWithTag("descripcion");

        String titulo = tituloET.getText().toString().trim();
        String descripcion = descripcionET.getText().toString().trim();

        if (titulo.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            Tema crudTema = new Tema(context);
            TemaModel tema = new TemaModel(
                    titulo,
                    descripcion
            );

            crudTema.open();
            try {
                crudTema.insert(tema);
                Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show();

                tituloET.setText("");
                descripcionET.setText("");

            } catch (Exception e) {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudTema.close();
            }
        }
    }

    private void updateTema(Context context) {
        String titulo = ((EditText) layout.findViewWithTag("titulo")).getText().toString().trim();
        String descripcion = ((EditText) layout.findViewWithTag("descripcion")).getText().toString().trim();

        if (titulo.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            Tema crudTema = new Tema(context);
            TemaModel tema = new TemaModel(
                    id,
                    titulo,
                    descripcion
            );

            crudTema.open();
            try {
                crudTema.update(tema);
                Toast.makeText(context, "Registro actualizado", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudTema.close();
            }
        }
    }

    private void insertExamenDiagnostico(Context context) {
        Spinner spinner = layout.findViewWithTag("tema_spinner");
        String selection = spinner.getSelectedItem().toString();

        String titulo = ((EditText) layout.findViewWithTag("titulo")).getText().toString().trim();
        String nPreguntas = ((EditText) layout.findViewWithTag("n_preguntas")).getText().toString().trim();
        String nivelMaximo = ((EditText) layout.findViewWithTag("nivel_maximo")).getText().toString().trim();

        Pattern pattern = Pattern.compile("\\(\\d+\\)");
        Matcher matcher = pattern.matcher(selection);
        matcher.find();
        int idItem = Integer.parseInt(matcher.group().substring(1, matcher.group().length() - 1));

        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel tema = crudTema.read(idItem);
        crudTema.close();

        if (idItem > 0 || !nPreguntas.isEmpty() || !nivelMaximo.isEmpty()) {
            if (titulo.isEmpty()) {
                titulo = tema.tituloValue;
            }

            ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
            ExamenDiagnosticoModel examen = new ExamenDiagnosticoModel(
                    idItem,
                    titulo,
                    Integer.parseInt(nPreguntas),
                    Integer.parseInt(nivelMaximo)
            );
            crudExamen.open();
            try {
                crudExamen.insert(examen);
                Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show();

                spinner.setSelection(0);
                ((EditText) layout.findViewWithTag("titulo")).setText("");
                ((EditText) layout.findViewWithTag("n_preguntas")).setText("");
                ((EditText) layout.findViewWithTag("nivel_maximo")).setText("");

            } catch (Exception e) {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudExamen.close();
            }
        } else {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateExamenDiagnostico(Context context) {
        Spinner spinner = layout.findViewWithTag("tema_spinner");
        String selection = spinner.getSelectedItem().toString();

        String titulo = ((EditText) layout.findViewWithTag("titulo")).getText().toString().trim();
        String nPreguntas = ((EditText) layout.findViewWithTag("n_preguntas")).getText().toString().trim();
        String nivelMaximo = ((EditText) layout.findViewWithTag("nivel_maximo")).getText().toString().trim();

        Pattern pattern = Pattern.compile("\\(\\d+\\)");
        Matcher matcher = pattern.matcher(selection);
        matcher.find();
        int idItem = Integer.parseInt(matcher.group().substring(1, matcher.group().length() - 1));

        Tema crudTema = new Tema(context);
        crudTema.open();
        TemaModel tema = crudTema.read(idItem);
        crudTema.close();

        if (idItem > 0 || !nPreguntas.isEmpty() || !nivelMaximo.isEmpty()) {
            if (titulo.isEmpty()) {
                titulo = tema.tituloValue;
            }

            ExamenDiagnostico crudExamen = new ExamenDiagnostico(context);
            ExamenDiagnosticoModel examen = new ExamenDiagnosticoModel(
                    id,
                    idItem,
                    titulo,
                    Integer.parseInt(nPreguntas),
                    Integer.parseInt(nivelMaximo)
            );
            crudExamen.open();
            try {
                crudExamen.update(examen);
                Toast.makeText(context, "Registro actualizado", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudExamen.close();
            }
        } else {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void notImplemented(Context context) {
        Toast.makeText(context, "Aún no implementado", Toast.LENGTH_SHORT).show();
    }
}