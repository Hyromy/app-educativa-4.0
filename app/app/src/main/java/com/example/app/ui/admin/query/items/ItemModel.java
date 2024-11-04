package com.example.app.ui.admin.query.items;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.PreguntaExamenModel;
import com.example.app.db.models.TemaModel;
import com.example.app.db.utils.crud.Contenido;
import com.example.app.db.utils.crud.ExamenDiagnostico;
import com.example.app.db.utils.crud.PreguntaExamen;
import com.example.app.db.utils.crud.Tema;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemModel {
    public void insertTema(Context context, LinearLayout layout) {
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

    public void updateTema(Context context, LinearLayout layout, int id) {
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

    public void insertExamenDiagnostico(Context context, LinearLayout layout) {
        Spinner spinner = layout.findViewWithTag("tema_spinner");
        String selection = spinner.getSelectedItem().toString();
        Pattern pattern = Pattern.compile("\\(\\d+\\)");
        Matcher matcher = pattern.matcher(selection);
        matcher.find();
        int idItem = Integer.parseInt(matcher.group().substring(1, matcher.group().length() - 1));

        String titulo = ((EditText) layout.findViewWithTag("titulo")).getText().toString().trim();
        String nPreguntas = ((EditText) layout.findViewWithTag("n_preguntas")).getText().toString().trim();
        String nivelMaximo = ((EditText) layout.findViewWithTag("nivel_maximo")).getText().toString().trim();

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

    public void updateExamenDiagnostico(Context context, LinearLayout layout, int id) {
        Spinner spinner = layout.findViewWithTag("tema_spinner");
        String selection = spinner.getSelectedItem().toString();
        Pattern pattern = Pattern.compile("\\(\\d+\\)");
        Matcher matcher = pattern.matcher(selection);
        matcher.find();
        int idItem = Integer.parseInt(matcher.group().substring(1, matcher.group().length() - 1));

        String titulo = ((EditText) layout.findViewWithTag("titulo")).getText().toString().trim();
        String nPreguntas = ((EditText) layout.findViewWithTag("n_preguntas")).getText().toString().trim();
        String nivelMaximo = ((EditText) layout.findViewWithTag("nivel_maximo")).getText().toString().trim();

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

    public void insertContenido(Context context, LinearLayout layout) {
        Spinner spinner = layout.findViewWithTag("tema_spinner");
        String selection = spinner.getSelectedItem().toString();
        Pattern pattern = Pattern.compile("\\(\\d+\\)");
        Matcher matcher = pattern.matcher(selection);
        matcher.find();
        int idItem = Integer.parseInt(matcher.group().substring(1, matcher.group().length() - 1));

        String titulo = ((EditText) layout.findViewWithTag("titulo")).getText().toString().trim();
        String descripcion = ((EditText) layout.findViewWithTag("descripcion")).getText().toString().trim();

        // reemplazar por un spinner
        int nivelContenido = Integer.parseInt(((EditText) layout.findViewWithTag("nivel_contenido")).getText().toString().trim());

        int nPreguntas = Integer.parseInt(((EditText) layout.findViewWithTag("n_preguntas")).getText().toString().trim());

        if (idItem > 0 || nivelContenido > 0 || nPreguntas > 0) {
            Contenido crudContenido = new Contenido(context);
            ContenidoModel contenido = new ContenidoModel(
                    idItem,
                    titulo,
                    descripcion,
                    nivelContenido,
                    nPreguntas
            );
            crudContenido.open();
            try {
                crudContenido.insert(contenido);
                Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show();

                spinner.setSelection(0);
                ((EditText) layout.findViewWithTag("titulo")).setText("");
                ((EditText) layout.findViewWithTag("descripcion")).setText("");
                ((EditText) layout.findViewWithTag("nivel_contenido")).setText("");
                ((EditText) layout.findViewWithTag("n_preguntas")).setText("");

            } catch (Exception e) {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudContenido.close();
            }
        } else {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateContenido(Context context, LinearLayout layout, int id) {
        Spinner spinner = layout.findViewWithTag("tema_spinner");
        String selection = spinner.getSelectedItem().toString();
        Pattern pattern = Pattern.compile("\\(\\d+\\)");
        Matcher matcher = pattern.matcher(selection);
        matcher.find();
        int idItem = Integer.parseInt(matcher.group().substring(1, matcher.group().length() - 1));

        String titulo = ((EditText) layout.findViewWithTag("titulo")).getText().toString().trim();
        String descripcion = ((EditText) layout.findViewWithTag("descripcion")).getText().toString().trim();

        // reemplazar por un spinner
        int nivelContenido = Integer.parseInt(((EditText) layout.findViewWithTag("nivel_contenido")).getText().toString().trim());

        int nPreguntas = Integer.parseInt(((EditText) layout.findViewWithTag("n_preguntas")).getText().toString().trim());

        if (idItem > 0 || nivelContenido > 0 || nPreguntas > 0) {
            Contenido crudContenido = new Contenido(context);
            ContenidoModel contenido = new ContenidoModel(
                    id,
                    idItem,
                    titulo,
                    descripcion,
                    nivelContenido,
                    nPreguntas
            );
            crudContenido.open();
            try {
                crudContenido.update(contenido);
                Toast.makeText(context, "Registro actualizado", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            } finally {
                crudContenido.close();
            }
        } else {
            Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertPreguntaExamen(Context context, LinearLayout layout) {
        Spinner spinner = layout.findViewWithTag("examen_spinner");
        String selection = spinner.getSelectedItem().toString();
        Pattern pattern = Pattern.compile("\\(\\d+\\)");
        Matcher matcher = pattern.matcher(selection);
        matcher.find();
        int idItem = Integer.parseInt(matcher.group().substring(1, matcher.group().length() - 1));

        String texto = ((EditText) layout.findViewWithTag("texto")).getText().toString().trim();
        PreguntaExamenModel pregunta = new PreguntaExamenModel(
                idItem,
                0,
                texto
        );

        PreguntaExamen crudPregunta = new PreguntaExamen(context);
        crudPregunta.open();
        try {
            crudPregunta.insert(pregunta);
            Toast.makeText(context, "Registro guardado", Toast.LENGTH_SHORT).show();

            spinner.setSelection(0);
            ((EditText) layout.findViewWithTag("texto")).setText("");

        } catch (Exception e) {
            Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());

        } finally {
            crudPregunta.close();
        }
    }

    public void updatePreguntaExamen(Context context, LinearLayout layout, int id) {
        Spinner spinner = layout.findViewWithTag("examen_spinner");
        String selection = spinner.getSelectedItem().toString();
        Pattern pattern = Pattern.compile("\\(\\d+\\)");
        Matcher matcher = pattern.matcher(selection);
        matcher.find();
        int idItem = Integer.parseInt(matcher.group().substring(1, matcher.group().length() - 1));

        String texto = ((EditText) layout.findViewWithTag("texto")).getText().toString().trim();

        PreguntaExamenModel pregunta = new PreguntaExamenModel(
                id,
                idItem,
                0,
                texto
        );
        PreguntaExamen crudPregunta = new PreguntaExamen(context);
        crudPregunta.open();

        try {
            crudPregunta.update(pregunta);
            Toast.makeText(context, "Registro actualizado", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());

        } finally {
            crudPregunta.close();
        }
    }

    public void insertPreguntaActividad(Context context, LinearLayout layout) {
        Spinner spinner = layout.findViewWithTag("contenido_spinner");
        String selection = spinner.getSelectedItem().toString();
        Pattern pattern = Pattern.compile("\\(\\d+\\)");
        Matcher matcher = pattern.matcher(selection);
        matcher.find();
        int idItem = Integer.parseInt(matcher.group().substring(1, matcher.group().length() - 1));

        String texto = ((EditText) layout.findViewWithTag("texto")).getText().toString().trim();
    }

    public void updatePreguntaActividad(Context context, LinearLayout layout, int id) {
        System.out.println("updatePreguntaActividad");
    }
}